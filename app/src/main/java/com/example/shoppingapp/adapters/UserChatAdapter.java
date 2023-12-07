package com.example.shoppingapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.activities.fragments.UserChatFragment;
import com.example.shoppingapp.models.User;

import java.util.List;

public class UserChatAdapter extends RecyclerView.Adapter<UserChatAdapter.UserChatViewHolder> {
    List<User> list;
    Context context;
    IClickChatUser iClickChatUser;

    public UserChatAdapter(List<User> list, Context context,IClickChatUser iClickChatUser) {
        this.list = list;
        this.context = context;
        this.iClickChatUser=iClickChatUser;
    }

    @NonNull
    @Override
    public UserChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_item_chat,parent,false);
        return new UserChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserChatViewHolder holder, int position) {
        User user=list.get(position);
        holder.tvUserNameChat.setText(user.getEmail());
        holder.imgUserChat.setImageResource(R.drawable.ic_launcher_background);
        holder.llUserChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickChatUser.onClick(user.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }

    public class UserChatViewHolder extends RecyclerView.ViewHolder{
        TextView tvUserNameChat;
        ImageView imgUserChat;
        LinearLayout llUserChat;
        public UserChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserNameChat=itemView.findViewById(R.id.tvUserNameChat);
            imgUserChat=itemView.findViewById(R.id.imgUserChat);
            llUserChat=itemView.findViewById(R.id.llUserChat);
        }
    }
    public interface IClickChatUser{
        void onClick(String userId);
    }
}
