package com.example.BackendShop.controller;


import com.example.BackendShop.DirectorComponent;
import com.example.BackendShop.exception.InternalError;
import com.example.BackendShop.model.Director;
import com.example.BackendShop.security.Protected;
import com.example.BackendShop.service.FileService;
import com.example.BackendShop.service.JwtHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/directors", "/directors/"})
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private DirectorComponent directorComponent;






    @GetMapping()
    @Protected({"ADMIN","USER"})
    public ResponseEntity<List<Director>> getDirectorsByToken(@RequestHeader("Authorization") String token) throws InternalError {
        return ResponseEntity.ok().body(fileService.getFilesByUserId(JwtHandler.getSubject(token).getId()));
    }

    @PostMapping("/{nameDirector}")
    @Protected({"ADMIN","USER"})
    public ResponseEntity<Boolean> createDirector(@RequestHeader("Authorization") String token,@PathVariable String nameDirector) throws InternalError {

        directorComponent.createDirectorForUser( JwtHandler.getSubject(token), nameDirector);
        return ResponseEntity.ok().body(true);
    }

}
