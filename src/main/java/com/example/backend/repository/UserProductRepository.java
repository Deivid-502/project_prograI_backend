package com.example.backend.repository;

import com.example.backend.dto.ProductRequest;
import com.example.backend.model.Product;
import com.example.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio “laboratorio de inyecciones”:
 * aquí concatenamos TODO sin sanitizar, para que el profe vea bien
 * cómo explotar SQL Injection en clase.
 */
@Repository
public class UserProductRepository {

    @Autowired
    private JdbcTemplate jdbc;

    /**
     * Valida credenciales de usuario.
     * Vulnerable a SQLi: si metes username=' OR '1'='1
     * te salta la validación.
     */
    public boolean validateUser(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE username='"
                + username + "' AND password='" + password + "'";
        Integer count = jdbc.queryForObject(sql, Integer.class);
        return count != null && count > 0;
    }

    /**
     * Lista todos los usuarios (¡ojo con exponer contraseñas!).
     */
    public List<User> findAllUsers() {
        String sql = "SELECT * FROM users";
        return jdbc.query(sql, (rs, rowNum) -> new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password")
        ));
    }

    /**
     * Crea un usuario.
     * Vulnerable porque concatenamos user.getUsername() directamente.
     */
    public User addUser(User user) {
        String sql = "INSERT INTO users(username,password) VALUES('"
                + user.getUsername() + "','" + user.getPassword() + "')";
        jdbc.update(sql);
        Integer id = jdbc.queryForObject("SELECT MAX(id) FROM users", Integer.class);
        return new User(id, user.getUsername(), user.getPassword());
    }

    /**
     * Lista todos los productos.
     */
    public List<Product> findAllProducts() {
        String sql = "SELECT * FROM products";
        return jdbc.query(sql, (rs, rowNum) -> new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price")
        ));
    }

    /**
     * Inserta un producto. Vulnerable a SQLi si pones comillas en el nombre.
     */
    public Product addProduct(ProductRequest r) {
        String sql = "INSERT INTO products(name,description,price) VALUES('"
                + r.getName() + "','" + r.getDescription() + "'," + r.getPrice() + ")";
        jdbc.update(sql);
        Integer id = jdbc.queryForObject("SELECT MAX(id) FROM products", Integer.class);
        return new Product(id, r.getName(), r.getDescription(), r.getPrice());
    }

    /**
     * Actualiza un producto por ID.
     * Aquí concatenamos TODO: si inyectas '); DROP TABLE products; --
     * ¡boom! tabla borrada.
     */
    public Product updateProduct(int id, ProductRequest r) {
        String sql = "UPDATE products SET name='"
                + r.getName() + "', description='"
                + r.getDescription() + "', price="
                + r.getPrice() + " WHERE id=" + id;
        jdbc.update(sql);
        return new Product(id, r.getName(), r.getDescription(), r.getPrice());
    }

    /**
     * Borra un producto por ID.
     * Si id = 1 OR 1=1, borras todo el catálogo.
     */
    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id=" + id;
        jdbc.update(sql);
    }

    /**
     * Busca productos por nombre usando LIKE.
     * Vulnerable: name='%anything%' concatenado sin filtro.
     */
    public List<Product> searchProductsByName(String name) {
        String sql = "SELECT * FROM products WHERE name LIKE '%" + name + "%'";
        return jdbc.query(sql, (rs, rowNum) -> new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDouble("price")
        ));
    }

    /**
     * Ejecuta cualquier SQL que le envíes.
     * ¡Solo para pruebas de inyección! En un mundo real, ni se te ocurra.
     */
    public List<?> executeArbitrarySql(String sql) {
        return jdbc.queryForList(sql);
    }
}
