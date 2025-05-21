package com.example.backend.controller;

import com.example.backend.dto.LoginRequest;
import com.example.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest req) {
    boolean ok = authService.validateUser(
            req.getUsername(), req.getPassword());
    // Vulnerable: devuelve mensajes genéricos que pueden ayudar a un atacante
    return ok
            ? ResponseEntity.ok("OK")
            : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("FAIL");
  }
}
