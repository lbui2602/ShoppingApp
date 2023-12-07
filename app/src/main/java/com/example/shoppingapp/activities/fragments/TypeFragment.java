package com.example.shoppingapp.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.example.shoppingapp.adapters.ProductAdapter;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TypeFragment extends Fragment implements IClick {
    RecyclerView rcv;
    List<Product> list;
    ProductAdapter productAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseAuth auth;
    FirebaseUser user;
    ImageView imgBackType;
    NavController navController;
    int menuId,back;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TypeFragment newInstance(String param1, String param2) {
        TypeFragment fragment = new TypeFragment();
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
        return inflater.inflate(R.layout.fragment_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv=view.findViewById(R.id.rcv);
        navController = NavHostFragment.findNavController(TypeFragment.this);
        imgBackType=view.findViewById(R.id.imgBackType);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        rcv.setLayoutManager(gridLayoutManager);
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference("products");
        list=new ArrayList<>();
        productAdapter=new ProductAdapter(list,getContext(),TypeFragment.this);
        menuId=getArguments().getInt("menu_id");
        back=getArguments().getInt("back");
        imgBackType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(back==300){
                    navController.navigate(R.id.action_typeFragment_to_menuFragment);
                }else if(back==100){
                    navController.navigate(R.id.action_typeFragment_to_homeFragment);
                }
            }
        });
        if(menuId==7){
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                        Product product = productSnapshot.getValue(Product.class);
                        list.add(product);
                    }
                    Log.d("TAG", "onDataChange: "+list.get(2).getId());
                    productAdapter.notifyDataSetChanged();
                    rcv.setAdapter(productAdapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            Query query = myRef.orderByChild("type").equalTo(menuId);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        list.add(product);
                    }
                    productAdapter.notifyDataSetChanged();
                    rcv.setAdapter(productAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void onClick(Product product) {
        Bundle bundle=new Bundle();
        bundle.putString("product_id", product.getId());
        if(back==100){
            bundle.putInt("back",1000);
        }else{
            bundle.putInt("back",menuId);
        }
        bundle.putInt("menu_id",product.getType());
        navController.navigate(R.id.action_typeFragment_to_detailsFragment,bundle);
    }

    @Override
    public void onClickFavorite(Product product,int pos) {
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("favorites").child(product.getId());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    snapshot.getRef().setValue(null);
                }else{
                    databaseReference.setValue(product);
                }
                productAdapter.notifyItemChanged(pos);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onClickDelete(String id, int price, int quantity, int pos) {

    }

    @Override
    public void onClickCong(String id, int price, int pos) {

    }

    @Override
    public void onClicktru(String id, int price, int pos) {

    }


}