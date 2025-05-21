package com.example.backend.controller;

import com.example.backend.model.Product;
import com.example.backend.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador para buscar productos por nombre.
// Aquí demostramos cómo una simple búsqueda puede ser vulnerable.
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private UserProductRepository repo;

    /**
     * GET /api/search/products?name=<algo>
     * Vulnerable a SQLi en el parámetro 'name'.
     */
    @GetMapping("/products")
    public List<Product> searchProducts(@RequestParam String name) {
        // Si enviamos name=%' OR '1'='1, recup libretazo todo.
        return repo.searchProductsByName(name);
    }
}
