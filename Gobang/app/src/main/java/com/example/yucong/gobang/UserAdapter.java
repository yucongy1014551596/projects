package com.example.yucong.gobang;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by think on 2018/10/4.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> mUserList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rankView;
        TextView accountView;
        TextView winView;
        TextView loseView;
        TextView gradeView;
        public ViewHolder(View view){
            super(view);
            rankView = (TextView) view.findViewById(R.id.rank);
            accountView = (TextView) view.findViewById(R.id.account);
            winView = (TextView) view.findViewById(R.id.win);
            loseView = (TextView) view.findViewById(R.id.lose);
            gradeView = (TextView) view.findViewById(R.id.grade);
        }
    }

    public UserAdapter(List<User> userList){
        mUserList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        User user = mUserList.get(position);
        holder.rankView.setText(user.getRank()+"");
        holder.accountView.setText(user.getAccount());
        holder.winView.setText(user.getWin()+"");
        holder.loseView.setText(user.getLose()+"");
        holder.gradeView.setText(user.getGrade()+"");
    }

    @Override
    public int getItemCount(){
        return mUserList.size();
    }
}
