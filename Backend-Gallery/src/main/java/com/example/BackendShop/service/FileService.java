package com.example.BackendShop.service;

import com.example.BackendShop.model.Director;
import com.example.BackendShop.model.Photo;
import com.example.BackendShop.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    public List<Director> getFilesByUserId(Long id) {
        return fileRepository.findByUserId(id);
    }

    public Optional<Director> getFileById(Long id) {
        Optional<Director> dir=fileRepository.findById(id);
        if(dir.isPresent()){
            return dir;
        }
        return Optional.empty();
    }


    public Director save(Director director) {
        return fileRepository.save(director);

    }





}
