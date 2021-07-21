package com.example.movieguide;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieguide.Firebase.Firestore;
import com.example.movieguide.Functions.apifunc;
import com.example.movieguide.collections.Actors.ActorDetails;
import com.example.movieguide.collections.RvAdapter.MovieRVAdapterHorizontal;
import com.example.movieguide.collections.RvAdapter.SeriesRVAdapterHorizontal;
import com.example.movieguide.collections.Shows.Shows;
import com.example.movieguide.collections.User.User;

import java.util.List;

public class Actordetails extends AppCompatActivity {
    String id;
    User user;
    String userid;
    ImageView imageView;
    TextView name, age, biography, othernames;
    Button fav;
    //if favbutton is true delete function runs
    //else add function runs
    Boolean favbutton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actordetails);
        imageView = findViewById(R.id.ivactordetails);
        name = findViewById(R.id.tvadname);
        age = findViewById(R.id.tvadage);
        biography = findViewById(R.id.tvadbiography);
        othernames = findViewById(R.id.tvadknownnames);
        fav = findViewById(R.id.btnaddactorfav);
        Intent intent = getIntent();
        id = (intent.hasExtra("id") ? intent.getStringExtra("id") : "6384");
        user = (User) intent.getSerializableExtra("user");
        userid = intent.getStringExtra("userid");
        Firestore firestore = new Firestore(userid);
        apifunc func = new apifunc();
        Log.d("ActorUserId",userid);
        firestore.checker("actors", id, bool -> {
            Log.d("Actordetailsbool",bool.toString());
            fav.setText(bool ? "Remove" : "Add");
            favbutton = (bool ? true:false);
            fav.setVisibility(View.VISIBLE);
        });
        fav.setOnClickListener(v -> {
            if (favbutton) {
                firestore.deletefav(id, "actors");
                favbutton = false;
            } else {
                firestore.addfav(id, "actors");
                favbutton = true;
            }
            fav.setText(fav.getText().toString().equals("Add") ? "Remove" : "Add");

        });
        func.actordetails(id, Actordetails.this, new apifunc.getactordetails() {
            @Override
            public void onError(String message) {
                Log.d("Actordetails", message);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(ActorDetails actorDetails) {
                Glide.with(Actordetails.this).load(actorDetails.getPoster()).into(imageView);
                name.setText(actorDetails.getName());
                String birthday = actorDetails.getBirthday();
                String intage = ActorDetails.agecalculator(birthday);
                age.setText(intage);
                int size = actorDetails.getSize();
                biography.setText(actorDetails.getBiography());
                String[] names = new String[actorDetails.getSize()];
                names = actorDetails.getKnown_names();
                for (int i = 0; i < actorDetails.getSize(); i++) {
                    othernames.append(names[i] + "\n");
                }
            }
        });
        func.actormovies(id, this, new apifunc.moviesearchlistener() {
            @Override
            public void onError(String message) {
                Log.d("Actordetailsmovies", message);
            }

            @Override
            public void onResponse(List<MovieSearchReturn> movieSearchReturns) {
                recyclerView = findViewById(R.id.rvactormovies);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Actordetails.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                rAdapter = new MovieRVAdapterHorizontal(movieSearchReturns, Actordetails.this, user, userid);
                recyclerView.setAdapter(rAdapter);
            }
        });
        func.actorseries(id, Actordetails.this, new apifunc.popserieslistener() {
            @Override
            public void onResponse(List<Shows> list) {
                recyclerView = findViewById(R.id.rvactorseries);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Actordetails.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                rAdapter = new SeriesRVAdapterHorizontal(list, Actordetails.this, user, userid);
                recyclerView.setAdapter(rAdapter);
            }

            @Override
            public void onError(String message) {
                Log.d("Actordetailsseries", message);
            }
        });

    }
}