package com.example.clientapp.dao;

import com.example.clientapp.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDAO extends JpaRepository<Client, Long> {
}
