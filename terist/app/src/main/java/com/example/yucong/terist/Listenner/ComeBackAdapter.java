package com.example.yucong.terist.Listenner;


import com.example.yucong.terist.base.Ground;
import com.example.yucong.terist.base.Shape;

public interface ComeBackAdapter {

	public void refreshDisplay(Shape shape);
	public void refreshDisplay(Shape shape, Ground ground);
	public boolean isEnd(Shape eshape);
	public void gameOver();
}
