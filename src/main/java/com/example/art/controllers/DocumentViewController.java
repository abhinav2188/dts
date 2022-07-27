package com.example.art.controllers;

import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/docs")
public class DocumentViewController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/{docId}")
    public ResponseEntity<Resource> getFile(@PathVariable(name = "docId") String docId) throws EntityNotFoundException {
        return documentService.getFile(docId);
    }

}
