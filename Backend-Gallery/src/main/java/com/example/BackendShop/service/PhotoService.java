package com.example.BackendShop.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.example.BackendShop.Global;
import com.example.BackendShop.model.Director;
import com.example.BackendShop.model.Photo;
import com.example.BackendShop.model.User;
import com.example.BackendShop.repository.PhotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class PhotoService {
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    private Global global;
    @Autowired
    FileService directorService;
    @Autowired
    UserService userService;

    public Optional<Photo> getPhotoById(Long id){
        return photoRepository.findById(id);
    }
    @Transactional
    public boolean deletePhoto(Long id) {
        Optional<Photo> optional = photoRepository.findById(id);


        if (optional.isPresent()) {
            Photo deletePhoto = optional.get();
            Optional<Director> dirOptional =directorService.getFileById(optional.get().getFileId());
            if (dirOptional.isPresent()) {
                Optional<User> user = userService.getUserById(dirOptional.get().getUserId());
                if (user.isPresent()) {

                    String pathFrom = global.filePath + user.get().getId() + global.fileSeparator+dirOptional.get().getName()+global.fileSeparator;
                    String pathTo = global.filePath + "Delete";
                    moveFile(pathFrom, pathTo, id + ".jpeg", user.get().getEmail()+"_"+id+".jpeg");
                    deleteFile(pathFrom,id + "_s.jpeg");

                    photoRepository.deleteById(id);
                    return true;
                }
            }
        }

        return false;
    }


    public void savePhoto(MultipartFile file, String path, Long directorId, String extension) {
        Photo photo = new Photo();
        photo.setFileId(directorId);
        photo.setExtension(extension);
        photo = photoRepository.save(photo);

        try {
            Path originalFilePath = Paths.get(path + global.fileSeparator + photo.getId() + ".jpeg");
            Files.copy(file.getInputStream(), originalFilePath);
            saveResizedImage(originalFilePath, path + global.fileSeparator + photo.getId() + "_s" + ".jpeg");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveResizedImage(Path path,  String outputPath) throws IOException {
        int scaleFactor=6;
        BufferedImage originalImage = ImageIO.read(path.toFile());
        if(originalImage.getHeight()>originalImage.getWidth()){
            scaleFactor=originalImage.getWidth()/300;
        }else{
            scaleFactor=originalImage.getHeight()/300;
        }
        File imageFile = new File(path.toUri());
        BufferedImage rotatedImage;
        boolean needRotate=checkExifOrientation(imageFile);
        if(needRotate){
            rotatedImage = rotateImage(originalImage);
        }else{
            rotatedImage=originalImage;
        }

        int originalWidth = rotatedImage.getWidth();
        int originalHeight = rotatedImage.getHeight();

        int newWidth = originalWidth / scaleFactor;
        int newHeight = originalHeight / scaleFactor;

        Image resizedImage = rotatedImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);


        BufferedImage bufferedResizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        bufferedResizedImage.getGraphics().drawImage(resizedImage, 0, 0, null);


        File outputFile = new File(outputPath);
        ImageIO.write(bufferedResizedImage, "jpeg", outputFile); // sau "png"
    }

    private BufferedImage rotateImage(BufferedImage originalImage) {

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();


        BufferedImage rotatedImage = new BufferedImage(height, width, originalImage.getType());
        Graphics2D g2d = rotatedImage.createGraphics();


        g2d.translate((height - width) / 2, (width - height) / 2);
        g2d.rotate(Math.toRadians(90), width / 2.0, height / 2.0);


        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }
    public static boolean checkExifOrientation(File imageFile) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
            ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if (directory != null) {
                int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
                if(orientation==6){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String moveFile(String pathFrom, String pathTo, String nameFrom, String nameTo) {
        File sourceFile = new File(pathFrom, nameFrom); // Calea fișierului sursă
        File destinationFile = new File(pathTo, nameTo); // Calea fișierului destinație
        if (!sourceFile.exists()) {
            return "Fișierul sursă nu există: " + sourceFile.getAbsolutePath();
        }
        try {
            if (sourceFile.renameTo(destinationFile)) {
                return "Fișierul a fost mutat cu succes la: " + destinationFile.getAbsolutePath();
            } else {
                return "Mutarea fișierului a eșuat.";
            }
        } catch (Exception e) {
            return "A apărut o eroare: " + e.getMessage();
        }
    }

    public static boolean deleteFile(String path, String name) {
        File file = new File(path, name);
        if (file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }



}




