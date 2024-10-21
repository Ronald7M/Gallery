package com.example.BackendShop.controller;

import com.example.BackendShop.security.Protected;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = {"/test", "/test/"})
public class TestController {
    @PostMapping()
    @Protected({"ADMIN","USER"})
    public ResponseEntity<String> post(){
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping()
    public ResponseEntity<String> get(){
        return ResponseEntity.ok("Salutare lumeee");
    }
}
