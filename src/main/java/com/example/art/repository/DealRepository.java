package com.example.art.repository;

import com.example.art.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal,Long> {

    boolean existsByName(String name);

}
