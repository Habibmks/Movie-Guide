package com.example.movieguide;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.movieguide.collections.User.User;
import com.example.movieguide.collections.fragments.ActorFragment;
import com.example.movieguide.collections.fragments.MovieFragment;
import com.example.movieguide.collections.fragments.ProfileFragment;
import com.example.movieguide.collections.fragments.SeriesFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class viewadapter extends FragmentStateAdapter {

    int count;
    Context context;
    User user;
    String userid;

    public viewadapter(@NonNull FragmentActivity fragmentActivity,int count,Context context,User user,String userid) {
        super(fragmentActivity);
        this.count = count;
        this.context = context;
        this.user = user;
        this.userid = userid;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new MovieFragment(context,user,userid);
            case 1: return new ActorFragment(context,user,userid);
            case 2: return new SeriesFragment(context,user,userid);
            case 3: return new ProfileFragment(context,user,userid);
            default: return new MovieFragment(context,user,userid);
        }
    }
    @Override
    public int getItemCount() {
        return count;
    }

}
