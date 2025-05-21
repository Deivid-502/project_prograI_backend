package com.example.backend.service;

import com.example.backend.dto.ProductRequest;
import com.example.backend.model.Product;
import com.example.backend.repository.UserProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private UserProductRepository repo;

    public List<Product> getAll() {
        return repo.findAllProducts();
    }

    public Product add(ProductRequest r) {
        return repo.addProduct(r);
    }
}
