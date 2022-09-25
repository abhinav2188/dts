package com.example.art.repository;

import com.example.art.model.Party;
import com.example.art.model.views.PartyDropdownView;
import com.example.art.model.views.PartyExcelView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findByPartyName(String partyName);

    List<PartyDropdownView> findByIsActiveTrueOrderByCreateTimestampDesc();

    List<PartyDropdownView> findByOrderByCreateTimestampDesc();

    List<PartyExcelView> findByIdNotNull();

    boolean existsByPartyName(String partyName);

}
