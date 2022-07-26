package com.example.art.services.impl;

import com.example.art.dto.response.BaseResponse;
import com.example.art.exceptions.DocumentStorageException;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.model.Document;
import com.example.art.repository.DocumentRepository;
import com.example.art.services.DocumentService;
import com.example.art.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import java.io.IOException;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public String storeFile(MultipartFile file) throws DocumentStorageException {

        String fileName = file.getOriginalFilename();

        try{
            Document document = new Document();
            document.setDocumentName(file.getOriginalFilename());
            document.setDocumentType(file.getContentType());
            document.setData(file.getBytes());

            Document saved = documentRepository.save(document);

            return saved.getId();

        } catch (IOException e) {
            log.error("failure saving file in database: {}",e.getLocalizedMessage());
            throw new DocumentStorageException(fileName);
        }

    }

    @Override
    public ResponseEntity<Resource> getFile(String docId) throws EntityNotFoundException {
        Document document = documentRepository.findById(docId).orElseThrow(
                () -> new EntityNotFoundException("Document","id",docId));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getDocumentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:"+document.getId())
                .body(new ByteArrayResource(document.getData()));
    }

}
