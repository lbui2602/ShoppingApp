package com.example.shoppingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.example.shoppingapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    TextView tvMessEmail2,tvMessPassword2,tvMessPasswordConfirm;
    EditText edtEmail2,edtPassword2,edtPasswordConfirm;
    Button btnRegister2;

    ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();
    }

    private void initListener() {
        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRegister();
            }
        });
    }

    private void onClickRegister() {
        String email=edtEmail2.getText().toString().trim();
        String password= edtPassword2.getText().toString().trim();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser= auth.getCurrentUser();
                            String id= currentUser.getUid();
                            User user=new User(id,email,password);
                            myRef.child(user.getId()).setValue(user);
                            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initView() {
        tvMessEmail2=findViewById(R.id.tvMessEmail2);
        tvMessPassword2=findViewById(R.id.tvMessPassword2);
        tvMessPasswordConfirm=findViewById(R.id.tvMessPasswordConfirm);
        btnRegister2=findViewById(R.id.btnRegister2);
        edtEmail2=findViewById(R.id.edtEmail2);
        edtPassword2=findViewById(R.id.edtPassword2);
        edtPasswordConfirm=findViewById(R.id.edtPasswordConfirm);
        progressDialog=new ProgressDialog(this);
        firebaseDatabase =FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference("users");
        auth=FirebaseAuth.getInstance();
    }
}