package com.example.yucong.lianliankan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yucong.lianliankan.entity.Rank;
import com.example.yucong.lianliankan.util.GameConf;

import org.litepal.crud.DataSupport;

import java.util.List;


public class MyDialog extends Dialog {
    private  Context context;
    public MyDialog(final MainActivity linkup, boolean success, final Context context) {
        super(linkup,R.style.CustomDialogStyle);
        this.context=context;
        setContentView(R.layout.mydialog);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        TextView btnCancel = findViewById(R.id.tv_back);
        TextView btnOk = findViewById(R.id.tv_again);
        TextView tv_title=findViewById(R.id.dialog_title);
        TextView tv_next=findViewById(R.id.tv_next);
        if(success){
            tv_title.setText(context.getString(R.string.gamesuccess));
            Drawable LeftDrawable = context.getResources().getDrawable(R.drawable.success_1);
            LeftDrawable.setBounds(0, 0, LeftDrawable.getMinimumWidth(), LeftDrawable.getMinimumHeight());
            tv_title.setCompoundDrawables(LeftDrawable, null, null, null);
            tv_next.setVisibility(View.VISIBLE);
        }else{

        }
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancel();
                linkup.onclose();
            }

        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                linkup.gameGlass++;

                linkup.levelTextView.setText(context.getString(R.string.llkd)+linkup.gameGlass+context.getString(R.string.guan));
                if (linkup.gameGlass == 4) {
                    if(isRank(linkup.totalscore)){
                        if(!linkup.isFinishing()) {
                            linkup.successDialog = new SuccessDialog(linkup, context);
                            linkup.successDialog.showDialog(linkup.totalscore);
                        }
                    }
                    else{
                        Toast.makeText(context,context.getString(R.string.rankfail_tishi),Toast.LENGTH_SHORT).show();
                    }
                    linkup.gameGlass = 1;
                    linkup.oldtotscore=linkup.totalscore;
                    linkup.totalscore=0;
                    linkup.thisscore=0;
                }else {
                    linkup.isPlaying = linkup.IS_PLAY;
                    String s=linkup.getmaxscore(linkup.gameGlass)+"";
                    linkup.maxscoreText.setText(context.getString(R.string.jilu)+s);
                    linkup.startGame(GameConf.DEFAULT_TIME, true);
                }
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancel();
                linkup.totalscore=linkup.oldtotscore;
                linkup.startGame(GameConf.DEFAULT_TIME,true);
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            TextView btn = (TextView) findViewById(R.id.tv_back);
            btn.performClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *
     *
     *
     *            游戏分数
     */
    public void showDialog(int totalscore,int thisscore) {
        TextView tvScore = (TextView) findViewById(R.id.tv_totalscore);
        tvScore.setText(totalscore +context.getString( R.string.fen));
        TextView tvthisscore=findViewById(R.id.tv_thisscore);
        tvthisscore.setText(thisscore+context.getString(R.string.fen));
        this.show();
    }
    private boolean isRank(int score){
        List<Rank> list= DataSupport.where("score>?","score").find(Rank.class,true);
        if(list.size()<5)
            return  true;
        else
            return false;
    }
}


