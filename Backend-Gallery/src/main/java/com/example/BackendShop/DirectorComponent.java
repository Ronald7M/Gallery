package com.example.BackendShop;

import com.example.BackendShop.exception.InternalError;
import com.example.BackendShop.model.Director;
import com.example.BackendShop.model.User;
import com.example.BackendShop.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DirectorComponent {


    @Autowired
    private FileService fileService;
    @Autowired
    Global global;

    public  void createDirectorForUser(User user,String name) throws InternalError {
        createDirector(global.filePath+user.getId(),name);
        Director director = new Director();
        director.setUserId(user.getId());
        director.setName(name);
        fileService.save(director);

    }



    private   void  createDirector(String path, String name) throws InternalError {
        String directoryPath=path+global.fileSeparator+name+global.fileSeparator;
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new InternalError(10,"DirectoryCreationFailed" +directoryPath);
            }
        } else {
            throw new InternalError(10,"DirectoryExistAlready"+directoryPath);
        }
    }
    public void  createDirector(Long id) throws InternalError {
        String directoryPath= global.filePath+id+global.fileSeparator;
        File directory = new File(directoryPath);
        System.out.println(directoryPath);
        if (!directory.exists()) {
            
            boolean created = directory.mkdirs();
            if (!created) {
                throw new InternalError(10,"DirectoryCreationFailed"+directoryPath);
            }
        } else {
            throw new InternalError(10,"DirectoryExistAlready"+directoryPath);
        }
    }
}
