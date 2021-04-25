package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieguide.collections.MovieDetails;

public class moviedetails extends AppCompatActivity {
    String movieid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetails);

        Intent intent = getIntent();
        if (intent.hasExtra("movieid")){
            movieid = intent.getStringExtra("movieid");
        }else{
            //it's the Matrix
            movieid = "603";
        }

        ImageView imageView = findViewById(R.id.ivmoviedetails);
        TextView tvname = findViewById(R.id.tvmoviename);
        TextView tvdate = findViewById(R.id.tvreleasedate);
        TextView tvoverview = findViewById(R.id.tvmovieoverview);
        apifunc func = new apifunc();
        func.getmoviedetails(movieid,this, new apifunc.moviedetailslistener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(MovieDetails movieDetails) {
                Glide.with(moviedetails.this).load(movieDetails.getPosterpath()).into(imageView);
                tvname.setText(movieDetails.getOriginalname());
                tvdate.setText(movieDetails.getReleasedate());
                tvoverview.setText(movieDetails.getOverview());
            }
        });

    }
}