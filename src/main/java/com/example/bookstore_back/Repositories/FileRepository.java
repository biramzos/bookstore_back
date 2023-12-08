package com.example.bookstore_back.Repositories;

import com.example.bookstore_back.Models.File;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
@Transactional
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findFileByName(String fileName);
}
