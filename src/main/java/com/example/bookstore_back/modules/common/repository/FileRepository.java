package com.example.bookstore_back.modules.common.repository;

import com.example.bookstore_back.modules.common.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findFileByName(String fileName);
}
