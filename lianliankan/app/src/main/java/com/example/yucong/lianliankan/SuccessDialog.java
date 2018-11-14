package com.example.yucong.lianliankan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class SuccessDialog extends Dialog {
    private  EditText ed_name;
    private Context context;
    public SuccessDialog(final MainActivity linkup, final Context context) {
        super(linkup,R.style.CustomDialogStyle);
        this.context=context;
        setContentView(R.layout.dialog_success);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        ed_name=findViewById(R.id.tv_name);
        TextView tv_can=findViewById(R.id.tv_back_suc);
        TextView tv_ok=findViewById(R.id.tv_ok_suc);
        linkup.isPlaying=linkup.IS_PAUSE;
        tv_can.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancel();
                Intent intent=new Intent(context,StartActivity.class);
                linkup.startActivity(intent);
                linkup.finish();
            }

        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=ed_name.getText().toString();
                if(isletornum(s)) {
                    cancel();
                    linkup.rank.setScore(linkup.oldtotscore);
                    linkup.rank.setName(s);
                    DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    linkup.rank.setTime(df.format(System.currentTimeMillis()));
                    linkup.rank.save();
                    linkup.oldtotscore=0;
                    Intent intent=new Intent(linkup,RankActivity.class);
                    linkup.startActivity(intent);
                    linkup.finish();
                }else{
                    Toast.makeText(context,context.getString(R.string.rankname_tishi),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            TextView btn = (TextView) findViewById(R.id.tv_back_suc);
            btn.performClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showDialog(int totalscore) {
        TextView tvScore = (TextView) findViewById(R.id.tv_totalscore_suc);
        tvScore.setText(totalscore + context.getString(R.string.fen));
        this.show();
    }
    private boolean isletornum(String s){
        String pattern = "[a-zA-z0-9]{4,5}";
        return Pattern.matches(pattern,s);
    }
}
