package com.example.art.repository;

import com.example.art.model.Consultant;
import com.example.art.model.views.ConsultantDropdownView;
import com.example.art.model.views.ConsultantExcelView;
import com.example.art.model.views.ContactDropdownView;
import com.example.art.model.views.ContactExcelView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsultantRepository extends JpaRepository<Consultant,Long> {

    Page<Consultant> findAllByDealId(Long dealId, Pageable pageable);

    List<ConsultantDropdownView> findByDeal_Id(Long id);

    @Query(nativeQuery = true)
    List<ConsultantExcelView> findAllConsultantExcelViews_Named();

}
