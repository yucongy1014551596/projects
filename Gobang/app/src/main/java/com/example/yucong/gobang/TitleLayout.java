package com.example.yucong.gobang;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by think on 2018/10/4.
 */

public class TitleLayout extends LinearLayout {
    public TitleLayout(final Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);
        Button toRank = (Button) findViewById(R.id.rank_button);
        toRank.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Rank.class);
                context.startActivity(intent);
            }
        });
    }
}
