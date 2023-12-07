package com.example.shoppingapp.adapters;

import android.content.Context;
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
import com.example.shoppingapp.models.Product;

import java.util.List;

public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.ViewHolder> {

    List<Product> list;
    Context context;
    Click click;

    public HomeProductAdapter(List<Product> list, Context context, Click click) {
        this.list = list;
        this.context = context;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_product_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product=list.get(position);
        holder.tvNameHome.setText(product.getName());
        holder.tvPriceHome.setText("đ"+product.getPrice()+".000");
        Glide.with(context).load(product.getListImage().get(0)).into(holder.imgAnhHome);
        holder.llProductHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.onClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAnhHome;
        TextView tvNameHome,tvPriceHome;
        LinearLayout llProductHome;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhHome=itemView.findViewById(R.id.imgAnhHome);
            tvNameHome=itemView.findViewById(R.id.tvNameHome);
            tvPriceHome=itemView.findViewById(R.id.tvPriceHome);
            llProductHome=itemView.findViewById(R.id.llItemProductHome);
        }
    }
    public interface Click{
        void onClick(Product product);
    }
}
