package com.example.yucong.tetris.chrislee.tetris;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yucong.tetris.R;
import com.example.yucong.tetris.chrislee.tetris.entity.rank;

import java.util.List;

public class RankAdapter  extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    private List<rank> mRankList;

    /**
     * 首先定义一个内部类ViewHolder 继承自RecyclerView.ViewHolder
     */

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView irank;
        TextView tname;
        TextView tscore;
        //view通常为RecyclerView的最外层布局
        public ViewHolder(View view) {
            super(view);
            irank=itemView.findViewById(R.id.ivrank);
            tname=itemView.findViewById(R.id.tvname);
            tscore=itemView.findViewById(R.id.tv_score);
        }
    }

    //用于把要展示的数据源传进来
    public RankAdapter(List<rank> rankList) {
        mRankList = rankList;
    }


    /**
     * 继承自RecyclerView.Adapter 所以需要重写onCreateViewHolder onBindViewHolder getItemCount
     * @param parent
     * @param viewType
     * @return
     */

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //对RecyclerView滚动到屏幕的子想赋值
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        rank mrank = mRankList.get(position);
        holder.irank.setImageResource(mrank.getImageid());
        holder.tname.setText(mrank.getName());
        holder.tscore.setText(mrank.getScore()+"");
    }

    @Override
    public int getItemCount() {
        return mRankList.size();
    }
}
