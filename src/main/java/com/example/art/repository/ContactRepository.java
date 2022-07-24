package com.example.art.repository;

import com.example.art.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ContactRepository extends JpaRepository<Contact, Long> {

    Page<Contact> findAllByDealId(Long dealId,Pageable pageable);

}
