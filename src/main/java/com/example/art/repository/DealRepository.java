package com.example.art.repository;

import com.example.art.model.Deal;
import com.example.art.model.views.DealExcelView;
import com.example.art.model.views.DealExcelViewPartial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal,Long> {

    List<DealExcelViewPartial> findByIdNotNull();

    boolean existsByName(String name);

    Page<Deal> findAllByCoOwners_Id(Long id, Pageable pageable);

    @Query(nativeQuery = true)
    List<DealExcelView> findAllDealExcelView_Named();

}
