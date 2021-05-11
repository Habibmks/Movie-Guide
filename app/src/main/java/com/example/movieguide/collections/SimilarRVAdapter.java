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
import com.example.movieguide.MovieSearchReturn;
import com.example.movieguide.R;
import com.example.movieguide.moviedetails;

import java.util.List;

public class SimilarRVAdapter extends RecyclerView.Adapter<SimilarRVAdapter.ViewHolder> {


    List<MovieSearchReturn> movieSearchReturns;
    Context context;
    public SimilarRVAdapter(List<MovieSearchReturn> movieSearchReturns, Context context) {
        this.movieSearchReturns = movieSearchReturns;
        this.context = context;
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
        holder.tvsimilar.setText(movieSearchReturns.get(position).getTitle());
        Glide.with(this.context).load(movieSearchReturns.get(position).getPoster()).into(holder.ivsimilar);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, moviedetails.class);
                intent.putExtra("movieid",String.valueOf(movieSearchReturns.get(position).getId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieSearchReturns.size();
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
