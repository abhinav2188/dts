package com.example.art.repository;

import com.example.art.model.Document;
import com.example.art.model.views.DocumentView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {

    List<DocumentView> findAllByDealId(Long dealId);

}
