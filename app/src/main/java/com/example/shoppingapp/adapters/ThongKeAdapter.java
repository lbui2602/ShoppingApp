package com.example.shoppingapp.adapters;

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
import com.example.shoppingapp.models.ThongKe;

import java.util.List;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.ViewHolder> {
    List<ThongKe> list;
    Context context;

    public ThongKeAdapter(List<ThongKe> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_thong_ke,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThongKe thongKe=list.get(position);
        Glide.with(context).load(thongKe.getImg()).into(holder.img);
        holder.tvName.setText(thongKe.getName());
        holder.tvSlg.setText(thongKe.getSlg()+"");
    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView tvName;
        TextView tvSlg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.imgThongKe);
            tvName=itemView.findViewById(R.id.tvNameThongKe);
            tvSlg=itemView.findViewById(R.id.tvSoLuongThongKe);
        }
    }
}
