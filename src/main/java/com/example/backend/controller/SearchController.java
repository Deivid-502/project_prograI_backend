package com.example.backend.controller;

import com.example.backend.model.Product;
import com.example.backend.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoint para demostrar vulnerabilidad de búsqueda (SQLi con LIKE).
 * Recibe un parámetro 'name' y lo concatena directo en la consulta.
 */
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private UserProductRepository repo;

    /**
     * GET /api/search/products?name=<algo>
     * @param name texto a buscar dentro del nombre del producto.
     * @return lista de productos que contengan el texto.
     * Vulnerable a SQL Injection si se usan comillas.
     */
    @GetMapping("/products")
    public List<Product> searchProducts(@RequestParam String name) {
        return repo.searchProductsByName(name);
    }
}