package com.example.BackendShop.repository;

import com.example.BackendShop.model.Photo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface PhotoRepository extends JpaRepository<Photo, Long> {



}
