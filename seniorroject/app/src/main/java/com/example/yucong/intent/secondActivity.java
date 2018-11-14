package com.example.yucong.intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.yucong.seniorroject.R;

public class secondActivity extends AppCompatActivity {
    private static final String TAG = "secondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
       Person1 person1=(Person1) getIntent().getParcelableExtra("person_data");
        Log.i(TAG, "onCreate: "+person1.getName());
    }
}
