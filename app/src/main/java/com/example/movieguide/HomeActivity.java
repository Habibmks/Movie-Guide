package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {
    TextView tv;
    Button apibtn;
    String cityinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv = findViewById(R.id.tvapi); apibtn = findViewById(R.id.buttonapi);

        apibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
                    String url ="https://www.metaweather.com/api/location/search/?query=london";

                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url ,null ,  new Response.Listener<JSONArray>() {
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