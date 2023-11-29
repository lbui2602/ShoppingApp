package com.example.shoppingapp.models;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
     private String id;
     private String name;
     private String price;
     private List<String> listImage;
     private int type;

    public Product(String id, String name, String price, List<String> listImage, int type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.listImage = listImage;
        this.type = type;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getListImage() {
        return listImage;
    }

    public void setListImage(List<String> listImage) {
        this.listImage = listImage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
