package com.example.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.model.Product;
import com.example.backend.repository.VulnerableRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  @Autowired
  private VulnerableRepository repo;

  @GetMapping
  public List<Product> list() {
    return repo.findAllProducts();
  }

  @PostMapping
  public ResponseEntity<String> create(
      @RequestParam String name,
      @RequestParam String description) {
    repo.addProduct(name, description);
    return ResponseEntity.ok("Producto agregado");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> update(
      @PathVariable int id,
      @RequestParam String name,
      @RequestParam String description) {
    repo.updateProduct(id, name, description);
    return ResponseEntity.ok("Producto actualizado");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable int id) {
    repo.deleteProduct(id);
    return ResponseEntity.ok("Producto eliminado");
  }
}
