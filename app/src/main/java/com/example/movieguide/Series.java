package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieguide.Firebase.Firestore;
import com.example.movieguide.Functions.Functions;
import com.example.movieguide.Functions.apifunc;
import com.example.movieguide.collections.ActorRVAdapter;
import com.example.movieguide.collections.Actors.Actors;
import com.example.movieguide.collections.SeriesRVAdapter;
import com.example.movieguide.collections.SeriesRVAdapterHorizontal;
import com.example.movieguide.collections.Shows.Shows;
import com.example.movieguide.collections.Shows.showdetails;
import com.example.movieguide.collections.User.User;

import java.util.List;

public class Series extends AppCompatActivity {
    User user;
    String userid, id;
    ImageView imageView;
    TextView name, date, overview, genres;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    Button fav;
    //if favbutton is true delete function runs
    //else add function runs
    Boolean favbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        imageView = findViewById(R.id.ivseriesdetails);
        name = findViewById(R.id.tvseriesname);
        date = findViewById(R.id.tvseriesdate);
        genres = findViewById(R.id.tvseriesgenres);
        overview = findViewById(R.id.tvseriesoverview);
        fav = findViewById(R.id.btnaddseriesfav);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        userid = intent.getStringExtra("userid");
        Firestore firestore = new Firestore(userid);
        apifunc func = new apifunc();
        firestore.checker("series", id, bool -> {
            fav.setText(bool ? "Remove" : "Add");
            favbutton = (bool);
            fav.setVisibility(View.VISIBLE);
        });
        fav.setOnClickListener(v -> {
            if (favbutton) {
                firestore.deletefav(id, "series");
                favbutton = false;
            } else {
                firestore.addfav(id, "series");
                favbutton = true;
            }
            fav.setText(fav.getText().toString().equals("Add") ? "Remove" : "Add");

        });
        func.seriesdetails(id, this, new apifunc.seriesdetailslistener() {
            @Override
            public void onError(String message) {
                Log.d("Series activity", message);
            }

            @Override
            public void onResponse(showdetails showdetails) {
                Glide.with(Series.this).load(showdetails.getPoster()).into(imageView);
                name.setText(showdetails.getName());
                date.setText(showdetails.getDate());
                String[] genre = showdetails.getGenres();
                for (int i = 0; i < showdetails.getSize(); i++) {
                    genres.append(Functions.genres(Integer.parseInt(genre[i])) + "\n");
                }
                overview.setText(showdetails.getOverview());
            }
        });
        func.similarseries(this, id, new apifunc.popserieslistener() {
            @Override
            public void onResponse(List<Shows> list) {
                recyclerView = findViewById(R.id.rvsimilarseries);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Series.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                rAdapter = new SeriesRVAdapterHorizontal(list, Series.this, user, userid);
                recyclerView.setAdapter(rAdapter);
            }

            @Override
            public void onError(String message) {
                Log.d("Seriesdetail", message);
            }
        });
        func.SeriesActors(this, id, new apifunc.popactorlistener() {
            @Override
            public void onResponse(List<Actors> list) {
                recyclerView = findViewById(R.id.rvseriesactor);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Series.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                rAdapter = new ActorRVAdapter(list, Series.this, userid);
                recyclerView.setAdapter(rAdapter);
            }

            @Override
            public void onError(String message) {

            }
        });
    }
}