package com.example.movieguide;

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
import com.example.movieguide.Functions.apifunc;
import com.example.movieguide.collections.User.User;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<MovieSearchReturn> movielist;
    Context context;
    apifunc api = new apifunc();
    User user;
    String userid;

    public RecyclerViewAdapter(List<MovieSearchReturn> movielist, Context context,User user,String userid) {
        this.movielist = movielist;
        this.context = context;
        this.user = user;
        this.userid = userid;
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
        int size = movielist.get(position).getSize();
        String[] genre = movielist.get(position).getGenres();
        //BAKILACAK!!!!!
        /*for (int i=0; i<size;i++){
            genre[i] = api.genres(Integer.parseInt(genre[i]));
            holder.tvgenres.append(genre[i] + ", ");
        }*/
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,moviedetails.class);
                intent.putExtra("movieid",String.valueOf(movielist.get(position).getId()));
                intent.putExtra("user",user);
                intent.putExtra("userid",userid);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return movielist.size();
    }
    public int getItemViewType(int position){
        return position;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivmovie;
        TextView tvtitle;
        TextView tvdate;
        TextView tvgenres;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivmovie = itemView.findViewById(R.id.iv_poster);
            tvtitle = itemView.findViewById(R.id.tv_title);
            tvdate = itemView.findViewById(R.id.tv_date);
            layout = itemView.findViewById(R.id.oneitemlayout);
            tvgenres = itemView.findViewById(R.id.tvgenres);
        }
    }

}
