package com.example.movieguide;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieguide.Functions.apifunc;
import com.example.movieguide.collections.Actors.ActorDetails;
import com.example.movieguide.collections.User.User;

public class Actordetails extends AppCompatActivity {
    String id;
    User user;
    String userid;
    ImageView imageView;
    TextView name,age,biography,othernames;
    Button fav;
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
        apifunc func = new apifunc();
        Intent intent = getIntent();
        if (!intent.hasExtra("id")) id = "6384"; else id = intent.getStringExtra("id");
        user = (User) intent.getSerializableExtra("user");
        userid = intent.getStringExtra("userid");
        fav.setOnClickListener(v -> fav.setText(fav.getText().toString().equals("Add") ? "Remove":"Add"));
        func.actordetails(id, Actordetails.this, new apifunc.getactordetails() {
            @Override
            public void onError(String message) {
                name.setText(message);
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
                for (int i = 0;i<actorDetails.getSize();i++){
                    othernames.append(names[i]+"\n");
                }
            }
        });
    }
}