package com.example.yucong.lianliankan;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.yucong.lianliankan.entity.Rank;

import java.util.List;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private List<Rank> mRank;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivrank;
        TextView tvname;
        TextView tvscore;
        public ViewHolder(View itemView) {
            super(itemView);
            ivrank=itemView.findViewById(R.id.ivrank);
            tvname=itemView.findViewById(R.id.tvname);
            tvscore=itemView.findViewById(R.id.tv_score);
        }
    }

    public RankAdapter(List<Rank> mRank) {
        super();
        this.mRank=mRank;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rank rank=mRank.get(position);
        holder.ivrank.setImageResource(rank.getImageid());
        holder.tvname.setText(rank.getName());
        holder.tvscore.setText(rank.getScore()+"");
    }

    @Override
    public int getItemCount() {
        return mRank.size();
    }

}
