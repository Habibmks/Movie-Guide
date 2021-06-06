package com.example.movieguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.movieguide.collections.User.User;
import com.example.movieguide.collections.fragments.MovieFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class Test extends AppCompatActivity {
    ViewPager2 vp;
    TabLayout tb;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        String userid = intent.getStringExtra("userid");
        vp = findViewById(R.id.testpager);
        tb = findViewById(R.id.testtab);
        Toast.makeText(this,"Welcome, " + user.getName(),Toast.LENGTH_SHORT).show();
        viewadapter viewadapter = new viewadapter(this,tb.getTabCount(),this,user,userid);
        vp.setAdapter(viewadapter);
        TabLayout.Tab tab = tb.getTabAt(vp.getCurrentItem());
        tb.setScrollPosition(vp.getCurrentItem(),0,true);
        tb.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
                viewadapter.notifyDataSetChanged();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}