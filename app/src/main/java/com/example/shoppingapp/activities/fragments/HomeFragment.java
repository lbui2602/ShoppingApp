package com.example.shoppingapp.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.example.shoppingapp.adapters.HomeProductAdapter;
import com.example.shoppingapp.models.Product;
import com.example.shoppingapp.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment implements HomeProductAdapter.Click {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    SearchView searchView;
    Button btnAo,btnQuan,btnGiay,btnPhuKien,btnSport,btnDoLot;
    NavController navController;
    List<Product> list;
    HomeProductAdapter adapter;
    RecyclerView rcv;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                NavController navController= NavHostFragment.findNavController(HomeFragment.this);
                Bundle bundle=new Bundle();
                bundle.putString("key",s);
                navController.navigate(R.id.action_homeFragment_to_searchFragment,bundle);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        btnAo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("menu_id",1);
                bundle.putInt("back",100);
                navController.navigate(R.id.action_homeFragment_to_typeFragment,bundle);
            }
        });
        btnQuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("menu_id",2);
                bundle.putInt("back",100);
                navController.navigate(R.id.action_homeFragment_to_typeFragment,bundle);
            }
        });
        btnGiay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("menu_id",3);
                bundle.putInt("back",100);
                navController.navigate(R.id.action_homeFragment_to_typeFragment,bundle);
            }
        });
        btnPhuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("menu_id",4);
                bundle.putInt("back",100);
                navController.navigate(R.id.action_homeFragment_to_typeFragment,bundle);
            }
        });
        btnSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("menu_id",6);
                bundle.putInt("back",100);
                navController.navigate(R.id.action_homeFragment_to_typeFragment,bundle);
            }
        });
        btnDoLot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("menu_id",5);
                bundle.putInt("back",100);
                navController.navigate(R.id.action_homeFragment_to_typeFragment,bundle);
            }
        });
    }

    private void initView(View view) {
        searchView=view.findViewById(R.id.searchView);
        btnAo=view.findViewById(R.id.btnAo);
        btnQuan=view.findViewById(R.id.btnQuan);
        btnGiay=view.findViewById(R.id.btnGiay);
        btnPhuKien=view.findViewById(R.id.btnPhuKien);
        btnSport=view.findViewById(R.id.btnSport);
        btnDoLot=view.findViewById(R.id.btnDoLot);
        navController=NavHostFragment.findNavController(HomeFragment.this);
        rcv=view.findViewById(R.id.rcvHome);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        list=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Product product=dataSnapshot.getValue(Product.class);
                    if(Integer.parseInt(product.getId())%10==0){
                        list.add(product);
                    }
                }
                adapter=new HomeProductAdapter(list,getContext(),HomeFragment.this);
                rcv.setAdapter(adapter);
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
        bundle.putInt("back",100);
        bundle.putInt("menu_id",product.getType());
        navController.navigate(R.id.action_homeFragment_to_detailsFragment,bundle);
    }
}