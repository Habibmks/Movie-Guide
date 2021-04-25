package com.example.movieguide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieguide.collections.MovieDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    TextView tv;
    Button apibtn;
    EditText page,name;
    String str;
    String temp="";
    RequestQueue queue;
    ListView lv;
    List<MovieSearchReturn> movieSearchReturn;
    Menu menu;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv = findViewById(R.id.tvapi); apibtn = findViewById(R.id.buttonapi);
        page = findViewById(R.id.etidpage); name = findViewById(R.id.etidname);

        tv.setText("");
        Intent intent = getIntent();
        if(intent.hasExtra("user_email")){
            str = intent.getStringExtra("user_email").toString();
        }else {
            str = "Misafir";
        }
        queue = Volley.newRequestQueue(this);
        movieSearchReturn = null;


        apibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apifunc func = new apifunc();
                func.moviesearch(name.getText().toString(), page.getText().toString(), HomeActivity.this, new apifunc.moviesearchlistener() {
                    @Override
                    public void onError(String message) {
                        tv.setText(message);
                    }

                    @Override
                    public void onResponse(List<MovieSearchReturn> movieSearchReturns) {
                        movieSearchReturn = movieSearchReturns;
//                        ArrayAdapter arrayAdapter = new ArrayAdapter(HomeActivity.this, android.R.layout.simple_list_item_1,movieSearchReturns);
//                        lv.setAdapter(arrayAdapter);
                        tv.setText("");
                        recyclerView = findViewById(R.id.rvmovie);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(HomeActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        rAdapter = new RecyclerViewAdapter(movieSearchReturn,HomeActivity.this);
                        recyclerView.setAdapter(rAdapter);
                    }
                });

            }
        });



    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sortmenu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ascyear:
                Collections.sort(movieSearchReturn,MovieSearchReturn.comparatoryearascending);
                rAdapter.notifyDataSetChanged();
                return true;
            case R.id.descyear:
                Collections.sort(movieSearchReturn,MovieSearchReturn.comparatoryeardescending);
                rAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}