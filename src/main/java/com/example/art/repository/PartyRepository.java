package com.example.art.repository;

import com.example.art.model.Party;
import com.example.art.model.views.PartyDropdownView;
import com.example.art.model.views.PartyExcelView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Long> {

    Optional<Party> findByPartyName(String partyName);

    List<PartyDropdownView> findByIsActiveTrueOrderByCreateTimestampDesc();

    List<PartyDropdownView> findByOrderByCreateTimestampDesc();

    List<PartyExcelView> findByIdNotNullOrderByPartyName();

    boolean existsByPartyName(String partyName);

}
