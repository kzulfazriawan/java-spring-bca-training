package com.springdemo.repository;

import com.springdemo.entities.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepositoryInterface extends JpaRepository<UserGroup, Long> {
    UserGroup findByName(String name);
}
