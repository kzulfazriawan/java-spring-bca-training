package com.springdemo.repository;

import com.springdemo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUsername(String username);
}
