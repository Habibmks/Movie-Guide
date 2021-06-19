package com.example.movieguide.collections.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.movieguide.R;
import com.example.movieguide.Functions.apifunc;
import com.example.movieguide.collections.ActorRVAdapterVerical;
import com.example.movieguide.collections.Actors.Actors;
import com.example.movieguide.collections.User.User;

import java.util.List;


public class ActorFragment extends Fragment {

    Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;
    User user;
    String userid;

    public ActorFragment(Context context,User user,String userid) {
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
        View v = inflater.inflate(R.layout.fragment_actor, container, false);
        EditText name = v.findViewById(R.id.etactorfragment);
        Button btn = v.findViewById(R.id.btnactorfragment);
        recyclerView = v.findViewById(R.id.rvactorfragment);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        apifunc apifunc = new apifunc();
        apifunc.popactors(context, new apifunc.popactorlistener() {
            @Override
            public void onResponse(List<Actors> list) {
                rAdapter = new ActorRVAdapterVerical(list,context,userid);
                recyclerView.setAdapter(rAdapter);
            }

            @Override
            public void onError(String message) {

            }
        });
        btn.setOnClickListener(v1 -> actorsearch(name.getText().toString()));
        return v;
    }
    public void actorsearch(String name){
        apifunc func = new apifunc();
        func.actorsearch(name, context, new apifunc.actorsearchlistener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(List<Actors> actors) {
                rAdapter = new ActorRVAdapterVerical(actors,context,userid);
                recyclerView.setAdapter(rAdapter);
            }
        });
    }
}