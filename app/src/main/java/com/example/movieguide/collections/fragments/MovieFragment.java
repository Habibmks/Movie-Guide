package com.example.movieguide.collections.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.movieguide.MovieSearchReturn;
import com.example.movieguide.R;
import com.example.movieguide.RecyclerViewAdapter;
import com.example.movieguide.Test;
import com.example.movieguide.apifunc;
import com.example.movieguide.collections.User.User;

import java.util.List;

public class MovieFragment extends Fragment {
    Context context;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;
    User user;
    String userid;


    public MovieFragment(Context context,User user,String userid) {
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
        View v = inflater.inflate(R.layout.fragment_movie, container, false);
        EditText name = (EditText) v.findViewById(R.id.etmoviesearchfragment);
        Button btn = v.findViewById(R.id.btnmoviesearchfragment);
        recyclerView = v.findViewById(R.id.rvmoviesearchfragment);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        apifunc apifunc = new apifunc();
        apifunc.popmovies(context,new apifunc.popmovielistener() {
            @Override
            public void onResponse(List<MovieSearchReturn> list) {
                rAdapter= new RecyclerViewAdapter(list,context,user,userid);
                recyclerView.setAdapter(rAdapter);
            }

            @Override
            public void onError(String message) {
                Log.d("Movie Fragment create",message);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(v,name.getText().toString());
            }
        });

        return  v;
    }
    public void search(View v,String query){
        apifunc func = new apifunc();
        func.moviesearch(query, "1", context, new apifunc.moviesearchlistener   () {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(List<MovieSearchReturn> movieSearchReturns) {
                rAdapter = new RecyclerViewAdapter(movieSearchReturns, context,user,userid);
                recyclerView.setAdapter(rAdapter);
            }
        });

    }
}