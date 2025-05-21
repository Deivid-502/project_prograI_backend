package com.example.backend.dto;

/**
 * DTO para operaciones de producto: creación y actualización.
 * Recibe nombre, descripción y precio desde el cliente.
 */
public class ProductRequest {
    private String name;
    private String description;
    private double price;

    public ProductRequest() {}

    public String getName() { return name; }
    public void setName(String n) { this.name = n; }

    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }

    public double getPrice() { return price; }
    public void setPrice(double p) { this.price = p; }
}