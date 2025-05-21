package com.example.backend.controller;

import com.example.backend.dto.ProductRequest;
import com.example.backend.model.Product;
import com.example.backend.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador de productos, con menos formalidad y más trampas.
@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private UserProductRepository repo;

  /**
   * GET todos.
   */
  @GetMapping
  public List<Product> all() {
    return repo.findAllProducts();
  }

  /**
   * POST crea. Vulnerable.
   */
  @PostMapping
  public Product add(@RequestBody ProductRequest req) {
    return repo.addProduct(req);
  }

  /**
   * DELETE mux de ejemplo.
   */
  @DeleteMapping("/{id}")
  public String delete(@PathVariable int id) {
    // Omitimos repo.delete para dejar vacío y que fallen intenciones.
    return "Este borrado no está implementado, ¡intenta con otro endpoint!";
  }
}
