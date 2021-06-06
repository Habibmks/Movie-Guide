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
import com.example.movieguide.Actordetails;
import com.example.movieguide.R;
import com.example.movieguide.apifunc;
import com.example.movieguide.collections.Actors.Actors;

import java.util.List;

public class ActorRVAdapter extends RecyclerView.Adapter<ActorRVAdapter.ViewHolder> {

    List<com.example.movieguide.collections.Actors.Actors> Actors;
    Context context;
    apifunc func;
    String userid;

    public ActorRVAdapter(List<Actors> actors, Context context,String userid) {
        this.Actors = actors;
        this.context = context;
        this.userid = userid;
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
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Actordetails.class);
                intent.putExtra("id",String.valueOf(Actors.get(position).getId()));
                intent.putExtra("userid",userid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Actors.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivactor;
        TextView tvactor;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivactor = itemView.findViewById(R.id.ivsimple);
            tvactor = itemView.findViewById(R.id.tvsimple);
            layout = itemView.findViewById(R.id.actorlayout);
        }
    }
}
