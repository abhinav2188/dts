package com.example.art.services.impl;

import com.example.art.dto.mapper.DocumentMapper;
import com.example.art.dto.response.AttachmentsResponse;
import com.example.art.dto.response.BaseResponse;
import com.example.art.dto.response.SuccessCreateResponse;
import com.example.art.dto.response.inner.AttachmentDetails;
import com.example.art.exceptions.DocumentStorageException;
import com.example.art.exceptions.EntityNotFoundException;
import com.example.art.exceptions.NoAuthorizationException;
import com.example.art.model.Deal;
import com.example.art.model.Document;
import com.example.art.model.enums.DealSubject;
import com.example.art.model.views.DocumentView;
import com.example.art.repository.DocumentRepository;
import com.example.art.services.DealHistoryService;
import com.example.art.services.DocumentService;
import com.example.art.services.helper.ServiceUtils;
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

import java.util.List;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ServiceUtils serviceUtils;

    @Autowired
    private DocumentMapper documentsMapper;

    @Autowired
    private DealHistoryService dealHistoryService;

    @Override
    public BaseResponse<AttachmentDetails> storeFile(Long dealId, MultipartFile file)
            throws DocumentStorageException, EntityNotFoundException, NoAuthorizationException{

        Deal deal = serviceUtils.getDeal(dealId);
        serviceUtils.checkUserAuthorization(deal);

        Document document = documentsMapper.getDocument(file);
        document.setDeal(deal);
        Document saved = documentRepository.save(document);

        AttachmentDetails attachmentDetails = documentsMapper.getAttachmentDetails(saved);

        dealHistoryService.addDealHistory(dealId, DealSubject.ADDED_DEAL_ATTACHMENT, attachmentDetails);

        return BaseResponse.<AttachmentDetails>builder()
                .status(HttpStatus.OK)
                .responseMsg(MessageUtils.successPostMessage("Document"))
                .data(attachmentDetails)
                .build();

    }

    @Override
    public BaseResponse<AttachmentsResponse> getAttachmentsInfo(Long dealId)
            throws NoAuthorizationException, EntityNotFoundException {

        Deal deal = serviceUtils.getDeal(dealId);
        serviceUtils.checkUserAuthorization(deal);

        List<DocumentView> documents = documentRepository.findAllByDealId(dealId);

        AttachmentsResponse response = documentsMapper.createAttachmentsResponse(documents);

        return BaseResponse.<AttachmentsResponse>builder()
                .status(HttpStatus.OK)
                .data(response)
                .responseMsg(MessageUtils.successGetMessage("Documents", response.getTotalCount()))
                .build();

    }

    @Override
    public BaseResponse removeFile(Long dealId, String docId) throws EntityNotFoundException, NoAuthorizationException {

        if(!documentRepository.existsById(docId))
                throw new EntityNotFoundException("Document","id",docId);

        Deal deal = serviceUtils.getDeal(dealId);
        serviceUtils.checkUserAuthorization(deal);

        documentRepository.deleteById(docId);

        dealHistoryService.addDealHistory(dealId, DealSubject.DELETED_DEAL_ATTACHMENT, docId);

        return BaseResponse.builder()
                .status(HttpStatus.OK)
                .responseMsg(MessageUtils.successDeleteMessage("Document"))
                .build();
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


//    @Override
//    public List<String> storeFiles(MultipartFile[] files) {
//        List<String> ids = new ArrayList<>();
//        for(MultipartFile file : files){
//            try{
//                String id = this.storeFile(dealId, file);
//                ids.add(id);
//            }
//            catch(DocumentStorageException ex){
//                log.info("error saving file: "+file.getOriginalFilename());
//            }
//        }
//        return ids;
//    }


}
