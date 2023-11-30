package com.example.shoppingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.interfaces.IClick;
import com.example.shoppingapp.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    List<Product> list;
    Context context;
    IClick iClick;

    public ProductAdapter(List<Product> list, Context context,IClick iClick) {
        this.list = list;
        this.context=context;
        this.iClick=iClick;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context= parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product_item,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product=list.get(position);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user= auth.getCurrentUser();
        Glide.with(context).load(product.getListImage().get(0)).into(holder.imgAnh);
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText("đ"+product.getPrice()+".000");
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("favorites");
        databaseReference.orderByValue().equalTo(product.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    holder.imgTim.setImageResource(R.drawable.baseline_favorite_24);
                } else {
                    holder.imgTim.setImageResource(R.drawable.favorite_icon);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        holder.llItemProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClick.onClick(product);
            }
        });
        holder.imgTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClick.onClickFavorite(product.getId(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAnh,imgTim;
        TextView tvName,tvPrice;
        LinearLayout llItemProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnh=itemView.findViewById(R.id.imgAnh);
            imgTim=itemView.findViewById(R.id.imgTim);
            tvName=itemView.findViewById(R.id.tvName);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            llItemProduct=itemView.findViewById(R.id.llItemProduct);
        }

    }
}
