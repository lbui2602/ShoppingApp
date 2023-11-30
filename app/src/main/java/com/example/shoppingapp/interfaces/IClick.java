package com.example.shoppingapp.interfaces;

import com.example.shoppingapp.models.Product;

public interface IClick {
    void onClick(Product product);
    void onClickFavorite(String productId,int pos);
    void onClickDelete(String id,int pos);
    void onClickCong(String id,int quantity,int pos);
    void onClicktru(String id,int quantity,int pos);
}
