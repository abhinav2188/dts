package com.example.art.repository;

import com.example.art.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);
    public boolean existsByMobile(String mobile);

    public Optional<User> findByEmail(String email);

}
