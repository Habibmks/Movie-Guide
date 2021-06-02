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

import java.util.List;

public class viewadapter extends FragmentStateAdapter {

    int count;
    Context context;
    User user;

    public viewadapter(@NonNull FragmentActivity fragmentActivity,int count,Context context,User user) {
        super(fragmentActivity);
        this.count = count;
        this.context = context;
        this.user = user;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new MovieFragment(context);
            case 1: return new ActorFragment(context);
            case 2: return new SeriesFragment(context);
            case 3: return new ProfileFragment(context,user);
            default: return new MovieFragment(context);
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }
}
