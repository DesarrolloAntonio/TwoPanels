package com.desarrollodroide.twopanels;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public  class MyExtendsFrameLayout extends FrameLayout {
	public MyExtendsFrameLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MyExtendsFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public MyExtendsFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	private void setMyWidth(int value) {
		getLayoutParams().width = value;
		requestLayout();
	}
	
	@SuppressWarnings("unused")
	private void setMyHeight(int value) {
		getLayoutParams().height = value;
		requestLayout();
	}
}
