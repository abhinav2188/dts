package com.example.art.repository;

import com.example.art.model.User;
import com.example.art.model.views.UserHandlerDropdownView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);
    public boolean existsByMobile(String mobile);

    public Optional<User> findByEmail(String email);

    List<UserHandlerDropdownView> findByIsActiveTrueAndCoOwnedDeals_Id(@NonNull Long id);

    boolean existsByCoOwnedDeals_Id(Long id);

}
