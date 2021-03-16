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
                String url ="https://api.themoviedb.org/3/search/movie?api_key=5852ecb0b3ac54ac9867feffa62a3b3d";
                String apiquery = "&query=";
                String apipage="&page=";
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(HomeActivity.this);
                //String url ="https://www.metaweather.com/api/location/search/?query=london";
                apiquery +=name.getText().toString();apipage +=page.getText().toString();
                if(apiquery.equals("&query=")) apiquery="&query=fight";
                if(apipage.equals("&page=")) apipage="&page=1";
                url += apiquery + apipage;
                tv.setText("");
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
                            JSONArray array = response.getJSONArray("results");
                            JSONObject view;
                            int length = array.length();
                            String[] name = new String[length];String[] year = new String[length];String[] id = new  String[length];

                            for (int i = 0; i<=length;i++){
                                view = array.getJSONObject(i);
                                name[i] = view.getString("original_title");
                                year[i] = view.getString("release_date");
                                tv.append("\n Name: "+name[i]+" Year: "+year[i]);
                            }
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