package com.example.movieguide.collections.RvAdapter;

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

import java.util.List;

public class SeriesRVAdapter extends RecyclerView.Adapter<SeriesRVAdapter.ViewHolder> {

    List<Shows> showlist;
    Context context;
    String userid;

    public SeriesRVAdapter(Context context,List<Shows> showlist,String userid){
        this.context = context;
        this.showlist = showlist;
        this.userid = userid;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvname.setText(showlist.get(position).getName());
        holder.tvdate.setText(showlist.get(position).getDate());
        Glide.with(this.context).load(showlist.get(position).getPoster()).into(holder.iv);
        holder.layout.setOnClickListener(v -> {
            Intent intent = new Intent(context, Series.class);
            intent.putExtra("userid",userid);
            intent.putExtra("id",String.valueOf(showlist.get(position).getId()));
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return showlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tvname,tvdate;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_poster);
            tvname = itemView.findViewById(R.id.tv_title);
            tvdate = itemView.findViewById(R.id.tv_date);
            layout = itemView.findViewById(R.id.oneitemlayout);
        }
    }
}
