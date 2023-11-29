package com.example.shoppingapp.repositories;

import androidx.annotation.NonNull;

import com.example.shoppingapp.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ProductRepository {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    public ProductRepository() {
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.myRef = firebaseDatabase.getReference("products");
    }
}
