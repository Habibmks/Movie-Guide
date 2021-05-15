package com.example.movieguide;

import android.app.VoiceInteractor;
import android.content.Context;
import android.graphics.drawable.Icon;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movieguide.collections.Actors.ActorDetails;
import com.example.movieguide.collections.Actors.Actors;
import com.example.movieguide.collections.Movies.MovieDetails;
import com.example.movieguide.collections.Shows.Shows;

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
                    JSONArray array = response.getJSONArray("genres");
                    int size = array.length();
                    String[] genres = new String[size];
                    for (int i = 0; i<size;i++){
                        JSONObject object = array.getJSONObject(i);
                        genres[i] = object.getString("id");
                    }
                    MovieDetails detail = new MovieDetails(
                            response.getString("original_title"),
                            response.getString("overview"),
                            response.getString("title"),
                            response.getString("release_date"),
                            response.getInt("id"),
                            response.getString("poster_path"),genres
                            ,size
                            );

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
                        Actors actors = new Actors(object.getInt("id"),object.getString("name"),object.getString("profile_path"),object.getInt("gender"),Float.parseFloat(object.getString("popularity")));
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
    public interface getactordetails{
        void onError(String message);
        void onResponse(ActorDetails actorDetails);
    }
    public void actordetails (String id,Context context,getactordetails listener){
        String url = "https://api.themoviedb.org/3/person/";
        url = url + id + key;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("also_known_as");
                    int size = array.length();
                    String[] names =  new String[size];
                    for (int i = 0; i<size; i++){
                        names[i] = array.getString(i);
                    }
                    ActorDetails actorDetails = new ActorDetails(Integer.parseInt(response.getString("id")),
                            response.getString("name"),
                            response.getString("profile_path"),
                            response.getInt("gender"),
                            Float.parseFloat(response.getString("popularity")),
                            names,
                            response.getString("biography"),
                            response.getString("birthday"),
                            size
                    );
                    listener.onResponse(actorDetails);
                } catch (JSONException e) {
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
    public interface actorsearchlistener{
        void onError(String message);
        void onResponse(List<Actors> actors);
    }
    public void actorsearch(String name,Context context,actorsearchlistener listener){
        String url = "https://api.themoviedb.org/3/search/person";
        String query = "&query=" + name;
        url = url + key + query;
        List<Actors> actorlist = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("results");
                    for (int i = 0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        Actors actors = new Actors(object.getInt("id"),object.getString("name"),object.getString("profile_path"),object.getInt("gender"),Float.parseFloat(object.getString("popularity")));
                        actorlist.add(actors);
                    }
                    listener.onResponse(actorlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }
    public interface showsearchlistener{
        void onError(String message);
        void onResponse(List<Shows> showsList);
    }
    public void showsearch(String name,Context context,showsearchlistener listener){
        String url = "https://api.themoviedb.org/3/search/tv";
        String query = "&query=" + name;
        url = url + key + query;
        List<Shows> showlist = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("results");
                    for (int i = 0;i<array.length();i++){
                        JSONObject object = array.getJSONObject(i);
                        JSONArray genre = object.getJSONArray("genre_ids");
                        String[] genres = new String[genre.length()];
                        for (int a = 0;a<genre.length();a++){
                            genres[a] = genre.getString(a);
                        }
                        Shows shows = new Shows(object.getInt("id"),genre.length(),genres,object.getString("name"),object.getString("overview"),object.getString("poster_path"),object.getString("original_name"),object.getString("first_air_date"));
                        showlist.add(shows);
                    }
                    listener.onResponse(showlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
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
    public interface testlistener{
        void onError(String message);
        void onResponse(String response);
    }
    public void test (String id, Context context,testlistener listener){
        String url = "https://api.themoviedb.org/3/person/";
        url = url + id + key;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listener.onResponse(response.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }
}
