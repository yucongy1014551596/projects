package com.example.yucong.seniorroject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void swip(View view){
        switch (view.getId()){
            case R.id.button1:
                Intent intent=new Intent();
                intent.setClassName("com.example.yucong.seniorroject","com.example.yucong.intent.FirstActivity");
                 startActivity(intent);






        }
    }
}
