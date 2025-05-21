package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.repository.UserProductRepository;

import java.util.List;
import java.util.Map;

/**
 * Endpoint para enviar SQL arbitrario y ver resultados,
 * ideal para probar inyecciones desde Postman.
 */
@RestController
@RequestMapping("/api/debug")
public class QueryController {

    @Autowired
    private UserProductRepository repo;

    @PostMapping("/query")
    public ResponseEntity<List<Map<String, Object>>> runRawQuery(
            @RequestBody Map<String, String> body) {
        String sql = body.get("sql");
        // Vamos a devolver un JSON con el resultado o con un mensaje de error
        List<Map<String, Object>> result = repo.executeArbitrarySql(sql);
        return ResponseEntity.ok(result);
    }
}