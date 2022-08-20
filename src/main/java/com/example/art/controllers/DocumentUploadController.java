package com.example.art.controllers;

import com.example.art.dto.response.AttachmentsResponse;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.SuccessCreateResponse;
import com.example.art.dto.response.inner.AttachmentDetails;
import com.example.art.exceptions.DocumentStorageException;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.services.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/ext/{dealId}/docs")
@Slf4j
public class DocumentUploadController {

    @Autowired
    private DocumentService documentService;

    @PostMapping
    public BaseResponse<AttachmentDetails> uploadDealFile(@RequestParam("file") MultipartFile file,
                                                          @PathVariable("dealId") Long dealId)
            throws DocumentStorageException, EntityNotFoundException, NoAuthorizationException {
        return documentService.storeFile(dealId, file);
    }

    @DeleteMapping
    public BaseResponse deleteDealFile(@PathVariable("dealId") Long dealId, @RequestParam("docId") String docId)
            throws NoAuthorizationException, EntityNotFoundException {
        return documentService.removeFile(dealId, docId);
    }

    @GetMapping
    public BaseResponse<AttachmentsResponse> getAttachmentsInfo( @PathVariable("dealId") Long dealId)
            throws NoAuthorizationException, EntityNotFoundException {
        return documentService.getAttachmentsInfo(dealId);
    }

//    @PostMapping
//    public BaseResponse<SuccessCreateResponse> uploadDealFiles(@RequestParam("files") MultipartFile[] files) throws DocumentStorageException {
//        List<String> ids = documentService.storeFiles(files);
//        int staged = files.length;
//        int saved = ids.size();
//        if(saved == 0) throw new DocumentStorageException(files[0].getOriginalFilename()+", ...");
//        log.info(MessageUtils.infoFileStorage(staged,saved));
//        return BaseResponse.<SuccessCreateResponse>builder()
//                .status(HttpStatus.OK)
//                .responseMsg(MessageUtils.successPostMessage("Documents", saved))
//                .data(new SuccessCreateResponse(ids))
//                .build();
//    }

}
