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
import com.example.movieguide.collections.SeriesRVAdapter;
import com.example.movieguide.collections.Shows.Shows;

import java.util.List;


public class SeriesFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Context context;
    public SeriesFragment(Context context) {
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
        View v = inflater.inflate(R.layout.fragment_series, container, false);
        EditText name = v.findViewById(R.id.edseriesfragment);
        Button btn = v.findViewById(R.id.btnseriesfragment);
        recyclerView = v.findViewById(R.id.rvseriesfragment);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchshows(name.getText().toString());
            }
        });
        return v;
    }
    public void searchshows(String name){
        apifunc func = new apifunc();
        func.showsearch(name, context, new apifunc.showsearchlistener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(List<Shows> showsList) {
                rAdapter = new SeriesRVAdapter(context,showsList);
                recyclerView.setAdapter(rAdapter);
            }
        });
    }
}