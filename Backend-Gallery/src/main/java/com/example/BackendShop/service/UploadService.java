package com.example.BackendShop.service;

import com.example.BackendShop.Global;
import com.example.BackendShop.exception.InternalError;
import com.example.BackendShop.model.Director;
import com.example.BackendShop.model.Photo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class UploadService {
    @Value("${main.file.path}")
    String uploadDir;

    @Autowired
    FileService fileService;
    @Autowired
    PhotoService photoService;
    @Autowired
    Global global;




    public void uploadFile(MultipartFile[] files, Long directorId) throws InternalError {
       Optional<Director> director= fileService.getFileById(directorId);
        if (director.isPresent()) {
           Long id= director.get().getUserId();
           String name=director.get().getName();
            for (MultipartFile file : files) {
                    uploadOnePhoto(file,uploadDir+id+global.fileSeparator+name,directorId,file.getContentType());
            }

        }else{
            throw new InternalError(10,"DirectorNotExist");
        }
    }



    private void uploadOnePhoto(MultipartFile file,String path ,Long directorId,String extension) {
        photoService.savePhoto(file,path,directorId,extension);
    }

}
