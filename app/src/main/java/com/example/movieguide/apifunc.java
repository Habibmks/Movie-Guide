package com.example.movieguide;

import android.app.VoiceInteractor;
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
import com.example.movieguide.collections.Actors;
import com.example.movieguide.collections.MovieDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class apifunc {
    String api = "";
    String rtn="";
    private static final String key = "?api_key=5852ecb0b3ac54ac9867feffa62a3b3d";

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(String rtn);
    }

    public void searchMovies(String query, String page, Context context,VolleyResponseListener listener){
        //request before add singleton
        //RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://api.themoviedb.org/3/search/movie";
        url += key;
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
        void onResponse(List<MovieSearchReturn> movieSearchReturns);
    }

    public void moviesearch(String query, String page, Context context, moviesearchlistener listener){

        String url="https://api.themoviedb.org/3/search/movie";
        url += key;
        String apiquery = "&query=";
        String apipage="&page=";
        apiquery +=query;apipage +=page;
        if(apiquery.equals("&query=")) apiquery="&query=fight";
        if(apipage.equals("&page=")) apipage="&page=1";
        url += apiquery + apipage;

        List<MovieSearchReturn> rtn = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("results");

                    for (int i=0;i<array.length();i++) {

                        JSONObject view = (JSONObject) array.get(i);
                        JSONArray ids = view.getJSONArray("genre_ids");
                        MovieSearchReturn movies = movieobjectcontroller(view,ids);

                        rtn.add(movies);
                    }
                    //view = array.getJSONObject(0);
                    //rtn = view.getString("original_title");
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



    public interface moviedetailslistener{
        void onError(String message);
        void onResponse(MovieDetails movieDetails);
    }

    public void getmoviedetails(String id,Context context, moviedetailslistener listener){

        String url = "https://api.themoviedb.org/3/movie/";
        if(id == null) id = "603";
        url = url + id + key;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    MovieDetails detail = new MovieDetails(response.getString("original_title"),response.getString("overview"),response.getString("poster_path"),response.getString("release_date"));

                    listener.onResponse(detail);
                }catch (JSONException e){
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
    public interface getactorslistener{
        void onError(String message);
        void onResponse(List<Actors> actors);
    }
    public void getactors(String id,Context context,getactorslistener listener){
        String url = "https://api.themoviedb.org/3/movie/";
        url = url + id + "/credits" + key;
        RequestQueue queue = Volley.newRequestQueue(context);
        List<Actors> actorsList = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("cast");
                    for (int i = 0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        Actors actors = new Actors(object.getInt("id"),object.getString("name"),object.getString("character"),object.getString("profile_path"),object.getInt("gender"),Float.parseFloat(object.getString("popularity")));
                        actorsList.add(actors);
                    }
                    listener.onResponse(actorsList);
                } catch (JSONException e) {
                    listener.onError(e.toString());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            listener.onError(error.toString());
            }
        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }
    public interface getsimilars{
        void onError(String message);
        void onResponse(List<MovieSearchReturn> movieSearchReturns);
    }
    public void getsimilars(String id,Context context,getsimilars listener){
        String url = "https://api.themoviedb.org/3/movie/";
        url = url + id + "/similar" + key;
        RequestQueue queue = Volley.newRequestQueue(context);
        List<MovieSearchReturn> movie = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("results");
                    for (int i = 0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        JSONArray genres = object.getJSONArray("genre_ids");
                        String[] id = new String[array.length()];
                        MovieSearchReturn movies = movieobjectcontroller(object,genres);
                        movie.add(movies);

                    }
                    listener.onResponse(movie);
                }catch (JSONException e){
                    listener.onError(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }
    //function that checks JSON array parameters exist
    public static MovieSearchReturn movieobjectcontroller (JSONObject view,JSONArray array) throws JSONException {
        MovieSearchReturn movies = null;
        String[] id = new String[array.length()];
        for (int a=0;a<array.length();a++){
            if (array.getString(a)!=null){
                id[a] = array.getString(a);
            }else{

            }
        }
        try {
            if (!view.has("release_date")){
                movies = new MovieSearchReturn(view.getString("original_title"),view.getString("overview"),view.getString("title"),"No date",view.getInt("id"),view.getString("poster_path"),id,array.length());
            }else if(!view.has("original_title")){
                movies = new MovieSearchReturn("No_Title",view.getString("overview"),view.getString("title"),view.getString("release_date"),view.getInt("id"),view.getString("poster_path"),id,array.length());
            }else if (!view.has("overview")){
                movies = new MovieSearchReturn(view.getString("original_title"),"No_Overview", view.getString("title"), view.getString("release_date"), view.getInt("id"), view.getString("poster_path"),id,array.length());
            }else if (!view.has("title")){
                movies = new MovieSearchReturn(view.getString("original_title"), view.getString("overview"),"No_Title", view.getString("release_date"), view.getInt("id"), view.getString("poster_path"),id,array.length());
            }else if (!view.has("id")){
                movies = new MovieSearchReturn(view.getString("original_title"), view.getString("overview"), view.getString("title"), view.getString("release_date"), Integer.parseInt("No id"), view.getString("poster_path"),id,array.length());
            }else if (!view.has("poster_path")){
                movies = new MovieSearchReturn(view.getString("original_title"), view.getString("overview"), view.getString("title"), view.getString("release_date"), view.getInt("id"), "https://www.gulal2.com/images/no-image800.jpg",id,array.length());
            }else{
                movies = new MovieSearchReturn(view.getString("original_title"), view.getString("overview"), view.getString("title"), view.getString("release_date"), view.getInt("id"), view.getString("poster_path"),id,array.length());
            }
        }catch (Exception e){

        }

        return movies;

    }
    public static String genres(int id){
        switch (id){
            case 28:
                return "Action";
            case 12:
                return "Adventure";
            case 16:
                return "Animation";
            case 35:
                return "Comedy";
            case 80:
                return "Crime";
            case 99:
                return "Documentary";
            case 18:
                return "Drama";
            case 10751:
                return "Family";
            case 14:
                return "Fantasy";
            case 36:
                return "History";
            case 27:
                return "Horror";
            case 10402:
                return "Music";
            case 9648:
                return "Mystery";
            case 10749:
                return "Romance";
            case 878:
                return "Science Fiction";
            case 10770:
                return "TV Movie";
            case 53:
                return "Thriller";
            case 10752:
                return "War";
            case 37:
                return "Western";
            default:
                return null;
        }
    }
    public static String gender(int i){
        switch (i){
            case 1:
                return "Female";
            case 2:
                return "Male";
            default:
                return null;
        }
    }
}
