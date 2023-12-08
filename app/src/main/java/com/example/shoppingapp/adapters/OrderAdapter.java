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
import com.example.shoppingapp.models.DonHang;
import com.example.shoppingapp.models.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    List<DonHang> list;
    Context context;

    public OrderAdapter(List<DonHang> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang order=list.get(position);
        holder.tvMa.setText(order.getId());
        holder.tvPTTT.setText(order.getPtThanhToan());
        holder.tvNgay.setText(order.getNgay());
        holder.tvGio.setText(order.getTime());
        holder.tvSum.setText("đ"+String.valueOf(order.getSum())+".000");
        holder.tvDiaChi.setText(order.getAddress());
        String sp="";
        for(int i=0;i<order.getListCart().size();i++){
            if(i==order.getListCart().size()-1){
                sp=sp+order.getListCart().get(i).getProduct().getName()+" - "+order.getListCart().get(i).getQuantity();
            }else{
                sp=sp+order.getListCart().get(i).getProduct().getName()+" - "+order.getListCart().get(i).getQuantity()+"\n";
            }
        }
        holder.tvSP.setText(sp);
        Glide.with(context).load(order.getListCart().get(0).getProduct().getListImage().get(0)).into(holder.imgHistory);
    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHistory;
        TextView tvMa,tvDiaChi,tvSP,tvNgay,tvGio,tvPTTT,tvSum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHistory=itemView.findViewById(R.id.imgHistory);
            tvMa=itemView.findViewById(R.id.tvMaDonHang);
            tvDiaChi=itemView.findViewById(R.id.tvDiaChi);
            tvSP=itemView.findViewById(R.id.tvSanPham);
            tvNgay=itemView.findViewById(R.id.tvNgay);
            tvGio=itemView.findViewById(R.id.tvGio);
            tvPTTT=itemView.findViewById(R.id.tvPTThanhToan);
            tvSum=itemView.findViewById(R.id.tvTongTien);
        }
    }
}
