package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador de usuarios: CRUD básico y filtro express.
 * Algunos endpoints concatenan parámetros sin sanitizar a propósito.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserProductRepository repo;

    /**
     * GET /api/users
     * @return todos los usuarios (ojo, incluye contraseñas en texto plano).
     */
    @GetMapping
    public List<User> listAll() {
        return repo.findAllUsers();
    }

    /**
     * POST /api/users
     * @param u objeto User con username y password.
     * @return el usuario creado (vulnerable a SQLi).
     */
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u) {
        User creado = repo.addUser(u);
        return creado != null
                ? ResponseEntity.ok(creado)
                : ResponseEntity.badRequest().build();
    }

    /**
     * GET /api/users/filter?username=<u>
     * Filtro demo: llama al método validateUser con payload malicioso.
     * @return lista completa de usuarios.
     */
    @GetMapping("/filter")
    public List<User> filterByUsername(@RequestParam String username) {
        // Este truco intenta inyección al validar
        repo.validateUser(username, "'); DROP TABLE users; --");
        return repo.findAllUsers();
    }
}