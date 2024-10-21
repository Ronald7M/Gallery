package com.example.BackendShop.controller;

import com.example.BackendShop.exception.InternalError;
import com.example.BackendShop.model.Photo;
import com.example.BackendShop.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(path = {"/upload", "/upload/"})
public class UploadController {

    @Autowired
    UploadService uploadService;



    @PostMapping("/{idDir}")
    public ResponseEntity<String> handleFileUpload(@RequestParam("demo") MultipartFile[] files,@PathVariable Long idDir) throws  InternalError {
        uploadService.uploadFile(files, idDir);

            return ResponseEntity.ok("{\"success\": true}");


    }
}
