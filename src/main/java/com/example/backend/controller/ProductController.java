package com.example.backend.controller;

import com.example.backend.dto.ProductRequest;
import com.example.backend.model.Product;
import com.example.backend.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Endpoints de productos. Aquí vemos CRUD completo,
 * con vulnerabilidades a propósito y manejo de errores.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private UserProductRepository repo;

  // Listar todos
  @GetMapping
  public List<Product> all() {
    return repo.findAllProducts();
  }

  // Crear nuevo (vulnerable)
  @PostMapping
  public ResponseEntity<Product> add(@RequestBody ProductRequest req) {
    Product p = repo.addProduct(req);
    return p != null
            ? ResponseEntity.ok(p)
            : ResponseEntity.badRequest().build();
  }

  // Obtener uno (para edición)
  @GetMapping("/{id}")
  public ResponseEntity<Product> getOne(@PathVariable int id) {
    Product p = repo.getProductById(id);
    return p != null
            ? ResponseEntity.ok(p)
            : ResponseEntity.notFound().build();
  }

  // Actualizar (vulnerable)
  @PutMapping("/{id}")
  public ResponseEntity<Product> update(
          @PathVariable int id,
          @RequestBody ProductRequest req) {
    Product p = repo.updateProduct(id, req);
    return p != null
            ? ResponseEntity.ok(p)
            : ResponseEntity.badRequest().build();
  }

  // Borrar (vulnerable)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable int id) {
    boolean ok = repo.deleteProduct(id);
    return ok
            ? ResponseEntity.noContent().build()
            : ResponseEntity.badRequest().build();
  }
}