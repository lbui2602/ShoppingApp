package com.example.shoppingapp.activities.fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.activities.LoginActivity;
import com.example.shoppingapp.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnLogout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LinearLayout llTinNhan;
    onClickLogout onClickLogout;
    TextView tvNameUser;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogout=view.findViewById(R.id.btnLogout);
        llTinNhan=view.findViewById(R.id.llTinNhan);
        tvNameUser=view.findViewById(R.id.tvNameUser);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        NavController navController = NavHostFragment.findNavController(UserFragment.this);
        tvNameUser.setText("Hello "+user.getEmail());
        llTinNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "onClick: "+user.getEmail().toString());
                if(!user.getEmail().toString().equals("admin@gmail.com")){
                    Bundle bundle=new Bundle();
                    bundle.putString("user_id","MKseU4i70aejc3jUY6x9rbCIylp1");
                    navController.navigate(R.id.action_userFragment_to_chatActivity,bundle);
                    Log.d("TAG", "not admin: ");
                }else{
                    Log.d("TAG", " admin: ");
                    navController.navigate(R.id.action_userFragment_to_userChatFragment);
                }
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                if(onClickLogout!=null){
                    onClickLogout.onLogout();
                }
            }
        });
    }
    public interface onClickLogout{
        void onLogout();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onClickLogout) {
            onClickLogout = (onClickLogout) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }
}