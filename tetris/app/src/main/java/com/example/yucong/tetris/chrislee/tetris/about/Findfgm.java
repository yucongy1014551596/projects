package com.example.yucong.tetris.chrislee.tetris.about;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yucong.tetris.R;


/**
 * 模块介绍
 * 他可以在程序运行时动态添加到活动当中
 * LayoutInflater.inflate   将布局动态加载进来
 */
public class Findfgm extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.context_3, container, false);
	}
	
	
}
