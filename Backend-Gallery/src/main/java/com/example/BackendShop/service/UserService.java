package com.example.BackendShop.service;

import com.example.BackendShop.dto.CredentialDTO;
import com.example.BackendShop.enums.Roles;
import com.example.BackendShop.exception.InternalError;
import com.example.BackendShop.model.User;
import com.example.BackendShop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService  {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<User> getUserByEmail(String username) {
        return  userRepository.findByEmail(username);
    }

    public boolean register(CredentialDTO credential) throws InternalError {
        Optional<User> user = userRepository.findByEmail(credential.getEmail());
        if(user.isPresent()) {
            throw new InternalError(0,"UserAlreadyExists");
        }
        saveUser(credential);
        return true;
    }

    public Optional<User> login(CredentialDTO credential) throws InternalError {
        Optional<User> user = userRepository.findByEmail(credential.getEmail());
        if(user.isPresent()) {
            if (matches(credential.getPassword(), user.get().getPassword())) {
                return user;
            }
        }
        return Optional.empty();
    }

    private User saveUser(CredentialDTO credential) {
        User user = new User();
        user.setEmail(credential.getEmail());
        user.setPassword(credential.getPassword());
        user.setUsername("UNKNOW");
        user.setPasswordAttempts(0);
        user.setRole(Roles.USER.getValue());
        user.setPassword(passwordEncoder.encode(credential.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }



    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


}
