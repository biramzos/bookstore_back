package com.example.bookstore_back.Services;

import com.example.bookstore_back.Models.File;
import com.example.bookstore_back.Repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.NoSuchElementException;

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
        return fileRepository.save(new File(file.getOriginalFilename(), ByteService.getString(file.getBytes())));
    }

    public File getFileByName(String fileName) {
        return fileRepository.findFileByName(fileName).orElseThrow(() -> new NoSuchElementException("There is no file with name '" + fileName + "'!"));
    }
}
