package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.movieguide.Firebase.Firestore;
import com.example.movieguide.Functions.apifunc;
import com.example.movieguide.collections.User.User;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;
    static List<String> idlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.rvfavorites);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String userid = intent.getStringExtra("userid");
        User user = (User) intent.getSerializableExtra("user");
        Firestore firestore = new Firestore(userid);
        apifunc apifunc = new apifunc();
        TextView tvtitle = findViewById(R.id.tvfavorites);
        tvtitle.setText(title);
        idlist = new ArrayList<>();
        
        firestore.listgetter("Favorites", "movies", new Firestore.getlist() {
            @Override
            public void onResponse(ArrayList<String> list) {
                if (!list.isEmpty()){
                    idlist = list;
                    setlist(user,userid);
                }
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
        List<MovieSearchReturn> movieSearchReturnList = new ArrayList<>();
        try {
            apifunc.moviebyid(HomeActivity.this, idlist, new apifunc.idmovielistener() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse(MovieSearchReturn movieSearchReturn) {
                    movieSearchReturnList.add(movieSearchReturn);
                    rAdapter = new RecyclerViewAdapter(movieSearchReturnList,HomeActivity.this,user,userid);
                    recyclerView.setAdapter(rAdapter);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
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