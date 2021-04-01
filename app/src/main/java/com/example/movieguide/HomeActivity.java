package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv = findViewById(R.id.tvapi); apibtn = findViewById(R.id.buttonapi);
        page = findViewById(R.id.etidpage); name = findViewById(R.id.etidname);
        lv = findViewById(R.id.home_lv);
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
                /*func.searchMovies(name.getText().toString(), page.getText().toString(), HomeActivity.this, new apifunc.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        tv.setText("No Response");
                    }

                    @Override
                    public void onResponse(String rtn) {
                        tv.setText(rtn);
                    }
                });*/
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
}