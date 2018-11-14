package com.example.yucong.gobang;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by mrpeng on 18-10-10.
 */

public class DividerManager {
    private String rgb;
    private Context context;

    public DividerManager(String rgb, Context context){
        this.rgb = rgb;
        this.context = context;
    }

    public View getDivider(){
        View divider = new View(context);
        divider.setBackgroundColor(Color.parseColor(rgb));
        divider.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,2));

        return divider;
    }
}
