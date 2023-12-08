package com.example.shoppingapp.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoppingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoiMKFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoiMKFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText edtPasswordCu,edtPasswordMoi,edtPasswordMoiConfirm;
    Button btnDoiMK;
    NavController navController;

    public DoiMKFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoiMKFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoiMKFragment newInstance(String param1, String param2) {
        DoiMKFragment fragment = new DoiMKFragment();
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
        return inflater.inflate(R.layout.fragment_doi_m_k, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtPasswordCu=view.findViewById(R.id.edtPasswordCu);
        edtPasswordMoi=view.findViewById(R.id.edtPasswordMoi);
        edtPasswordMoiConfirm=view.findViewById(R.id.edtPasswordMoiConfirm);
        btnDoiMK=view.findViewById(R.id.btnDoiMK);
        FirebaseAuth auth=FirebaseAuth.getInstance();
        FirebaseUser user= auth.getCurrentUser();
        navController= NavHostFragment.findNavController(DoiMKFragment.this);
        DatabaseReference db=FirebaseDatabase.getInstance().getReference("users");

        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password=edtPasswordCu.getText().toString().trim();
                String passwordMoi=edtPasswordMoi.getText().toString().trim();
                String passwordMoiConfirm=edtPasswordMoiConfirm.getText().toString().trim();
                if(password.length()==0 || passwordMoi.length()==0 || passwordMoiConfirm.length()==0){
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                }else{
                    db.child(user.getUid()).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String pass=snapshot.getValue(String.class);
                            if(!password.equals(pass)){
                                Toast.makeText(getContext(), "Mật khẩu cũ không đúng.", Toast.LENGTH_SHORT).show();
                            }else{
                                if(passwordMoi.equals(passwordMoiConfirm)&& passwordMoi.length()>=6 && !password.equals(passwordMoi)){
                                    snapshot.getRef().setValue(passwordMoi);
                                    user.updatePassword(passwordMoi)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                    } else {
                                                    }
                                                }
                                            });
                                    Toast.makeText(getContext(), "Đổi mật khẩu thành công.", Toast.LENGTH_SHORT).show();
                                    navController.navigate(R.id.action_doiMKFragment_to_userFragment);
                                    edtPasswordMoi.setText("");
                                    edtPasswordCu.setText("");
                                    edtPasswordMoiConfirm.setText("");
                                }
                                else{
                                    Toast.makeText(getContext(), "Mật khẩu mới không hợp lệ.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}