package com.example.shoppingapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.example.shoppingapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    TextView tvMessEmail,tvMessPassword;
    EditText edtEmail,edtPassword;
    Button btnLogin,btnRegister;
    ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user");
        initListener();
    }

    private void initListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtEmail.getText().toString().trim().length()==0){
                    tvMessEmail.setVisibility(View.VISIBLE);
                }else{
                    tvMessEmail.setVisibility(View.GONE);
                }

                if(edtPassword.getText().toString().trim().length()==0){
                    tvMessPassword.setVisibility(View.VISIBLE);
                }
                else{
                    tvMessPassword.setVisibility(View.GONE);
                }
                if(edtEmail.getText().toString().trim().length()>0 && edtPassword.getText().toString().trim().length()>0){
                    tvMessEmail.setVisibility(View.GONE);
                    tvMessPassword.setVisibility(View.GONE);
                    progressDialog.show();
                    String email=edtEmail.getText().toString().trim();
                    String password=edtPassword.getText().toString().trim();
                    FirebaseAuth auth=FirebaseAuth.getInstance();
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finishAffinity();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin đăng nhập.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initView() {
        tvMessEmail=findViewById(R.id.tvMessEmail);
        tvMessPassword=findViewById(R.id.tvMessPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);
        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);
        progressDialog=new ProgressDialog(this);
    }
}