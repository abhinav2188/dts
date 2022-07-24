package com.example.art.repository;

import com.example.art.model.Interaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    Page<Interaction> findAllByDealId(Long dealId,Pageable pageable);

}
