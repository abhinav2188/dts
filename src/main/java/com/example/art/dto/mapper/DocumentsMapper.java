package com.example.art.dto.mapper;

import com.example.art.exceptions.DocumentStorageException;
import com.example.art.model.Document;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@Slf4j
public class DocumentsMapper {

    public Document getDocument(MultipartFile file) throws DocumentStorageException {
        try{
            Document document = new Document();
            document.setDocumentName(file.getOriginalFilename());
            document.setDocumentType(file.getContentType());
            document.setData(file.getBytes());
            return document;
        } catch (IOException e) {
            log.error("failure saving file in database: {}",e.getLocalizedMessage());
            throw new DocumentStorageException();
        }
    }

}
