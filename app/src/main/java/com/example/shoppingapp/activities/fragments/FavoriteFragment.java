package com.example.shoppingapp.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.shoppingapp.repositories.ProductRepository;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment implements IClick {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView rcv;
    List<Product> list;
    ProductAdapter productAdapter;
    ProductRepository productRepository;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseAuth auth;
    FirebaseUser user;
    ImageView imgBackType;
    NavController navController;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
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
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv=view.findViewById(R.id.rcvFavorite);
        navController = NavHostFragment.findNavController(FavoriteFragment.this);
        imgBackType=view.findViewById(R.id.imgBackType);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        rcv.setLayoutManager(gridLayoutManager);
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference("users");
        list=new ArrayList<>();
        productAdapter=new ProductAdapter(list,getContext(),FavoriteFragment.this);
        rcv.setAdapter(productAdapter);
//        imgBackType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                navController.navigate(R.id.action_typeFragment_to_menuFragment);
//            }
//        });
        loadData();
    }
    private void loadData() {
        myRef.child(user.getUid()).child("favorites").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot productSnapshot : snapshot.getChildren()) {
                    String productId=productSnapshot.getValue(String.class);
                    FirebaseDatabase.getInstance().getReference("products").child(productId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Product product=snapshot.getValue(Product.class);
                            list.add(product);
                            productAdapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(Product product) {
        Bundle bundle=new Bundle();
        bundle.putString("product_id", product.getId());
        bundle.putInt("back",122);
        bundle.putInt("menu_id",product.getType());
        navController.navigate(R.id.action_typeFragment_to_detailsFragment,bundle);
    }

    @Override
    public void onClickFavorite(String productId,int pos) {
        list.remove(pos);
        Toast.makeText(getContext(), ""+list.size(), Toast.LENGTH_SHORT).show();
        productAdapter.notifyDataSetChanged();
        myRef.child(user.getUid()).child("favorites").child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().setValue(null);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}