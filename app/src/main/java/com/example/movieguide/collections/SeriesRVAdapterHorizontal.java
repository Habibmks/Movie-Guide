package com.example.movieguide.collections;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieguide.R;
import com.example.movieguide.Series;
import com.example.movieguide.collections.Shows.Shows;
import com.example.movieguide.collections.User.User;

import java.lang.invoke.ConstantCallSite;
import java.util.List;

public class SeriesRVAdapterHorizontal extends RecyclerView.Adapter<SeriesRVAdapterHorizontal.ViewHolder> {

    List<Shows> shows;
    Context context;
    User user;
    String userid;

    public SeriesRVAdapterHorizontal(List<Shows> shows, Context context, User user, String userid) {
        this.context = context;
        this.shows = shows;
        this.user = user;
        this.userid = userid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.actorlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvsimilar.setText(shows.get(position).getName());
        Glide.with(this.context).load(shows.get(position).getPoster()).into(holder.ivsimilar);
        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, Series.class);
            intent.putExtra("userid", userid);
            intent.putExtra("id", String.valueOf(shows.get(position).getId()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivsimilar;
        TextView tvsimilar;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivsimilar = itemView.findViewById(R.id.ivsimple);
            tvsimilar = itemView.findViewById(R.id.tvsimple);
            layout = itemView.findViewById(R.id.actorlayout);
        }
    }
}
