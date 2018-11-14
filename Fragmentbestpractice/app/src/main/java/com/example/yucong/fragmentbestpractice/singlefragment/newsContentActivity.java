package com.example.yucong.fragmentbestpractice.singlefragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yucong.fragmentbestpractice.R;
import com.example.yucong.fragmentbestpractice.fragment.NewsContentFragment;

public class newsContentActivity extends AppCompatActivity {
   public static void actionStart(Context context,String newsTitle,String newsContent){
       Intent intent=new Intent(context,newsContentActivity.class);
       intent.putExtra("news_title",newsTitle);
       intent.putExtra("news_content",newsContent);
       context.startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
      String title=  getIntent().getStringExtra("news_title");
        String content=  getIntent().getStringExtra("news_content");
     getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);

    }
}
