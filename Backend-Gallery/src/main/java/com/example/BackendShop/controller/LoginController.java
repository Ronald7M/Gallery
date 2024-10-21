package com.example.BackendShop.controller;


import com.example.BackendShop.DirectorComponent;
import com.example.BackendShop.dto.CredentialDTO;
import com.example.BackendShop.exception.InternalError;
import com.example.BackendShop.exception.InvalidToken;
import com.example.BackendShop.model.User;
import com.example.BackendShop.security.JwtService;
import com.example.BackendShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Optional;

@RestController
@RequestMapping(path = {"/login", "/login/"})
public class LoginController {
    @Autowired
    JwtService jwtService;
    @Autowired
    DirectorComponent directorComponent;
    @Autowired
    UserService userService;
    @Value("${main.file.path}")
    String directoryPath;


    @PutMapping()
    public ResponseEntity<String> login(@RequestBody CredentialDTO credential) throws InternalError {
        credential.check();
        Optional<User> findUser=userService.login(credential);
        String token;
        if (findUser.isPresent()) {
            token=jwtService.generateJwtToken(credential,findUser.get().getRole());
        }else{
            throw new InternalError(0,"CredentialInvalid");
        }

        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(token);
    }
    @PostMapping
    public ResponseEntity<Boolean> register(@RequestBody CredentialDTO credential) throws InternalError {
        credential.check();
        userService.register(credential);

        Optional<User> user =userService.getUserByEmail(credential.getEmail());
        if(user.isPresent()){
            directorComponent.createDirector(user.get().getId());
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }



}
