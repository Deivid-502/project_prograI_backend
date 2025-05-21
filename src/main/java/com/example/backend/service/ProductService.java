package com.example.backend.service;

import com.example.backend.dto.ProductRequest;
import com.example.backend.model.Product;
import com.example.backend.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio de productos: puente entre controlador y repositorio.
 * Aquí tenemos métodos para obtener, agregar y editar productos,
 * dejando todo listo para que el controller lo exponga.
 */
@Service
public class ProductService {

    @Autowired
    private UserProductRepository repo;

    /**
     * Devuelve la lista completa de productos.
     */
    public List<Product> getAll() {
        return repo.findAllProducts();
    }

    /**
     * Agrega un producto nuevo a través del repositorio.
     * @param r datos del producto desde el cliente.
     * @return el producto creado con su ID.
     */
    public Product add(ProductRequest r) {
        return repo.addProduct(r);
    }
}