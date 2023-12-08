package com.example.shoppingapp.models;

import java.util.List;

public class DonHang {
    private String id;
    private List<Cart> listCart;
    private String address;
    private String ptThanhToan;
    private String ngay;
    private String time;
    private int sum;
    public DonHang (){}

    public DonHang(String id, List<Cart> listCart, String address, String ptThanhToan,String ngay,String time,int sum) {
        this.id = id;
        this.listCart = listCart;
        this.address = address;
        this.ptThanhToan = ptThanhToan;
        this.ngay=ngay;
        this.time=time;
        this.sum=sum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPtThanhToan() {
        return ptThanhToan;
    }

    public void setPtThanhToan(String ptThanhToan) {
        this.ptThanhToan = ptThanhToan;
    }
}
