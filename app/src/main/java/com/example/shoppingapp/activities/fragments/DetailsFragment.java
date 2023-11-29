package com.example.shoppingapp.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.shoppingapp.R;
import com.example.shoppingapp.models.Cart;
import com.example.shoppingapp.models.Product;
import com.google.android.material.slider.Slider;
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
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<SlideModel> list=new ArrayList<SlideModel>();
    ImageSlider imageSlider;
    TextView tvName,tvPrice;
    ImageView imgBackDetails;
    NavController navController;
    LinearLayout llSize;
    ImageView imgFavoriteDetails;
    RadioButton radioS,radioM,radioL,radioXL,radio38,radio39,radio40,radio41,radio42,radio43;
    RadioGroup rgSize;
    Button btnAddToCart;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    DatabaseReference myRef2;
    FirebaseAuth auth;
    FirebaseUser user;
    String size;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
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
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController= NavHostFragment.findNavController(DetailsFragment.this);
        String productId=getArguments().getString("product_id");
        int back=getArguments().getInt("back");
        int menuId=getArguments().getInt("menu_id");
        initView(view);
        initData();
        if(menuId==4){
            radioL.setVisibility(View.GONE);
            radioS.setVisibility(View.GONE);
            radioM.setVisibility(View.GONE);
            radioXL.setVisibility(View.GONE);
            radio38.setVisibility(View.GONE);
            radio39.setVisibility(View.GONE);
            radio40.setVisibility(View.GONE);
            radio41.setVisibility(View.GONE);
            radio42.setVisibility(View.GONE);
            radio43.setVisibility(View.GONE);
        }else if(menuId==3){
            radioL.setVisibility(View.GONE);
            radioS.setVisibility(View.GONE);
            radioM.setVisibility(View.GONE);
            radioXL.setVisibility(View.GONE);
        }else{
            radio38.setVisibility(View.GONE);
            radio39.setVisibility(View.GONE);
            radio40.setVisibility(View.GONE);
            radio41.setVisibility(View.GONE);
            radio42.setVisibility(View.GONE);
            radio43.setVisibility(View.GONE);
        }
        myRef2.child(user.getUid()).child("favorites").child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    imgFavoriteDetails.setImageResource(R.drawable.baseline_favorite_24);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        imgFavoriteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef2.child(user.getUid()).child("favorites").child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            myRef2.child(user.getUid()).child("favorites").child(productId).setValue(null);
                            imgFavoriteDetails.setImageResource(R.drawable.favorite_icon);
                        }else{
                            myRef2.child(user.getUid()).child("favorites").child(productId).setValue(productId);
                            imgFavoriteDetails.setImageResource(R.drawable.baseline_favorite_24);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        imgBackDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("menu_id",back);
                navController.navigate(R.id.action_detailsFragment_to_typeFragment,bundle);
            }
        });
        myRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product product=snapshot.getValue(Product.class);
                for(int i=0;i<product.getListImage().size();i++){
                    list.add(new SlideModel(product.getListImage().get(i), ScaleTypes.FIT));
                }
                imageSlider.setImageList(list);
                tvName.setText(product.getName().toString());
                tvPrice.setText(product.getPrice().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(menuId==4){
                    size="";
                }else if(menuId==3){
                    if(radio38.isChecked()){
                        size="38";
                    }
                    if(radio39.isChecked()){
                        size="39";
                    }
                    if(radio40.isChecked()){
                        size="40";
                    }
                    if(radio41.isChecked()){
                        size="41";
                    }
                    if(radio42.isChecked()){
                        size="42";
                    }
                    if(radio43.isChecked()){
                        size="43";
                    }
                }else{
                    if(radioS.isChecked()){
                        size="S";
                    }
                    if(radioM.isChecked()){
                        size="M";
                    }
                    if(radioL.isChecked()){
                        size="L";
                    }
                    if(radioXL.isChecked()){
                        size="XL";
                    }
                }
                if (size!=null){
                    String id=productId+size;
                    myRef2.child(user.getUid()).child("carts").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Cart cart;
                            if(snapshot.exists()){
                                cart = snapshot.getValue(Cart.class);
                                cart.setQuantity(cart.getQuantity()+1);
                                myRef2.child(user.getUid()).child("carts").child(id).setValue(cart);
                            }
                            else{
                                cart = new Cart(id,productId,1,size);
                                myRef2.child(user.getUid()).child("carts").child(id).setValue(cart);
                            }
                            Toast.makeText(getContext(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(getContext(), "Vui lòng chọn size", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initData() {
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef= firebaseDatabase.getReference("products");
        myRef2= firebaseDatabase.getReference("users");
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
    }

    private void initView(View view) {
        imageSlider=view.findViewById(R.id.image_slider);
        tvName=view.findViewById(R.id.tvNameDetail);
        tvPrice=view.findViewById(R.id.tvPriceDetail);
        imgFavoriteDetails=view.findViewById(R.id.imgFavoriteDetails);
        imgBackDetails=view.findViewById(R.id.imgBackDetail);
        radioS=view.findViewById(R.id.radioS);
        radioM=view.findViewById(R.id.radioM);
        radioL=view.findViewById(R.id.radioL);
        radioXL=view.findViewById(R.id.radioXL);
        radio38=view.findViewById(R.id.radio38);
        radio39=view.findViewById(R.id.radio39);
        radio40=view.findViewById(R.id.radio40);
        radio41=view.findViewById(R.id.radio41);
        radio42=view.findViewById(R.id.radio42);
        radio43=view.findViewById(R.id.radio43);
        btnAddToCart=view.findViewById(R.id.btnAddToCart);
        rgSize=view.findViewById(R.id.rgSize);
        llSize=view.findViewById(R.id.llSize);
    }
}