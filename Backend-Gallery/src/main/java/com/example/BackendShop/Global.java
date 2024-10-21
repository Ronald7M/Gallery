package com.example.BackendShop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Global {
    @Value("${main.file.separator}")
    public   String fileSeparator;
    @Value("${main.file.path}")
    public   String filePath;

    public int  photoOnPage=3;
}

