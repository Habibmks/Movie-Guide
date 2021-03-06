package com.example.movieguide.collections.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.movieguide.HomeActivity;
import com.example.movieguide.Profile;
import com.example.movieguide.R;
import com.example.movieguide.Test;
import com.example.movieguide.collections.User.User;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {
    Context context;
    User user;
    String userid;

    public ProfileFragment(Context context,User user,String userid) {
        // Required empty public constructor
        this.context = context;
        this.user = user;
        this.userid = userid;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView Username = v.findViewById(R.id.tvprofilename);
        Username.setText(user.getUsername());
        Button btnprofile = v.findViewById(R.id.btnprofile);
        Button btnfavmov = v.findViewById(R.id.btnfavmovie);
        Button btnfavser = v.findViewById(R.id.btnfavseries);
        Button btnfavact = v.findViewById(R.id.btnfavactors);
        Button btnlogout = v.findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(v12 -> {
            logout();
            FirebaseAuth.getInstance().signOut();
            getActivity().finish();
        });
        btnfavmov.setOnClickListener(v1 -> tofav("movies","Movies"));
        btnfavser.setOnClickListener(v14 -> tofav("series","Series"));
        btnfavact.setOnClickListener(v15 -> tofav("actors","Actors"));
        btnprofile.setOnClickListener(v13 -> profile());

        return v;
    }

    public void logout(){

    }

    public void tofav(String type,String title){
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("userid",userid);
        intent.putExtra("user",user);
        intent.putExtra("type",type);
        startActivity(intent);
    }
    public void profile(){
        Intent intent = new Intent(context, Profile.class);
        intent.putExtra("user",user);
        intent.putExtra("userid",userid);
        startActivity(intent);
    }
}