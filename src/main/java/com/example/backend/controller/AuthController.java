package com.example.backend.controller;

import com.example.backend.dto.LoginRequest;
import com.example.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Maneja el login de usuarios.
 * Nota: la construcción de la consulta en el repo es vulnerable,
 * pero aquí solo devolvemos OK/FAIL para demostrar el flujo.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest req) {
    boolean valido = authService.validateUser(
            req.getUsername(), req.getPassword()
    );
    // OK si encontró coincidencias, FAIL de lo contrario
    return valido
            ? ResponseEntity.ok("OK")
            : ResponseEntity.status(401).body("FAIL");
  }
}