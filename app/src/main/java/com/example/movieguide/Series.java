package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieguide.Functions.Functions;
import com.example.movieguide.Functions.apifunc;
import com.example.movieguide.collections.Shows.showdetails;
import com.example.movieguide.collections.User.User;

public class Series extends AppCompatActivity {
    User user;
    String userid,id;
    ImageView imageView;
    TextView name,date,overview,genres;
    Button fav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        imageView = findViewById(R.id.ivseriesdetails);
        name = findViewById(R.id.tvseriesname);
        date = findViewById(R.id.tvseriesdate);
        genres = findViewById(R.id.tvseriesgenres);
        overview = findViewById(R.id.tvseriesoverview);
        apifunc func = new apifunc();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        userid = intent.getStringExtra("userid");

        func.seriesdetails(id, this, new apifunc.seriesdetailslistener() {
            @Override
            public void onError(String message) {
                Log.d("Series activity",message);
            }

            @Override
            public void onResponse(showdetails showdetails) {
                Glide.with(Series.this).load(showdetails.getPoster()).into(imageView);
                name.setText(showdetails.getName());
                date.setText(showdetails.getDate());
                String[] genre = showdetails.getGenres();
                for (int i=0;i<showdetails.getSize();i++){
                    genres.append(Functions.genres(Integer.parseInt(genre[i]))+"\n");
                }
                overview.setText(showdetails.getOverview());
            }
        });
    }
}