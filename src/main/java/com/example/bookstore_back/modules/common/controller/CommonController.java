package com.example.bookstore_back.modules.common.controller;


import com.example.bookstore_back.utils.ByteService;
import com.example.bookstore_back.modules.common.model.File;
import com.example.bookstore_back.modules.common.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/files")
public class CommonController {

    private FileService fileService;

    @Autowired
    public CommonController(
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
