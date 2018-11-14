package com.example.yucong.gobang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by think on 2018/10/3.
 */

public class PlayerAdapter extends ArrayAdapter<Player> {
    private int resourceId;

    public PlayerAdapter(Context context, int itemResourceId, List<Player> objects){
        super(context,itemResourceId,objects);
        resourceId = itemResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Player player = getItem(position);
        View  view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView account = (TextView) view.findViewById(R.id.account);
        TextView status = (TextView) view.findViewById(R.id.status);
        account.setText(player.getAccount());
        status.setText(player.getStatusView());
        return view;
    }
}
