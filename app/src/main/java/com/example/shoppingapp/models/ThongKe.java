package com.example.shoppingapp.models;

public class ThongKe {
    String img;
    String name;
    int slg;

    public ThongKe(String img, String name, int slg) {
        this.img = img;
        this.name = name;
        this.slg = slg;
    }
    public ThongKe(){}

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSlg() {
        return slg;
    }

    public void setSlg(int slg) {
        this.slg = slg;
    }
}
