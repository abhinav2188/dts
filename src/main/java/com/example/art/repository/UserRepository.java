package com.example.art.repository;

import com.example.art.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public boolean existsByEmail(String email);
    public boolean existsByMobile(String mobile);

}
