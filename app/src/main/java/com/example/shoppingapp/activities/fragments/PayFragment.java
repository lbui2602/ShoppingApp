package com.example.shoppingapp.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.shoppingapp.R;

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
        int sum=getArguments().getInt("sum");

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