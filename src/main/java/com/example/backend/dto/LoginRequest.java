package com.example.backend.dto;

/**
 * DTO para recibir datos de login.
 * Simple contenedor de username y password.
 */
public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest() {}

    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }

    public String getPassword() { return password; }
    public void setPassword(String p) { this.password = p; }
}