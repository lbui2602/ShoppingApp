package com.example.shoppingapp.activities.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.example.shoppingapp.adapters.CartAdapter;
import com.example.shoppingapp.interfaces.IClick;
import com.example.shoppingapp.models.Cart;
import com.example.shoppingapp.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements IClick {
    List<Cart> list;
    Context context;
    CartAdapter cartAdapter;
    RecyclerView rcv;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbUser,dbProduct;
    FirebaseAuth auth;
    FirebaseUser user;
    Button btnThanhToan;
    static int sum;
    String email;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        loadData();
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sum>0){
                    NavController navController=NavHostFragment.findNavController(CartFragment.this);
                    Bundle bundle=new Bundle();
                    bundle.putInt("sum",sum);
                    bundle.putString("email",email);
                    navController.navigate(R.id.action_cartFragment_to_payFragment,bundle);
                }else{
                    Toast.makeText(getContext(), "Giỏ hàng của bạn chưa có gì!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void loadData() {
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        email=user.getEmail();
        firebaseDatabase=FirebaseDatabase.getInstance();
        dbUser=firebaseDatabase.getReference("users");
        dbProduct=firebaseDatabase.getReference("products");
        dbUser.child(user.getUid()).child("carts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sum=0;
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    Cart cart=productSnapshot.getValue(Cart.class);
                    list.add(cart);
                    cartAdapter.notifyDataSetChanged();
                    sum+=Integer.parseInt(cart.getProduct().getPrice())*cart.getQuantity();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "onCancelled1: "+error.getMessage());
            }
        });
    }
    private void initView(View view) {
        btnThanhToan=view.findViewById(R.id.btnThanhToan);
        rcv=view.findViewById(R.id.rcvCart);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        list=new ArrayList<>();
        cartAdapter=new CartAdapter(getContext(),list,CartFragment.this);
        rcv.setAdapter(cartAdapter);
    }
    @Override
    public void onClick(Product product) {

    }

    @Override
    public void onClickFavorite(Product product, int pos) {

    }

    @Override
    public void onClickDelete(String id,int price,int quantity,int pos) {
        dbUser.child(user.getUid()).child("carts").child(id).removeValue();
        list.remove(pos);
        cartAdapter.notifyDataSetChanged();
        sum=sum-(price*quantity);
    }

    @Override
    public void onClickCong(String id,int price, int pos) {
        dbUser.child(user.getUid()).child("carts").child(id).child("quantity").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int slg=snapshot.getValue(Integer.class);
                snapshot.getRef().setValue(slg+1);
                sum+=price;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClicktru(String id, int price, int pos) {
        dbUser.child(user.getUid()).child("carts").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int slg=snapshot.child("quantity").getValue(Integer.class);
                if (slg==1){
                    snapshot.getRef().setValue(null);
                }else{
                    snapshot.child("quantity").getRef().setValue(slg-1);
                }
                sum-=price;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}