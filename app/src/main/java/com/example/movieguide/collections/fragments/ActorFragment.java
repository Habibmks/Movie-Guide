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
import com.example.movieguide.apifunc;
import com.example.movieguide.collections.ActorRVAdapterVerical;
import com.example.movieguide.collections.Actors.Actors;

import java.util.List;


public class ActorFragment extends Fragment {

    Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public ActorFragment(Context context) {
        // Required empty public constructor
        this.context = context;
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actorsearch(name.getText().toString());
            }
        });
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
                rAdapter = new ActorRVAdapterVerical(actors,context);
                recyclerView.setAdapter(rAdapter);
            }
        });
    }
}