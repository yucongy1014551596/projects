package com.example.yucong.timer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "<<<";


    private TextView mTextView = null;


    private Button mButton_start = null;
    private Button mButton_pause = null;
    private Button mButton_stop = null;
    private Button mButton_resume = null;
    Timeutils timeutils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mTextView = (TextView)findViewById(R.id.mytextview);


        mButton_start = (Button)findViewById(R.id.mybutton_start);

        mButton_pause = (Button)findViewById(R.id.mybutton_pause);

        mButton_stop = (Button)findViewById(R.id.mybutton_stop);

        mButton_resume = (Button)findViewById(R.id.mybutton_resume);
        timeutils=new Timeutils(mTextView);



        mButton_start.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                timeutils.startTimer();
            }

        });

        mButton_pause.setOnClickListener(new View.OnClickListener()
        {

            @Override
           public void onClick(View v) {
            timeutils.puseTimer();
          }

        });


        mButton_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeutils.stopTimer();
            }
        });


        mButton_resume.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                timeutils.resumeTime();
            }
        });


    }

}
