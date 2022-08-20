package com.example.art.repository;

import com.example.art.model.Contact;
import com.example.art.model.views.ContactDropdownView;
import com.example.art.model.views.ContactExcelView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ContactRepository extends JpaRepository<Contact, Long> {

    Page<Contact> findAllByDealId(Long dealId,Pageable pageable);

    List<ContactDropdownView> findByDeal_Id(Long id);

    @Query(nativeQuery = true)
    List<ContactExcelView> findAllContactExcelViews_Named();
}
