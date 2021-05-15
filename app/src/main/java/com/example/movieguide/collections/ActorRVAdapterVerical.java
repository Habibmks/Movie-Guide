package com.example.movieguide.collections;

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
import com.example.movieguide.R;
import com.example.movieguide.collections.Actors.Actors;

import java.util.List;

public class ActorRVAdapterVerical extends RecyclerView.Adapter<ActorRVAdapterVerical.ViewHolder> {
    Context context;
    List<Actors> actorsList;
    public ActorRVAdapterVerical(List<Actors> actorsList,Context context){
        this.actorsList = actorsList;
        this.context = context;
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
