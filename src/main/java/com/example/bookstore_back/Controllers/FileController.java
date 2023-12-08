package com.example.bookstore_back.Controllers;

import com.example.bookstore_back.Models.File;
import com.example.bookstore_back.Services.ByteService;
import com.example.bookstore_back.Services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/files")
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(
            FileService fileService
    ){
        this.fileService = fileService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadFile(@PathVariable("id") File file) {
        byte[] fileData = ByteService.getBytes(file.getData());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", file.getName()); // Replace getFileName() with your method to retrieve the file name
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileData);
    }


}
