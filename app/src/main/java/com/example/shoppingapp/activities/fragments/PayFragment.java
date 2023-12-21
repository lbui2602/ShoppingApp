package com.example.shoppingapp.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.example.shoppingapp.models.Cart;
import com.example.shoppingapp.models.DonHang;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView tvEmailPay,tvGiaTriPay,tvShipPay,tvSumPay;
    EditText edtTP,edtHuyen,edtXa,edtSoNha;
    RadioButton radioNhanHang,radioCK;
    Button btnConfirmPay;
    ImageView imgBack;
    int sum;
    static List<Cart> list;

    public PayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayFragment newInstance(String param1, String param2) {
        PayFragment fragment = new PayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pay, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        btnConfirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tp=edtTP.getText().toString().trim();
                String huyen=edtHuyen.getText().toString().trim();
                String xa=edtXa.getText().toString().trim();
                String soNha=edtSoNha.getText().toString().trim();

                if(tp.length()>0 && huyen.length()>0 && xa.length()>0 && soNha.length()>0 && (radioCK.isChecked() || radioNhanHang.isChecked())){
                    String address = soNha+", "+xa+", "+huyen+ ", "+tp;
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    Date currentTime = calendar.getTime();
                    String time = timeFormat.format(currentTime);
                    String ptThanhToan="";
                    if(radioNhanHang.isChecked()){
                        ptThanhToan+="Thanh toán khi nhận hàng";
                    }
                    if(radioCK.isChecked()){
                        ptThanhToan+="Chuyển khoản";
                    }
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    String finalPtThanhToan = ptThanhToan;
                    list=new ArrayList<>();
                    FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.child("carts").getChildren()) {
                                Cart cart = dataSnapshot.getValue(Cart.class);
                                list.add(cart);
                                FirebaseDatabase.getInstance().getReference("spbc").child(cart.getProduct().getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            int value=snapshot.child("slg").getValue(Integer.class);
                                            int slg=value+cart.getQuantity();
                                            snapshot.child("slg").getRef().setValue(slg);
                                        }else{
                                            snapshot.child("slg").getRef().setValue(cart.getQuantity());
                                            snapshot.child("sp").getRef().setValue(cart.getProduct());
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            DonHang donHang = new DonHang(user.getUid() + day + month + year+ time , list, address, finalPtThanhToan, day + "-" + month + "-" + year,time,sum);
                            FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("orders").child(user.getUid() + day + month + year+time).setValue(donHang);
                            FirebaseDatabase.getInstance().getReference("doanhso").child(month+"-"+year+"").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        int value=snapshot.child("ds").getValue(Integer.class);
                                        int ds=(Integer) value+donHang.getSum();
                                        snapshot.child("ds").getRef().setValue(ds);
                                    }else{
                                        snapshot.child("ds").getRef().setValue(donHang.getSum());
                                        snapshot.child("thang").getRef().setValue(month+"-"+year);
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
//                            for(int i=0;i<donHang.getListCart().size();i++){
////                                FirebaseDatabase.getInstance().getReference("spbc").child(donHang.getListCart().get(i).getProduct().getId()).child(("sp")).setValue(donHang.getListCart().get(i).getProduct());
//                                int finalI = i;
//                                FirebaseDatabase.getInstance().getReference("spbc").child(donHang.getListCart().get(i).getProduct().getId()).addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        if(snapshot.exists()){
//                                            int value=snapshot.child("slg").getValue(Integer.class);
//                                            int slg=value+donHang.getListCart().get(finalI).getQuantity();
//                                            snapshot.child("slg").getRef().setValue(slg);
//                                        }else{
//                                            snapshot.child("slg").getRef().setValue(donHang.getListCart().get(finalI).getQuantity());
//                                            snapshot.child("ds").getRef().setValue(donHang.getListCart().get(finalI).getProduct());
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
//                            }
                            NavController navController = NavHostFragment.findNavController(PayFragment.this);
                            navController.navigate(R.id.action_payFragment_to_cartFragment);
                            FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("carts").removeValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Toast.makeText(getContext(), "Đặt hàng thành công.", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView(View view) {
        imgBack=view.findViewById(R.id.imgBackPay);
        tvEmailPay=view.findViewById(R.id.tvEmailPay);
        tvGiaTriPay=view.findViewById(R.id.tvGiaTriDonPay);
        tvShipPay=view.findViewById(R.id.tvShipPay);
        tvSumPay=view.findViewById(R.id.tvSum);
        edtTP=view.findViewById(R.id.edtTP);
        edtHuyen=view.findViewById(R.id.edtHuyen);
        edtXa=view.findViewById(R.id.edtXa);
        edtSoNha=view.findViewById(R.id.edtSoNha);
        radioNhanHang=view.findViewById(R.id.radioNhanHang);
        radioCK=view.findViewById(R.id.radioChuyenKhoan);
        btnConfirmPay=view.findViewById(R.id.btnConfirmPay);
        sum=getArguments().getInt("sum");

        tvEmailPay.setText(getArguments().getString("email"));
        tvGiaTriPay.setText("đ"+sum+".000");
        if(sum>=3000){
            tvShipPay.setText("Miễn phí giao hàng");
            tvSumPay.setText("đ"+sum+".000");
        }else{
            tvShipPay.setText("đ30.000");
            tvSumPay.setText("đ"+(sum+30)+".000");
        }
        NavController navController= NavHostFragment.findNavController(PayFragment.this);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_payFragment_to_cartFragment);
            }
        });

    }
}