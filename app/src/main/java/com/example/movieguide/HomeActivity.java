package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class HomeActivity extends AppCompatActivity {
    TextView tv;
    Button apibtn;
    EditText page,name;
    String str;
    String temp="";
    RequestQueue queue;

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
                        tv.setText("No Response");
                    }

                    @Override
                    public void onResponse(MovieSearchReturn movieSearchReturn) {
                        tv.setText(movieSearchReturn.overview.toString());
                    }
                });
                tv.setText("");
            }
        });
    }
}