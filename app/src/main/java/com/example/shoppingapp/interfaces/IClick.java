package com.example.shoppingapp.interfaces;

import com.example.shoppingapp.models.Product;

public interface IClick {
    void onClick(Product product);
    void onClickFavorite(String productId);
}
