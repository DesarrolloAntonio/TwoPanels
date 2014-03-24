package com.desarrollodroide.twopanels;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public  class MyExtendedImageView extends ImageView {

	public MyExtendedImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyExtendedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyExtendedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
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
