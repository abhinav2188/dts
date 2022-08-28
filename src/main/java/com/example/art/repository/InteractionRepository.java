package com.example.art.repository;

import com.example.art.model.Interaction;
import com.example.art.model.views.InteractionExcelView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    Page<Interaction> findAllByDealId(Long dealId,Pageable pageable);

    @Query(nativeQuery = true)
    List<InteractionExcelView> findAllInteractionExcelViews_Named();

}
