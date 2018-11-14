package com.example.yucong.musicdemo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaofanchen on 2018/3/21.
 */

public class MyAdapter extends BaseAdapter{
    private Context context;
    private List<MusicInfor> mylist;
    public MyAdapter(MainActivity mainActivity,List<MusicInfor> mylist){
        this.context = mainActivity;
        this.mylist = mylist;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int i) {
        return mylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = View.inflate(context,R.layout.music_item,null);
            holder.song = (TextView)view.findViewById(R.id.item_music_song);
            holder.singer = (TextView)view.findViewById(R.id.item_music_singer);
            holder.duration = (TextView)view.findViewById(R.id.item_music_duration);
            holder.position = (TextView)view.findViewById(R.id.item_music_position);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }
        holder.song.setText(mylist.get(i).song.toString());
        holder.singer.setText(mylist.get(i).singer.toString());
        int duration = mylist.get(i).duration;
        String time = MainActivity.formatTime(duration);
        holder.duration.setText(time);
        holder.position.setText(i+1+"");

        return view;
    }
    class ViewHolder{
        TextView song;
        TextView singer;
        TextView duration;
        TextView position;
    }
}
