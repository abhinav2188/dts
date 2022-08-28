package com.example.art.controllers;

import com.example.art.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BrochureViewController {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("api/download/{fileName}")
    public ResponseEntity<Resource> viewBrochure(@PathVariable("fileName") String fileName){

        Resource resource = null;
        try {
            resource = fileStorageService.getFileAsResource(fileName);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if(resource == null){
            return ResponseEntity.notFound().build();
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);

    }
}
