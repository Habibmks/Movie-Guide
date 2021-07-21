package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.movieguide.Firebase.Firestore;
import com.example.movieguide.Functions.apifunc;
import com.example.movieguide.collections.Actors.Actors;
import com.example.movieguide.collections.RvAdapter.ActorRVAdapterVerical;
import com.example.movieguide.collections.RvAdapter.SeriesRVAdapter;
import com.example.movieguide.collections.Shows.Shows;
import com.example.movieguide.collections.User.User;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;
    static List<String> idlist;
    User user;
    Firestore firestore;
    String userid,title,type;
    TextView tvtitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.rvfavorites);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        userid = intent.getStringExtra("userid");
        type = intent.getStringExtra("type");
        user = (User) intent.getSerializableExtra("user");
        firestore = new Firestore(userid);
        tvtitle = findViewById(R.id.tvfavorites);
        tvtitle.setText(title);
        idlist = new ArrayList<>();
        Log.d("HomeActivity",type);
        firestore.listgetter("Favorites", type, new Firestore.getlist() {
            @Override
            public void onResponse(ArrayList<String> list) {
                if (!list.isEmpty()){
                    Log.d("Profilelistgetter",list.toString());
                    idlist = list;
                    setlist(user,userid);
                }
            }

            @Override
            public void onError(String message) {
                Log.d("HomeActivity",message);
            }
        });
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };

    }
    public void setlist(User user,String userid){
        apifunc apifunc = new apifunc();
        Log.d("setlist","before try catch");
        try {
            if(type.equals("movies")) {
                List<MovieSearchReturn> movieSearchReturnList = new ArrayList<>();
                apifunc.moviebyid(HomeActivity.this, idlist, new apifunc.idmovielistener() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(MovieSearchReturn movieSearchReturn) {
                        movieSearchReturnList.add(movieSearchReturn);
                        Log.d("setlistmovies",movieSearchReturnList.toString());
                        rAdapter = new RecyclerViewAdapter(movieSearchReturnList, HomeActivity.this, user, userid);
                        recyclerView.setAdapter(rAdapter);
                    }
                });
            }else if(type.equals("series")){
                List<Shows> showsList = new ArrayList<>();
                apifunc.seriesid(HomeActivity.this, idlist, new apifunc.serieslistener() {
                    @Override
                    public void onResponse(Shows shows) {
                        showsList.add(shows);
                        Log.d("setlistseries",showsList.toString());
                        rAdapter = new SeriesRVAdapter(HomeActivity.this,showsList,userid);
                        recyclerView.setAdapter(rAdapter);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }else if(type.equals("actors")){
                List<Actors> actorsList = new ArrayList<>();
                apifunc.actorbyid(HomeActivity.this, idlist, new apifunc.actorlistener() {
                    @Override
                    public void onResponse(Actors actors) {
                        actorsList.add(actors);
                        rAdapter = new ActorRVAdapterVerical(actorsList,HomeActivity.this,userid,user);
                        recyclerView.setAdapter(rAdapter);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("SetList",e.getMessage());
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sortmenu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ascyear:
                Collections.sort(movieSearchReturn,MovieSearchReturn.comparatoryearascending);
                rAdapter.notifyDataSetChanged();
                return true;
            case R.id.descyear:
                Collections.sort(movieSearchReturn,MovieSearchReturn.comparatoryeardescending);
                rAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}