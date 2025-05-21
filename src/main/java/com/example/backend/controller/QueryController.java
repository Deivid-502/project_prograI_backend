package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.*;
import com.example.backend.repository.UserProductRepository;

import java.util.List;
import java.util.Map;

// Este endpoint es el "portaaviones" de la inyección SQL.
// Te deja pasar cualquier query para que la ejecutes.
@RestController
@RequestMapping("/api/debug")
public class QueryController {

    @Autowired
    private UserProductRepository repo;

    /**
     * POST /api/debug/query
     * Body: { "sql": "SELECT * FROM users" }
     * Devuelve el resultado bruto de la consulta.
     */
    @PostMapping("/query")
    public ResponseEntity<?> runRawQuery(@RequestBody Map<String,String> body) {
        String sql = body.get("sql");
        try {
            List<?> result = repo.executeArbitrarySql(sql);
            return ResponseEntity.ok(result);
        } catch (BadSqlGrammarException ex) {
            // Si la sintaxis falla, devolvemos el mensaje de error (información sensible).
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
