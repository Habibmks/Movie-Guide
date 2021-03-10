package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    String cityinfo,str;
    String temp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv = findViewById(R.id.tvapi); apibtn = findViewById(R.id.buttonapi);

        Intent intent = getIntent();
        if(intent.hasExtra("user_email")){
            str = intent.getStringExtra("user_email").toString();
        }else {
            str = "Misafir";
        }

        apibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
                    //String url ="https://www.metaweather.com/api/location/search/?query=london";

                    String url ="https://api.themoviedb.org/3/search/movie?api_key=5852ecb0b3ac54ac9867feffa62a3b3d&query=fight";
                    /*JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url ,null ,  new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject city = response.getJSONObject(0);
                            cityinfo = city.getString("title") + "\n" + city.getString("woeid");
                            tv.setText(cityinfo);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv.setText(error.getMessage().toString());
                    }
                });*/
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject movie = new JSONObject(response.toString());
                            temp = movie.getJSONObject("results").getString("original_title").toString();
                            tv.setText(temp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                queue.add(request);
                    /*
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    tv.setText(response);

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            tv.setText(error.getMessage().toString() );
                        }
                    });
                    // Add the request to the RequestQueue.*/
            }
        });
    }
}