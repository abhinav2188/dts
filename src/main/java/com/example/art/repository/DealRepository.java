package com.example.art.repository;

import com.example.art.model.Deal;
import com.example.art.model.views.DealExcelView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.NamedQuery;
import java.util.List;

public interface DealRepository extends JpaRepository<Deal,Long> {

    boolean existsByName(String name);

    Page<Deal> findAllByCoOwners_Id(Long id, Pageable pageable);

//    @Query(value = "SELECT d.id, d.name as deal_name , p.party_name as party_name, group_concat(u.email separator ',') as co_owners from (user u join deal_co_owners dco on u.id=dco.co_owners_id) join deal d on d.id = dco.co_owned_deals_id join party p on p.id = d.party_id group by d.name", nativeQuery = true);
//    List<DealExcelView> findAllDealExcelView();

    @Query(value = "SELECT d.id, d.name as deal_name , p.party_name as party_name," +
            " group_concat(u.email separator ',') as co_owners " +
            "from (user u join deal_co_owners dco on u.id=dco.co_owners_id)" +
            " join deal d on d.id = dco.co_owned_deals_id join party p on p.id = d.party_id " +
            "group by d.name order by d.name" , nativeQuery = true)
    List<DealExcelView> findDeals();


    @Query(nativeQuery = true)
    List<DealExcelView> findAllDealExcelView_Named();

}
