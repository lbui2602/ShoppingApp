package com.example.shoppingapp.models;

import java.util.List;

public class Cart {
    private String id;
    private Product product;
    private String size;
    private int quantity;

    public Cart() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Cart(String id, Product product, String size, int quantity) {
        this.id = id;
        this.product = product;
        this.size = size;
        this.quantity = quantity;
    }
}
