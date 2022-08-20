package com.example.art.repository;

import com.example.art.model.User;
import com.example.art.model.views.UserExcelView;
import com.example.art.model.views.UserDropdownView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);
    public boolean existsByMobile(String mobile);

    public Optional<User> findByEmail(String email);

    List<UserDropdownView> findByIsActiveTrueAndCoOwnedDeals_Id(@NonNull Long id);



    boolean existsByCoOwnedDeals_Id(Long id);

    boolean existsByCoOwnedDeals_IdAndEmail(Long id, String email);

    List<UserExcelView> findByIdNotNull();

    List<UserDropdownView> findByEmailNotNull();

}
