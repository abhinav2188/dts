package com.example.art.services;

import com.example.art.exceptions.DocumentStorageException;
import com.example.art.exceptions.EntityNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    
    String storeFile(MultipartFile file) throws DocumentStorageException;

    ResponseEntity<Resource> getFile(String docId) throws EntityNotFoundException;

}
