package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieguide.Firebase.Firestore;
import com.example.movieguide.Functions.apifunc;
import com.example.movieguide.collections.RvAdapter.ActorRVAdapter;
import com.example.movieguide.collections.Actors.Actors;
import com.example.movieguide.collections.Movies.MovieDetails;
import com.example.movieguide.collections.RvAdapter.SimilarRVAdapter;
import com.example.movieguide.collections.User.User;

import java.util.List;

public class moviedetails extends AppCompatActivity {
    String movieid;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;
    User user;
    //if favbutton is true delete function runs
    //else add function runs
    Boolean favbutton;

    List<Actors> act;
    List<MovieSearchReturn> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetails);
        Intent intent = getIntent();
        if (intent.hasExtra("movieid")) {
            movieid = intent.getStringExtra("movieid");
        } else {
            //it's the Matrix
            movieid = "603";
        }
        user = (User) intent.getSerializableExtra("user");
        String userid = intent.getStringExtra("userid");
        act = null;
        movies = null;
        ImageView imageView = findViewById(R.id.ivmoviedetails);
        TextView tvname = findViewById(R.id.tvmoviename);
        TextView tvdate = findViewById(R.id.tvreleasedate);
        TextView tvoverview = findViewById(R.id.tvmovieoverview);
        Firestore firestore = new Firestore(userid);
        Button fav = findViewById(R.id.btnmoviefav);
        firestore.checker("movies", movieid, bool -> {
            fav.setText(bool ? "Remove" : "Add");
            favbutton = (bool ? true:false);
            fav.setVisibility(View.VISIBLE);
        });
        fav.setOnClickListener(v -> {
            if (favbutton) {
                firestore.deletefav(movieid, "movies");
                favbutton = false;
            } else {
                firestore.addfav(movieid, "movies");
                favbutton = true;
            }
            fav.setText(fav.getText().toString().equals("Add") ? "Remove" : "Add");

        });
        apifunc func = new apifunc();
        Runnable rvrunnable = new Runnable() {
            @Override
            public void run() {

                func.getactors(movieid, moviedetails.this, new apifunc.getactorslistener() {
                    @Override
                    public void onError(String message) {
                    }

                    @Override
                    public void onResponse(List<Actors> actors) {
                        act = actors;
                        recyclerView = findViewById(R.id.actorrecyclerview);
                        recyclerView.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(moviedetails.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        rAdapter = new ActorRVAdapter(act, moviedetails.this, userid);
                        recyclerView.setAdapter(rAdapter);
                    }
                });


                func.getsimilars(movieid, moviedetails.this, new apifunc.getsimilars() {
                    @Override
                    public void onError(String message) {
                    }

                    @Override
                    public void onResponse(List<MovieSearchReturn> movieSearchReturns) {
                        movies = movieSearchReturns;
                        recyclerView = findViewById(R.id.similarrecyclerview);
                        recyclerView.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(moviedetails.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        rAdapter = new SimilarRVAdapter(movies, moviedetails.this, user, userid);
                        recyclerView.setAdapter(rAdapter);
                    }
                });
            }

        };
        Runnable detailsrunnable = new Runnable() {
            @Override
            public void run() {
                func.getmoviedetails(movieid, moviedetails.this, new apifunc.moviedetailslistener() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(MovieDetails movieDetails) {
                        Glide.with(moviedetails.this).load(movieDetails.getPoster()).into(imageView);
                        tvname.setText(movieDetails.getOriginal_title());
                        tvdate.setText(movieDetails.getReleasedate());
                        tvoverview.setText(movieDetails.getOverview());
                    }
                });
            }
        };
        Thread th1 = new Thread(rvrunnable);
        Thread th2 = new Thread(detailsrunnable);
        th1.start();
        th2.start();
    }
}