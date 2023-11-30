package com.example.shoppingapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoppingapp.R;
import com.example.shoppingapp.interfaces.IClick;
import com.example.shoppingapp.models.CartCTO;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    List<CartCTO> list;
    IClick iClick;

    public CartAdapter(Context context, List<CartCTO> list,IClick iClick) {
        this.context = context;
        this.list = list;
        this.iClick=iClick;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_cart,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CartCTO cartCTO=list.get(position);
        holder.tvNameCart.setText(cartCTO.getName());
        holder.tvKichThuocCart.setText(cartCTO.getSize());
        holder.tvPriceCart.setText("đ"+cartCTO.getPrice()+".000");
        holder.tvSoLuongCart.setText(String.valueOf(cartCTO.getQuantity()));
        holder.tvProductIdCart.setText(cartCTO.getProductId());
        Glide.with(context).load(cartCTO.getImage()).into(holder.imgAnhCart);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClick.onClickDelete(cartCTO.getId(),Integer.parseInt(cartCTO.getPrice()), cartCTO.getQuantity(), position);
            }
        });
        holder.imgCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tvSoLuongCart.setText(String.valueOf(Integer.parseInt(holder.tvSoLuongCart.getText().toString())+1));
                iClick.onClickCong(cartCTO.getId(),Integer.parseInt(cartCTO.getPrice()),position);
            }
        });
        holder.imgTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(holder.tvSoLuongCart.getText().toString())==1){
                    list.remove(position);
                    notifyDataSetChanged();
                    iClick.onClicktru(cartCTO.getId(),Integer.parseInt(cartCTO.getPrice()),position);
                }
                else{
                    holder.tvSoLuongCart.setText(String.valueOf(Integer.parseInt(holder.tvSoLuongCart.getText().toString())-1));
                    iClick.onClicktru(cartCTO.getId(),Integer.parseInt(cartCTO.getPrice()),position);
                }
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

    public class CartViewHolder extends RecyclerView.ViewHolder{
        TextView tvNameCart,tvPriceCart,tvProductIdCart,tvKichThuocCart,tvSoLuongCart;
        ImageView imgAnhCart,imgCong,imgTru,imgDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCart=itemView.findViewById(R.id.tvNameCart);
            tvPriceCart=itemView.findViewById(R.id.tvPriceCart);
            tvProductIdCart=itemView.findViewById(R.id.tvProductIdCart);
            tvKichThuocCart=itemView.findViewById(R.id.tvSizeCart);
            tvSoLuongCart=itemView.findViewById(R.id.tvSoLuong);
            imgAnhCart=itemView.findViewById(R.id.imgAnhCart);
            imgCong=itemView.findViewById(R.id.imgCong);
            imgTru=itemView.findViewById(R.id.imgTru);
            imgDelete=itemView.findViewById(R.id.imgDelete);
        }
    }
}
