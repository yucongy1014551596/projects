package com.example.yucong.musicdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

/**
 * Created by chaofanchen on 2018/4/4.
 */

public class TimeActivity extends Activity implements OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_layout);
        Spinner spinner = (Spinner)findViewById(R.id.sp_time);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this,TimeService.class);
        switch(i) {
            case 0:
                break;
            case 1:
                TimeService.timelong = 1;
                this.startService(intent);
                break;
            case 2:
                TimeService.timelong = 5;
                this.startService(intent);
                break;
            case 3:
                TimeService.timelong = 11;
                this.startService(intent);
                break;
            case 4:
                TimeService.timelong = 59;
                this.startService(intent);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> arg0){

    }
}
