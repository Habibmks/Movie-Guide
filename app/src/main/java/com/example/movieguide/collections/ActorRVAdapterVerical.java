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
import com.example.movieguide.collections.Actors.Actors;
import com.example.movieguide.collections.User.User;

import java.util.List;

public class ActorRVAdapterVerical extends RecyclerView.Adapter<ActorRVAdapterVerical.ViewHolder> {
    Context context;
    List<Actors> actorsList;
    String userid;
    User user;
    public ActorRVAdapterVerical(List<Actors> actorsList,Context context,String userid,User user){
        this.actorsList = actorsList;
        this.context = context;
        this.userid = userid;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.oneactorlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv.setText(actorsList.get(position).getName());
        Glide.with(this.context).load(actorsList.get(position).getPoster()).into(holder.iv);
        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, Actordetails.class);
            intent.putExtra("id",String.valueOf(actorsList.get(position).getId()));
            intent.putExtra("userid",userid);
            context.startActivity(intent);
        });
        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context,Actordetails.class);
            intent.putExtra("actorid",actorsList.get(position).getId());
            intent.putExtra("user",user);
            intent.putExtra("userid",userid);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return actorsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.ivactorlayout);
            tv = itemView.findViewById(R.id.tvactorlayout);
            layout = itemView.findViewById(R.id.actorverticallayout);
        }
    }
}
