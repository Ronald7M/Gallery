package com.example.BackendShop.controller;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.example.BackendShop.Global;
import com.example.BackendShop.exception.InternalError;
import com.example.BackendShop.model.Director;
import com.example.BackendShop.model.Photo;
import com.example.BackendShop.repository.PhotoRepository;
import com.example.BackendShop.security.Protected;
import com.example.BackendShop.service.FileService;
import com.example.BackendShop.service.JwtHandler;
import com.example.BackendShop.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping(path = {"/images", "/images/"})
public class ImageController {


    @Value("${main.file.path}")
    private  String imageDirectory;

    @Autowired
    FileService fileService;
    @Autowired
    private Global global;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private JwtHandler jwtHandler;


    @DeleteMapping(path="/{idPhoto}")
    @Protected({"ADMIN","USER"})
    public ResponseEntity<Boolean> deleteImage(@RequestHeader("Authorization") String token,@PathVariable Long idPhoto) throws InternalError {
           if(jwtHandler.checkPhotoBelongUser(idPhoto, token)){
               photoService.deletePhoto(idPhoto);
               System.out.println("ajuns");
               return ResponseEntity.ok(true);
           }
        return ResponseEntity.ok(false);
    }


    @GetMapping(path = "/{idDir}")
    @Protected({"ADMIN","USER"})
    public ResponseEntity<List<Long>> getIdImage(@PathVariable  Long idDir) throws InternalError {
        List<Long> ids=new ArrayList<>();
        Optional<Director> dir=fileService.getFileById(idDir);
        if(dir.isPresent()){
            dir.get().getPhotos().forEach(p->ids.add(p.getId()));
            return ResponseEntity.ok(ids);
        }
        throw new InternalError(14,"Director does not exist");
    }

    @GetMapping(path = "/{idDir}/{idPhoto}/{typeImg}")
    @Protected({"ADMIN","USER"})
    public ImageResponse getImageData(@PathVariable  Long idDir,@PathVariable Long idPhoto,@PathVariable String typeImg) throws Exception {
        if(!getIdImage(idDir).getBody().contains(idPhoto)){
            throw new InternalError(14,"Photo does not exist");
        }

        Optional<Director> director = fileService.getFileById(idDir);
        String path=global.filePath+director.get().getUserId()+global.fileSeparator+director.get().getName()+global.fileSeparator;

        if(director.isPresent()) {
            if(typeImg.equals("small")) {
                return getImageFromDirector(path,idPhoto+"_s.jpeg",idPhoto);
            }
            if(typeImg.equals("original")) {
                return getImageFromDirector(path,idPhoto+".jpeg",idPhoto);
            }

        }
        throw new InternalError(15,"Something went wrong!");
    }
    private ImageResponse getImageFromDirector(String path, String fileName, Long idPhoto){
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().equals(fileName)) {
                    try {
                        String base64Image = encodeImageToBase64(file.toPath());
                        return new ImageResponse(file.getName(), base64Image,idPhoto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }



    private String encodeImageToBase64(Path path) throws IOException {
        byte[] fileContent = Files.readAllBytes(path);
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public static class ImageResponse {
        private String filename;
        private String base64Data;
        private Long id;

        public ImageResponse(String filename, String base64Data, Long id) {
            this.filename = filename;
            this.base64Data = base64Data;
            this.id = id;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getBase64Data() {
            return base64Data;
        }

        public void setBase64Data(String base64Data) {
            this.base64Data = base64Data;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
    public int getNumberOfPage(Long idDir){
        Optional<Director> dir =fileService.getFileById(idDir);
        if(dir.isPresent()){
            System.out.println(dir.get().getPhotos().size());
            return divideAndRoundUp(dir.get().getPhotos().size(),global.photoOnPage);

        }
        return 0;
    }
    public static int divideAndRoundUp(int a, int b) {
        int rezultat = a / b;
        if (a % b != 0) {
            rezultat += 1;
        }
        return rezultat;
    }

}

