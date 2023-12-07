package com.example.shoppingapp.models;

import java.util.List;

public class Order {
    private String address;
    private String id;
    private List<Cart> listCart;
    private String nagy;
    private String ptThanhToan;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Cart> getListCart() {
        return listCart;
    }

    public void setListCart(List<Cart> listCart) {
        this.listCart = listCart;
    }

    public String getNagy() {
        return nagy;
    }

    public void setNagy(String nagy) {
        this.nagy = nagy;
    }

    public String getPtThanhToan() {
        return ptThanhToan;
    }

    public void setPtThanhToan(String ptThanhToan) {
        this.ptThanhToan = ptThanhToan;
    }

    public Order(String address, String id, List<Cart> listCart, String nagy, String ptThanhToan) {
        this.address = address;
        this.id = id;
        this.listCart = listCart;
        this.nagy = nagy;
        this.ptThanhToan = ptThanhToan;
    }
}
