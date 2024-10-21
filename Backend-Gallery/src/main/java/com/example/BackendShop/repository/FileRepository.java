package com.example.BackendShop.repository;


import com.example.BackendShop.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileRepository extends JpaRepository<Director, Long> {

    public List<Director> findByUserId(Long id);



    public Director findById(long id);

}
