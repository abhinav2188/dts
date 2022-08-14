package com.example.art.repository;

import com.example.art.model.Brochure;
import com.example.art.model.views.BrochureDropdownView;
import com.example.art.model.views.BrochureUrlView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BrochureRepository extends JpaRepository<Brochure,String> {

    boolean existsByBrochureName(String brochureName);

    Brochure findByBrochureName(String brochureName);

    List<BrochureDropdownView> findByIsActiveTrueOrderByUpdateTimestampDesc();

    List<BrochureUrlView> findByBrochureNameIn(Collection<String> brochureNames);


}
