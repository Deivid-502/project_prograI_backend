package com.example.backend.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;

    public Product() { }
    public Product(int id, String n, String d, double p) {
        this.id = id; this.name = n; this.description = d; this.price = p;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String n) { this.name = n; }

    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }

    public double getPrice() { return price; }
    public void setPrice(double p) { this.price = p; }
}
