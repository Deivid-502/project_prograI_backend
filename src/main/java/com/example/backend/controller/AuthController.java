package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.repository.VulnerableRepository;

@RestController
@RequestMapping("/api")
public class AuthController {
  @Autowired
  private VulnerableRepository repo;

  @PostMapping("/login")
  public ResponseEntity<String> login(
      @RequestParam String username,
      @RequestParam String password) {
    boolean ok = repo.validateUser(username, password);
    if (ok) {
      return ResponseEntity.ok("Login exitoso");
    } else {
      return ResponseEntity.status(401).body("Credenciales inválidas");
    }
  }
}
