package com.example.art.repository;

import com.example.art.model.DealHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DealHistoryRepository extends JpaRepository<DealHistory, Long> {

    Page<DealHistory> findByDealId(Long dealId, Pageable pageable);

}
