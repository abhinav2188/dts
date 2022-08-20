package com.example.art.dto.mapper;

import com.example.art.dto.response.AttachmentsResponse;
import com.example.art.dto.response.inner.AttachmentDetails;
import com.example.art.exceptions.DocumentStorageException;
import com.example.art.model.Document;
import com.example.art.model.views.DocumentView;
import com.example.art.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DocumentMapper {

    @Value("${project.path}")
    private String projectPath;

    @Autowired
    private MapperUtils mapperUtils;

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

    private String createDocPath(String id){
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/docs/").path(id)
                .toUriString();
    }

    public AttachmentsResponse createAttachmentsResponse(List<DocumentView> documents) {
        List<AttachmentDetails> attachmentDetailsList = documents.stream()
                .map(this::getAttachmentDetails)
                .collect(Collectors.toList());
        return new AttachmentsResponse(attachmentDetailsList.size(), attachmentDetailsList);
    }

    public AttachmentDetails getAttachmentDetails(DocumentView documentView) {
        AttachmentDetails details = new AttachmentDetails();
        mapperUtils.updateResponseFromEntity(documentView,details);
        details.setPath(createDocPath(details.getId()));
        return details;
    }

    public AttachmentDetails getAttachmentDetails(Document document) {
        AttachmentDetails details = new AttachmentDetails();
        mapperUtils.updateResponseFromEntity(document,details);
        details.setPath(createDocPath(details.getId()));
        return details;
    }


}
