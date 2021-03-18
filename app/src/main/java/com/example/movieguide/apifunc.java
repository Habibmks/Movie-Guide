package com.example.movieguide;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class apifunc {
    String api = "";
    String rtn="";

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(String rtn);
    }

    public void searchMovies(String query, String page, Context context,VolleyResponseListener listener){
        //request before add singleton
        //RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://api.themoviedb.org/3/search/movie?api_key=5852ecb0b3ac54ac9867feffa62a3b3d";
        String apiquery = "&query=";
        String apipage="&page=";
        apiquery +=query;apipage +=page;
        if(apiquery.equals("&query=")) apiquery="&query=fight";
        if(apipage.equals("&page=")) apipage="&page=1";
        url += apiquery + apipage;



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("results");
                    JSONObject view;
                    int length = array.length();
                    String[] name = new String[length];String[] year = new String[length];String[] id = new  String[length];
                    /*
                    for (int i = 0; i<=length;i++){
                        view = array.getJSONObject(i);
                        name[i] = view.getString("original_title");
                        year[i] = view.getString("release_date");
                        tv.append("\n Name: "+name[i]+" Year: "+year[i]);
                    }
                    */
                    view = array.getJSONObject(0);
                    rtn = view.getString("overview");
                    listener.onResponse(rtn);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError(e.getMessage().toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }

    public interface moviesearchlistener{
        void onError(String message);
        void onResponse(MovieSearchReturn movieSearchReturn);
    }

    public void moviesearch(String query, String page, Context context, moviesearchlistener listener){
        String url="https://api.themoviedb.org/3/search/movie?api_key=5852ecb0b3ac54ac9867feffa62a3b3d&query=fight&page=1";
        List<MovieSearchReturn> rtn = new ArrayList<>();


        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("results");

                    JSONObject view = (JSONObject) array.get(0);
                    MovieSearchReturn first = new MovieSearchReturn(view.getString("original_title"),view.getString("overview"),view.getString("title"),view.getString("release_date"),view.getInt("id"));

                    int length = array.length();
                    String[] name = new String[length];String[] year = new String[length];String[] id = new  String[length];
                    /*
                    for (int i = 0; i<=length;i++){
                        view = array.getJSONObject(i);
                        name[i] = view.getString("original_title");
                        year[i] = view.getString("release_date");
                        tv.append("\n Name: "+name[i]+" Year: "+year[i]);
                    }*/
                    view = array.getJSONObject(0);
                    //rtn = view.getString("original_title");
                    listener.onResponse(first);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError(e.getMessage().toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
}
