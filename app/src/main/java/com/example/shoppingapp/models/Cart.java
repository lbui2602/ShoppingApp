package com.example.shoppingapp.models;

import java.util.List;

public class Cart {
    private String id;
    private String productId;
    private int quantity;
    private String size;

    public Cart(String id, String productId, int quantity, String size) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Cart() {
    }


}
