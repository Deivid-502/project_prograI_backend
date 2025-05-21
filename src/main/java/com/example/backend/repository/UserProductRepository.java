package com.example.backend.repository;

import com.example.backend.dto.ProductRequest;
import com.example.backend.model.Product;
import com.example.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Repositorio para demostraciones de SQL Injection.
 * Aquí mantenemos la vulnerabilidad, pero atrapamos errores para que
 * la app no se caiga si la sintaxis es incorrecta.
 */
@Repository
public class UserProductRepository {

    @Autowired
    private JdbcTemplate jdbc;

    // Valida credenciales: vulnerable a SQLi, ejemplo: username=' OR '1'='1
    public boolean validateUser(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE username='"
                + username + "' AND password='" + password + "'";
        try {
            Integer count = jdbc.queryForObject(sql, Integer.class);
            return count != null && count > 0;
        } catch (BadSqlGrammarException ex) {
            // No rompemos nada, simplemente consideramos que no validó
            return false;
        }
    }

    // Listar todos los usuarios (ojo, expone contraseñas en texto claro)
    public List<User> findAllUsers() {
        String sql = "SELECT * FROM users";
        try {
            return jdbc.query(sql, (rs, rowNum) -> new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password")
            ));
        } catch (Exception ex) {
            // Algo raro pasó, devolvemos lista vacía en lugar de crash
            return Collections.emptyList();
        }
    }

    // Crear usuario: vulnerable porque concatenamos sin filtrar
    public User addUser(User user) {
        String sql = "INSERT INTO users(username,password) VALUES('"
                + user.getUsername() + "','" + user.getPassword() + "')";
        try {
            jdbc.update(sql);
            Integer id = jdbc.queryForObject("SELECT MAX(id) FROM users", Integer.class);
            return new User(id, user.getUsername(), user.getPassword());
        } catch (Exception ex) {
            // Si falla, devolvemos null o podríamos lanzar excepcion custom
            return null;
        }
    }

    // Listar productos
    public List<Product> findAllProducts() {
        String sql = "SELECT * FROM products";
        try {
            return jdbc.query(sql, (rs, rowNum) -> new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price")
            ));
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    // Crear producto: vulnerable a SQLi si pones comillas en el nombre
    public Product addProduct(ProductRequest r) {
        String sql = "INSERT INTO products(name,description,price) VALUES('"
                + r.getName() + "','" + r.getDescription() + "'," + r.getPrice() + ")";
        try {
            jdbc.update(sql);
            Integer id = jdbc.queryForObject("SELECT MAX(id) FROM products", Integer.class);
            return new Product(id, r.getName(), r.getDescription(), r.getPrice());
        } catch (Exception ex) {
            return null;
        }
    }

    // Obtener producto por ID (para edición)
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id=" + id;
        try {
            return jdbc.queryForObject(sql, (rs, rowNum) -> new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price")
            ));
        } catch (Exception ex) {
            return null;
        }
    }

    // Actualizar producto: vulnerable a SQLi, ejemplo: name=''); DROP TABLE products; --
    public Product updateProduct(int id, ProductRequest r) {
        String sql = "UPDATE products SET name='"
                + r.getName() + "', description='" + r.getDescription()
                + "', price=" + r.getPrice() + " WHERE id=" + id;
        try {
            jdbc.update(sql);
            return new Product(id, r.getName(), r.getDescription(), r.getPrice());
        } catch (Exception ex) {
            return null;
        }
    }

    // Borrar producto: vulnerable a SQLi, ejemplo: id=1 OR 1=1
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id=" + id;
        try {
            jdbc.update(sql);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    // Búsqueda por nombre: vulnerable a SQLi con LIKE
    public List<Product> searchProductsByName(String name) {
        String sql = "SELECT * FROM products WHERE name LIKE '%" + name + "%'";
        try {
            return jdbc.query(sql, (rs, rowNum) -> new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price")
            ));
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    // Ejecuta SQL arbitrario (sólo para demostración)
    public List<Map<String, Object>> executeArbitrarySql(String sql) {
        try {
            return jdbc.queryForList(sql);
        } catch (BadSqlGrammarException ex) {
            // Devolvemos mensaje de error si la sintaxis está mal
            return Collections.singletonList(
                    Map.of("error", ex.getMessage())
            );
        }
    }
}