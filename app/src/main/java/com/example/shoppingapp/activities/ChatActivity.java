package com.example.shoppingapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.adapters.ChatAdapter;
import com.example.shoppingapp.models.Chat;
import com.example.shoppingapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView tvName;
    RecyclerView rcv;
    EditText edtChat;
    ImageButton imgBtnSend;
    FirebaseUser user;
    DatabaseReference databaseReference;
    Intent intent;
    ChatAdapter chatAdapter;
    List<Chat> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        user= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        intent=getIntent();
        String userId=intent.getStringExtra("user_id").trim();
        Log.d("TAG", "user: "+userId+""+user.getEmail());
        FirebaseDatabase.getInstance().getReference("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user1=snapshot.getValue(User.class);
                tvName.setText(user1.getEmail().toString());
                displayMess(user.getUid(),userId,"default");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        imgBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mess=edtChat.getText().toString().trim();
                if(mess.length()!=0){
                    sendMess(user.getUid(),userId,mess);
                    edtChat.setText("");
                }
            }
        });
    }

    private void initView() {
        tvName=findViewById(R.id.tvUSerNameInChat);
        rcv=findViewById(R.id.rcvInChat);
        edtChat=findViewById(R.id.edtChat);
        imgBtnSend=findViewById(R.id.imgBtnSend);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(ChatActivity.this,RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
    }
    private void sendMess(String sender,String receiver, String message){
        HashMap<String, Object> hashMap=new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);
        FirebaseDatabase.getInstance().getReference("chats").push().setValue(hashMap);
    }
    private void displayMess(String myId,String userId,String imgUrl){
        list=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Chat chat=dataSnapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                            chat.getReceiver().equals(userId) && chat.getSender().equals(myId)){
                        list.add(chat);
                    }
                }
                chatAdapter=new ChatAdapter(list,ChatActivity.this,"default");
                Log.d("TAG", "onDataChange: "+list.size());
                rcv.setAdapter(chatAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}