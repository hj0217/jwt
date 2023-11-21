package com.jwt.jwt.repository;

import com.jwt.jwt.model.User_entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User_entity, Long> {
    public User_entity findByUsername(String username);
}
