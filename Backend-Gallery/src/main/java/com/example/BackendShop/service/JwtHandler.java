package com.example.BackendShop.service;

import com.example.BackendShop.exception.InternalError;
import com.example.BackendShop.model.Director;
import com.example.BackendShop.model.Photo;
import com.example.BackendShop.model.User;
import com.example.BackendShop.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtHandler {
    private static JwtService jwtService;
    private static UserService userService;
    public static PhotoService photoService;
    public static FileService directorService;

    public JwtHandler(JwtService jwtService, UserService userService, FileService directorService, PhotoService photoService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.directorService = directorService;
        this.photoService = photoService;
    }

    static public String getRole(String token) {
          token = token.substring(7);
          return jwtService.getRole(token);
    }

    static public User getSubject(String token) throws InternalError {
        token = token.substring(7);
        Optional<User> user =userService.getUserByEmail(jwtService.getSubject(token));
        if (user.isPresent()) {
            return user.get();
        }else{
            throw new InternalError(10,"UserNotExist");
        }
    }

     public boolean checkPhotoBelongUser(Long idPhoto,String token) throws InternalError {
        Optional<Photo> photo=photoService.getPhotoById(idPhoto);
        if(photo.isPresent()){
           Optional<Director> director = directorService.getFileById(photo.get().getFileId());
            if(director.isPresent()){
                if(director.get().getUserId().equals(JwtHandler.getSubject(token).getId())){
                    return true;
                }else{
                    throw new InternalError(10,"You dont have permission to delete this photo");
                }
            }else{
                throw new InternalError(10,"Director does not exist");
            }
        }else{
            throw new InternalError(10,"Photo does not exist");
        }

    }
}
