package com.example.backend.repository;

import java.sql.ResultSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.backend.model.Product;

@Repository
public class VulnerableRepository {
  @Autowired
  private JdbcTemplate jdbc;

  // SQL Injection: concatenación directa de user/pass
  public boolean validateUser(String user, String pass) {
    String sql = "SELECT COUNT(*) FROM users WHERE username = '" 
                 + user + "' AND password = '" + pass + "'";
    // <-- Vulnerable a SQL Injection
    Integer count = jdbc.queryForObject(sql, Integer.class);
    return count != null && count > 0;
  }

  // Listado de productos
  public List<Product> findAllProducts() {
    String sql = "SELECT id, name, description FROM products";
    return jdbc.query(sql, (ResultSet rs, int rowNum) -> 
      new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"))
    );
  }

  // SQL Injection: concatenación sin parámetros
  public void addProduct(String name, String desc) {
    String sql = "INSERT INTO products(name, description) VALUES ('" 
                 + name + "', '" + desc + "')";
    // <-- Vulnerable a SQL Injection
    jdbc.execute(sql);
  }

  public void updateProduct(int id, String name, String desc) {
    String sql = "UPDATE products SET name = '" + name 
                 + "', description = '" + desc 
                 + "' WHERE id = " + id;
    // <-- Vulnerable a SQL Injection
    jdbc.execute(sql);
  }

  public void deleteProduct(int id) {
    String sql = "DELETE FROM products WHERE id = " + id;
    // <-- Vulnerable a SQL Injection
    jdbc.execute(sql);
  }
}
