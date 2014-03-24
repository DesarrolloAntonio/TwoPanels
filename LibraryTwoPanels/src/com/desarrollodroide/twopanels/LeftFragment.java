package com.desarrollodroide.twopanels;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class LeftFragment extends Fragment {

	private ImageButton mSlideLeftButton;
	public View mContainer;
	private Boolean mSlidersButtonActive = true;
	private int mDrawableLeft = R.drawable.ic_slider_left;
	private int mDrawableUp = R.drawable.ic_slider_top;

	public interface OnSliderLeftListener {
		/** Called by LeftFragment when sliderLeftButton is clicked */
		public void slideFragmentsToLeft();
	}

	OnSliderLeftListener mCallback;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		try {
			mCallback = (OnSliderLeftListener) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().toString() + " must implement OnSliderLeftListener");
		}

		View myFragmentView = inflater.inflate(R.layout.left_fragment, container, false);
		mSlideLeftButton = (ImageButton) myFragmentView.findViewById(R.id.slideLeftButton);
		mSlideLeftButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mCallback.slideFragmentsToLeft();
			}
		});

		if (((TwoPanelsBaseActivity) getActivity()).getFragmentsOrientation() == LinearLayout.VERTICAL) {
			updateSlideLeftButtonOrientationVertical();
			mSlideLeftButton.setImageResource(mDrawableUp);

		} else {
			// Define in XML
			mSlideLeftButton.setImageResource(mDrawableLeft);
		}
		this.mContainer = myFragmentView;
		return myFragmentView;
	}

	
	protected void setSliderButtonsDrawables(int up, int left) {
		mDrawableUp = up;
		mDrawableLeft = left;
		if (((TwoPanelsBaseActivity) getActivity()).getFragmentsOrientation() == LinearLayout.VERTICAL) {
			mSlideLeftButton.setImageResource(mDrawableUp);
		} else {
			mSlideLeftButton.setImageResource(mDrawableLeft);
		}
	}

	public void updateSlideLeftButtonOrientationVertical() {
		if (mSlidersButtonActive) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			mSlideLeftButton.setLayoutParams(params);
			mSlideLeftButton.setImageResource(mDrawableUp);
		}
	}

	public void updateSlideLeftButtonOrientationHorizontal() {
		if (mSlidersButtonActive) {

			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			mSlideLeftButton.setLayoutParams(params);
			mSlideLeftButton.setImageResource(mDrawableLeft);
		}
	}

	public void switchButtonsSliderVisivility() {
		if (mSlidersButtonActive) {
			mSlideLeftButton.setVisibility(View.GONE);
			mSlidersButtonActive = false;
		} else {
			mSlideLeftButton.setVisibility(View.VISIBLE);
			mSlidersButtonActive = true;
		}
	}

	public ImageButton getmSlideLeftButton() {
		return mSlideLeftButton;
	}



}
