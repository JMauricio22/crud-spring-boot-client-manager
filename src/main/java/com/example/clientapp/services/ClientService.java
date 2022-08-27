package com.example.clientapp.services;

import com.example.clientapp.dao.ClientDAO;
import com.example.clientapp.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientDAO clientDAO;

    public List<Client> findAll() {
        return clientDAO.findAll();
    }

    @Transactional
    public void save(Client client) {
        clientDAO.save(client);
    }

    public Client findById(Long id) {
        return clientDAO.findById(id).orElse(null);
    }

    @Transactional
    public void remove(Client client) {
        clientDAO.delete(client);
    }

}
