package com.example.art.controllers;

import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.DocumentStorageException;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.model.Document;
import com.example.art.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/docs")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws DocumentStorageException {
        String id =  documentService.storeFile(file);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{docId}")
    public ResponseEntity<Resource> getFile(@PathVariable(name = "docId") String docId) throws EntityNotFoundException {
        return documentService.getFile(docId);
    }

}
