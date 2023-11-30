package com.example.shoppingapp.models;

public class CartCTO {
    private String name;
    private String price;
    private String productId;
    private String id;
    private String size;
    private int quantity;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public CartCTO(String name, String price, String productId, String id, String size, int quantity,String image) {
        this.name = name;
        this.price = price;
        this.productId = productId;
        this.id = id;
        this.size = size;
        this.quantity = quantity;
        this.image=image;
    }
}
