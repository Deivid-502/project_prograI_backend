package com.example.backend.service;

import com.example.backend.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserProductRepository repo;

    public boolean validateUser(String u, String p) {
        return repo.validateUser(u, p);
    }
}
