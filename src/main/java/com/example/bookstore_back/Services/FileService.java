package com.example.bookstore_back.Services;

import com.example.bookstore_back.Models.File;
import com.example.bookstore_back.Repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class FileService {

    private FileRepository fileRepository;

    @Autowired
    public FileService(
            FileRepository fileRepository
    ){
        this.fileRepository = fileRepository;
    }

    public File add(MultipartFile file) throws IOException {
        return fileRepository.save(new File(file.getOriginalFilename(), file.getBytes()));
    }
}
