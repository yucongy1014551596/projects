package com.example.yucong.file;

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

    public void share(View view){
        Intent intent=new Intent();
        intent.setClassName("com.example.yucong.file","com.example.yucong.sharepreference.KouLing");
        startActivity(intent);

    }


    public void file(View view){
        Intent intent=new Intent();
        intent.setClassName("com.example.yucong.file","com.example.yucong.file.FileService");
        startActivity(intent);

    }
    public void SdFile(View view){
        Intent intent=new Intent();
        intent.setClassName("com.example.yucong.file","com.example.yucong.file.FIleSDcard");
        startActivity(intent);

    }
}
