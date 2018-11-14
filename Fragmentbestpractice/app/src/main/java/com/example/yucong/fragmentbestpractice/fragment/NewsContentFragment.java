package com.example.yucong.fragmentbestpractice.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yucong.fragmentbestpractice.R;

public class NewsContentFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

      view= inflater.inflate(R.layout.news_content_frag,container,false);
        return view;

    }

    public void refresh(String newsTitle ,String newsContent){
     View visibility=   view.findViewById(R.id.visibility_layout);
     visibility.setVisibility(View.VISIBLE);
     TextView title= view.findViewById(R.id.news_title);
        TextView content= view.findViewById(R.id.news_content);
        title.setText(newsTitle);//刷新新闻标题
        content.setText(newsContent);


    }
}
