package com.example.shoppingapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.shoppingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },1000);
    }

    private void nextActivity() {
//        FirebaseAuth.getInstance().signOut();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(intent);
        }else{
            Log.d("TAG", "nextActivity: "+user.getEmail());
            Intent intent=new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}