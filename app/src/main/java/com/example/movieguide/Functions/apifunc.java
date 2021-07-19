package com.example.movieguide.Functions;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.movieguide.MovieSearchReturn;
import com.example.movieguide.Singleton;
import com.example.movieguide.collections.Actors.ActorDetails;
import com.example.movieguide.collections.Actors.Actors;
import com.example.movieguide.collections.Movies.MovieDetails;
import com.example.movieguide.collections.Shows.Shows;
import com.example.movieguide.collections.Shows.showdetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class apifunc {

    private static final String key = "?api_key=5852ecb0b3ac54ac9867feffa62a3b3d";

    public interface moviesearchlistener {
        void onError(String message);

        void onResponse(List<MovieSearchReturn> movieSearchReturns);
    }

    public interface popmovielistener {
        void onResponse(List<MovieSearchReturn> list);

        void onError(String message);
    }

    public interface popactorlistener {
        void onResponse(List<Actors> list);

        void onError(String message);
    }

    public interface popserieslistener {
        void onResponse(List<Shows> list);

        void onError(String message);
    }

    public interface moviedetailslistener {
        void onError(String message);

        void onResponse(MovieDetails movieDetails);
    }

    public interface idmovielistener {
        void onError(String message);

        void onResponse(MovieSearchReturn movieSearchReturn);
    }

    public interface getsimilars {
        void onError(String message);

        void onResponse(List<MovieSearchReturn> movieSearchReturns);
    }

    public interface getactorslistener {
        void onError(String message);

        void onResponse(List<Actors> actors);
    }

    public interface getactordetails {
        void onError(String message);

        void onResponse(ActorDetails actorDetails);
    }

    public interface actorsearchlistener {
        void onError(String message);

        void onResponse(List<Actors> actors);
    }

    public interface showsearchlistener {
        void onError(String message);

        void onResponse(List<Shows> showsList);
    }

    public interface seriesdetailslistener {
        void onError(String message);

        void onResponse(showdetails showdetails);
    }

    //searches movies by name
    public void moviesearch(String query, String page, Context context, moviesearchlistener listener) {

        String url = "https://api.themoviedb.org/3/search/movie";
        url += key;
        String apiquery = "&query=";
        String apipage = "&page=";
        apiquery += query;
        apipage += page;
        if (apiquery.equals("&query=")) apiquery = "&query=fight";
        if (apipage.equals("&page=")) apipage = "&page=1";
        url += apiquery + apipage;
        List<MovieSearchReturn> rtn = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray array = response.getJSONArray("results");

                for (int i = 0; i < array.length(); i++) {

                    JSONObject view = (JSONObject) array.get(i);
                    JSONArray ids = view.getJSONArray("genre_ids");
                    MovieSearchReturn movies = movieobjectcontroller(view, ids);

                    rtn.add(movies);
                }
                //view = array.getJSONObject(0);
                //rtn = view.getString("original_title");
                listener.onResponse(rtn);
            } catch (JSONException e) {
                e.printStackTrace();
                listener.onError(e.getMessage());
            }
        }, error -> {

        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }

    //Get Movies by Actor id as list
    public void actormovies(String id, Context context, moviesearchlistener listener) {
        String url = "https://api.themoviedb.org/3/person/" + id + "/movie_credits" + key;
        List<MovieSearchReturn> rtn = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray array = response.getJSONArray("cast");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject view = (JSONObject) array.get(i);
                    JSONArray ids = view.getJSONArray("genre_ids");
                    MovieSearchReturn movies = movieobjectcontroller(view, ids);
                    rtn.add(movies);
                }
                listener.onResponse(rtn);
            } catch (JSONException e) {
                e.printStackTrace();
                listener.onError(e.getMessage());
            }
        }, error -> listener.onError(error.getMessage()));
        Singleton.getInstance(context).addToRequestQueue(request);
    }

    //gets popular movies
    public void popmovies(Context context, popmovielistener listener) {
        String url = "https://api.themoviedb.org/3/movie/popular";
        url += key;
        List<MovieSearchReturn> rtn = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray array = response.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject view = (JSONObject) array.get(i);
                    JSONArray ids = view.getJSONArray("genre_ids");
                    MovieSearchReturn movies = movieobjectcontroller(view, ids);
                    rtn.add(movies);
                }
                listener.onResponse(rtn);
            } catch (JSONException e) {
                e.printStackTrace();
                listener.onError(e.getMessage());
                Log.d("Popular movies error", e.getMessage());
            }
        }, error -> {

        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }


    //gets popular actors
    public void popactors(Context context, popactorlistener listener) {
        String url = "https://api.themoviedb.org/3/person/popular";
        url += key;
        List<Actors> list = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray array = response.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = (JSONObject) array.get(i);
                    Actors actors = new Actors(
                            object.getInt("id"),
                            object.getString("name"),
                            object.getString("profile_path"),
                            object.getInt("gender"),
                            Float.parseFloat(object.getString("popularity"))
                    );
                    list.add(actors);
                }
                listener.onResponse(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }

    public void SeriesActors(Context context, String id, popactorlistener listener) {
        String url = "https://api.themoviedb.org/3/tv/" + id + "/credits" + key;
        List<Actors> list = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray array = response.getJSONArray("cast");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    Actors actors = new Actors(
                            object.getInt("id"),
                            object.getString("name"),
                            object.getString("profile_path"),
                            object.getInt("gender"),
                            Float.parseFloat(object.getString("popularity"))
                    );
                    list.add(actors);
                }
                listener.onResponse(list);
            } catch (JSONException e) {
                e.printStackTrace();
                listener.onError(e.getMessage());
            }
        }, error -> {
            listener.onError(error.getMessage());
        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }


    //gets popular series
    public void popseries(Context context, popserieslistener listener) {
        String url = "https://api.themoviedb.org/3/tv/popular";
        url += key;
        List<Shows> list = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray array = response.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    JSONArray genre = object.getJSONArray("genre_ids");
                    String[] genres = new String[genre.length()];
                    for (int a = 0; a < genre.length(); a++) {
                        genres[a] = genre.getString(a);
                    }
                    Shows shows = new Shows(
                            object.getInt("id"),
                            genre.length(),
                            genres,
                            object.getString("name"),
                            object.getString("overview"),
                            object.getString("poster_path"),
                            object.getString("original_name"),
                            object.getString("first_air_date")
                    );
                    list.add(shows);
                }
                listener.onResponse(list);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("popseries fetch", e.getMessage());
            }
        }, error -> listener.onError(error.getMessage()));
        Singleton.getInstance(context).addToRequestQueue(request);
    }

    //gets similar series list
    public void similarseries(Context context, String id, popserieslistener listener) {
        String url = "https://api.themoviedb.org/3/tv/" + id + "/similar" + key;
        List<Shows> list = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray array = response.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    JSONArray genre = object.getJSONArray("genre_ids");
                    String[] genres = new String[genre.length()];
                    for (int a = 0; a < genre.length(); a++) {
                        genres[a] = genre.getString(a);
                    }
                    Shows shows = new Shows(
                            object.getInt("id"),
                            genre.length(),
                            genres,
                            object.getString("name"),
                            object.getString("overview"),
                            object.getString("poster_path"),
                            object.getString("original_name"),
                            object.getString("first_air_date")
                    );
                    list.add(shows);
                }
                listener.onResponse(list);
            } catch (JSONException e) {
                e.printStackTrace();
                listener.onError(e.getMessage());
            }
        }, error -> {
            listener.onError(error.getMessage());
        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }


    //gets movie's detailed information
    public void getmoviedetails(String id, Context context, moviedetailslistener listener) {

        String url = "https://api.themoviedb.org/3/movie/";
        if (id == null) id = "603";
        url = url + id + key;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray array = response.getJSONArray("genres");
                int size = array.length();
                String[] genres = new String[size];
                for (int i = 0; i < size; i++) {
                    JSONObject object = array.getJSONObject(i);
                    genres[i] = object.getString("id");
                }
                MovieDetails detail = new MovieDetails(
                        response.getString("original_title"),
                        response.getString("overview"),
                        response.getString("title"),
                        response.getString("release_date"),
                        response.getInt("id"),
                        response.getString("poster_path"),
                        genres,
                        size
                );

                listener.onResponse(detail);
            } catch (JSONException e) {
                e.printStackTrace();
                listener.onError(e.getMessage());
            }
        }, error -> {

        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }


    //gets movies details by id
    public void moviebyid(Context context, List<String> list, idmovielistener listener) throws InterruptedException {
        Log.d("idlistfunc", list.toString());
        List<MovieSearchReturn> rtn = new ArrayList<>();
        for (int a = 0; a < list.size(); a++) {
            String url = "https://api.themoviedb.org/3/movie/";
            String id = list.get(a);
            if (id == null) id = "603";
            url = url + id + key;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                try {
                    JSONArray array = response.getJSONArray("genres");
                    int size = array.length();
                    String[] genres = new String[size];
                    for (int i = 0; i < size; i++) {
                        JSONObject object = array.getJSONObject(i);
                        genres[i] = object.getString("id");
                    }
                    MovieSearchReturn movieSearchReturn = new MovieSearchReturn(
                            response.getString("original_title"),
                            response.getString("overview"),
                            response.getString("title"),
                            response.getString("release_date"),
                            response.getInt("id"),
                            response.getString("poster_path"),
                            genres,
                            size
                    );
                    listener.onResponse(movieSearchReturn);
                    Log.d("inside item", movieSearchReturn.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onError(e.getMessage());
                }
            }, error -> {

            });
            Singleton.getInstance(context).addToRequestQueue(request);
        }
        /*for(int b=0;b<list.size();b++){
            wait(200);
            Log.d("Time","200ms passed");
        }*/
    }


    //gets actors by id for detailed information
    public void getactors(String id, Context context, getactorslistener listener) {
        String url = "https://api.themoviedb.org/3/movie/";
        url = url + id + "/credits" + key;
        List<Actors> actorsList = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray array = response.getJSONArray("cast");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    Actors actors = new Actors(
                            object.getInt("id"),
                            object.getString("name"),
                            object.getString("profile_path"),
                            object.getInt("gender"),
                            Float.parseFloat(object.getString("popularity"))
                    );
                    actorsList.add(actors);

                }
                listener.onResponse(actorsList);
            } catch (JSONException e) {
                listener.onError(e.toString());
                e.printStackTrace();
            }

        }, error -> listener.onError(error.toString()));
        Singleton.getInstance(context).addToRequestQueue(request);
    }


    //gets similar movies by movie id
    public void getsimilars(String id, Context context, getsimilars listener) {
        String url = "https://api.themoviedb.org/3/movie/";
        url = url + id + "/similar" + key;
        List<MovieSearchReturn> movie = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray array = response.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    JSONArray genres = object.getJSONArray("genre_ids");
                    String[] id1 = new String[array.length()];
                    MovieSearchReturn movies = movieobjectcontroller(object, genres);
                    movie.add(movies);

                }
                listener.onResponse(movie);
            } catch (JSONException e) {
                listener.onError(e.toString());
            }
        }, error -> {

        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }


    //gets actors detailed information by id
    public void actordetails(String id, Context context, getactordetails listener) {
        String url = "https://api.themoviedb.org/3/person/";
        url = url + id + key;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("also_known_as");
                    int size = array.length();
                    String[] names = new String[size];
                    for (int i = 0; i < size; i++) {
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
        }, error -> listener.onError(error.toString()));
        Singleton.getInstance(context).addToRequestQueue(request);
    }


    //gets list of actors by name
    public void actorsearch(String name, Context context, actorsearchlistener listener) {
        String url = "https://api.themoviedb.org/3/search/person";
        String query = "&query=" + name;
        url = url + key + query;
        List<Actors> actorlist = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray array = response.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    Actors actors = new Actors(
                            object.getInt("id"),
                            object.getString("name"),
                            object.getString("profile_path"),
                            object.getInt("gender"),
                            Float.parseFloat(object.getString("popularity"))
                    );
                    actorlist.add(actors);
                }
                listener.onResponse(actorlist);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }


    //gets list of series by name
    public void showsearch(String name, Context context, showsearchlistener listener) {
        String url = "https://api.themoviedb.org/3/search/tv";
        String query = "&query=" + name;
        url = url + key + query;
        List<Shows> showlist = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray array = response.getJSONArray("results");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    JSONArray genre = object.getJSONArray("genre_ids");
                    String[] genres = new String[genre.length()];
                    for (int a = 0; a < genre.length(); a++) {
                        genres[a] = genre.getString(a);
                    }
                    Shows shows = new Shows(
                            object.getInt("id"),
                            genre.length(), genres,
                            object.getString("name"),
                            object.getString("overview"),
                            object.getString("poster_path"),
                            object.getString("original_name"),
                            object.getString("first_air_date")
                    );
                    showlist.add(shows);
                }
                listener.onResponse(showlist);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> error.printStackTrace());
        Singleton.getInstance(context).addToRequestQueue(request);
    }

    //Get Series by Actor id
    public void actorseries(String id, Context context,popserieslistener listener){
        String url = "https://api.themoviedb.org/3/person/" + id+"/tv_credits"+key;
        List<Shows> showsList = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null,response -> {
            try {
                JSONArray array = response.getJSONArray("cast");
                for (int i=0;i<array.length();i++){
                    JSONObject object = array.getJSONObject(i);
                    JSONArray genre = object.getJSONArray("genre_ids");
                    String[] genres = new String[genre.length()];
                    for (int a = 0; a < genre.length(); a++) {
                        genres[a] = genre.getString(a);
                    }
                    Shows shows = new Shows(
                            object.getInt("id"),
                            genre.length(), genres,
                            object.getString("name"),
                            object.getString("overview"),
                            object.getString("poster_path"),
                            object.getString("original_name"),
                            object.getString("first_air_date")
                    );
                    showsList.add(shows);
                }
                listener.onResponse(showsList);
            } catch (JSONException e) {
                e.printStackTrace();
                listener.onError(e.getMessage());
            }
        },error -> {
            listener.onError(error.getMessage());
        });
        Singleton.getInstance(context).addToRequestQueue(request);

    }


    public void seriesdetails(String id, Context context, seriesdetailslistener listener) {
        String url = "https://api.themoviedb.org/3/tv/";
        url = url + id + key;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray array = response.getJSONArray("genres");
                String[] genres = new String[array.length()];
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    genres[i] = object.getString("id");
                }

                showdetails details = new showdetails(
                        response.getInt("id"),
                        array.length(),
                        genres,
                        response.getString("name"),
                        response.getString("overview"),
                        response.getString("poster_path"),
                        response.getString("original_name"),
                        response.getString("first_air_date"),
                        response.getString("status")
                );
                listener.onResponse(details);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            listener.onError(error.getMessage());
        });
        Singleton.getInstance(context).addToRequestQueue(request);
    }


    //function that checks JSON array parameters exist
    public static MovieSearchReturn movieobjectcontroller(JSONObject view, JSONArray array) throws JSONException {
        MovieSearchReturn movies = null;
        String[] id = new String[array.length()];
        for (int a = 0; a < array.length(); a++) {
            if (array.getString(a) != null) {
                id[a] = array.getString(a);
            } else {

            }
        }
        try {
            if (!view.has("release_date")) {
                movies = new MovieSearchReturn(view.getString("original_title"), view.getString("overview"), view.getString("title"), "Nate", view.getInt("id"), view.getString("poster_path"), id, array.length());
            } else if (!view.has("original_title")) {
                movies = new MovieSearchReturn("No_Title", view.getString("overview"), view.getString("title"), view.getString("released_date"), view.getInt("id"), view.getString("poster_path"), id, array.length());
            } else if (!view.has("overview")) {
                movies = new MovieSearchReturn(view.getString("original_title"), "No_Overview", view.getString("title"), view.getString("release_date"), view.getInt("id"), view.getString("poster_path"), id, array.length());
            } else if (!view.has("title")) {
                movies = new MovieSearchReturn(view.getString("original_title"), view.getString("overview"), "No_Title", view.getString("release_date"), view.getInt("id"), view.getString("poster_path"), id, array.length());
            } else if (!view.has("id")) {
                movies = new MovieSearchReturn(view.getString("original_title"), view.getString("overview"), view.getString("title"), view.getString("release_date"), Integer.parseInt("No id"), view.getString("poster_path"), id, array.length());
            } else if (!view.has("poster_path")) {
                movies = new MovieSearchReturn(view.getString("original_title"), view.getString("overview"), view.getString("title"), view.getString("release_date"), view.getInt("id"), "https://www.gulal2.com/images/no-image800.jpg", id, array.length());
            } else {
                movies = new MovieSearchReturn(view.getString("original_title"), view.getString("overview"), view.getString("title"), view.getString("release_date"), view.getInt("id"), view.getString("poster_path"), id, array.length());
            }
        } catch (Exception e) {

        }

        return movies;

    }

    /*public interface testlistener{
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
    }*/
}
