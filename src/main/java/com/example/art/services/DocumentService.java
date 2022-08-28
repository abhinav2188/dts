package com.example.art.services;

import com.example.art.dto.response.AttachmentsResponse;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.SuccessCreateResponse;
import com.example.art.dto.response.inner.AttachmentDetails;
import com.example.art.exceptions.DocumentStorageException;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    
    BaseResponse<AttachmentDetails> storeFile(Long dealId, MultipartFile file)
            throws DocumentStorageException, EntityNotFoundException, NoAuthorizationException;

    ResponseEntity<Resource> getFile(String docId) throws EntityNotFoundException;

    BaseResponse removeFile(Long dealId, String docId) throws EntityNotFoundException, NoAuthorizationException;

    BaseResponse<AttachmentsResponse> getAttachmentsInfo(Long dealId) throws NoAuthorizationException, EntityNotFoundException;

}
