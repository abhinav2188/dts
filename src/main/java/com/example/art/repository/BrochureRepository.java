package com.example.art.repository;

import com.example.art.model.Brochure;
import com.example.art.model.views.BrochureDropdownView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrochureRepository extends JpaRepository<Brochure,String> {

    boolean existsByBrochureName(String brochureName);

    Brochure findByBrochureName(String brochureName);

    List<BrochureDropdownView> findByIsActiveTrueOrderByUpdateTimestampDesc();

}
