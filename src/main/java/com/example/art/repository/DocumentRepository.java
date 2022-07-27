package com.example.art.repository;

import com.example.art.dto.response.inner.AttachmentDetails;
import com.example.art.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {

    List<AttachmentDetails> findAllByDealId(Long dealId);

}
