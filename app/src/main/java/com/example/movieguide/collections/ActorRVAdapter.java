package com.example.movieguide.collections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieguide.R;
import com.example.movieguide.apifunc;

import java.util.List;

public class ActorRVAdapter extends RecyclerView.Adapter<ActorRVAdapter.ViewHolder> {

    List<Actors> Actors;
    Context context;
    apifunc func;

    public ActorRVAdapter(List<Actors> actors, Context context) {
        this.Actors = actors;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.actorlayout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvactor.setText(Actors.get(position).getName());
        Glide.with(this.context).load(Actors.get(position).getPoster()).into(holder.ivactor);
    }

    @Override
    public int getItemCount() {
        return Actors.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivactor;
        TextView tvactor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivactor = itemView.findViewById(R.id.ivsimple);
            tvactor = itemView.findViewById(R.id.tvsimple);
        }
    }
}
