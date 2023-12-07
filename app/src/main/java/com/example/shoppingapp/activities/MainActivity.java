package com.example.shoppingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.example.shoppingapp.activities.fragments.UserFragment;
import com.example.shoppingapp.models.Cart;
import com.example.shoppingapp.models.Product;
import com.example.shoppingapp.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserFragment.onClickLogout {
    BottomNavigationView bnvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        bnvMain=findViewById(R.id.bnvMain);
        NavHostFragment navHostFragment= (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController=navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bnvMain,navController);
    }

    @Override
    public void onLogout() {
        Log.d("TAG", "onLogout: ");
        finish();
    }
}