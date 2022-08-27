package com.example.clientapp.dao;

import com.example.clientapp.domain.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<UserApp, Long> {
    UserApp findByUsername(String username);
}
