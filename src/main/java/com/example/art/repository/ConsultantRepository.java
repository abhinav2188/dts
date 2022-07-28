package com.example.art.repository;

import com.example.art.model.Consultant;
import com.example.art.model.views.ContactDropdownView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultantRepository extends JpaRepository<Consultant,Long> {

    Page<Consultant> findAllByDealId(Long dealId, Pageable pageable);

    List<ContactDropdownView> findByDeal_Id(Long id);

}
