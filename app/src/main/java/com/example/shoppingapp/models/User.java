package com.example.shoppingapp.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String id;
    private String email;
    private String password;
    private List<Cart> listCart;
    private List<Integer> listFavorite;

    public User(String email, String password, List<Cart> listCart, List<Integer> listFavorite) {
        this.email = email;
        this.password = password;
        this.listCart = listCart;
        this.listFavorite = listFavorite;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Integer> getListFavorite() {
        return listFavorite;
    }

    public void setListFavorite(List<Integer> listFavorite) {
        this.listFavorite = listFavorite;
    }

    public User(){}

    public User(String id,String email, String password) {
        this.id=id;
        this.email = email;
        this.password = password;
    }

    public List<Cart> getListCart() {
        return listCart;
    }

    public void setListCart(List<Cart> listCart) {
        this.listCart = listCart;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
