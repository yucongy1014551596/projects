package com.example.yucong.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.yucong.seniorroject.R;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Person1 person1=new Person1();
        person1.setAge(15);
        person1.setName("yucong");
        Intent intent=new Intent(FirstActivity.this,secondActivity.class);
        intent.putExtra("person_data",person1);
        startActivity(intent);

    }
}
