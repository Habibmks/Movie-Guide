package com.example.movieguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<MovieSearchReturn> movielist;
    Context context;

    public RecyclerViewAdapter(List<MovieSearchReturn> movielist, Context context) {
        this.movielist = movielist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvtitle.setText(movielist.get(position).getOriginal_title());
        holder.tvdate.setText(movielist.get(position).getReleasedate());
        Glide.with(this.context).load(movielist.get(position).poster).into(holder.ivmovie);
    }

    @Override
    public int getItemCount() {
        return movielist.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivmovie;
        TextView tvtitle;
        TextView tvdate;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivmovie = itemView.findViewById(R.id.iv_poster);
            tvtitle = itemView.findViewById(R.id.tv_title);
            tvdate = itemView.findViewById(R.id.tv_date);
            layout = itemView.findViewById(R.id.oneitemlayout);
        }
    }

}
