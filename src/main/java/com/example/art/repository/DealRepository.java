package com.example.art.repository;

import com.example.art.model.Deal;
import com.example.art.model.views.DealDropdownView;
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

    boolean existsByIdAndCoOwners_Id(Long dealId, Long userId);

    Page<Deal> findDistinctByCoOwners_EmailLikeAndParty_PartyNameLikeAndNameLike(String email, String partyName, String name, Pageable pageable);

    Page<Deal> findDistinctByCoOwners_IdAndCoOwners_EmailLikeAndParty_PartyNameLikeAndNameLike(Long id, String email, String partyName, String name, Pageable pageable);

    List<DealDropdownView> findByNameNotNull();


}
