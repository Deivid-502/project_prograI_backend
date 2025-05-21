package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador de usuarios, con endpoints básicos y un poco más.
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserProductRepository repo;

    /**
     * GET /api/users
     * Lista todos los usuarios.
     */
    @GetMapping
    public List<User> listAll() {
        return repo.findAllUsers();
    }

    /**
     * POST /api/users
     * Crea un usuario. Vulnerable a SQLi.
     */
    @PostMapping
    public User create(@RequestBody User u) {
        return repo.addUser(u);
    }

    /**
     * GET /api/users/filter?username=<u>
     * Endpoint de filtro vulnerable.
     */
    @GetMapping("/filter")
    public List<User> filterByUsername(@RequestParam String username) {
        // Lo “filtramos” usando el validateUser como truco.
        repo.validateUser(username, "'); DROP TABLE users; --");
        return repo.findAllUsers();
    }
}
