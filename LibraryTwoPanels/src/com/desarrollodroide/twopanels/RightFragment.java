package com.desarrollodroide.twopanels;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

public class RightFragment extends Fragment {

	private ImageButton mSlideRightButton;
	private Boolean mSlidersButtonActive = true;
	protected View mContainer;
	private int mDrawableDown = R.drawable.ic_slider_bottom;
	private int mDrawableRight = R.drawable.ic_slider_right;

	public interface OnSliderRightListener {
		/** Called by RightFragment when sliderRightButton is clicked */
		public void slideFragmentsToRight();
	}

	OnSliderRightListener mCallback;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myFragmentView = inflater.inflate(R.layout.right_fragment, container, false);

		try {
			mCallback = (OnSliderRightListener) getActivity();
		} catch (ClassCastException e) {
			throw new ClassCastException(getActivity().toString() + " must implement OnSliderRightListener");
		}

		mSlideRightButton = (ImageButton) myFragmentView.findViewById(R.id.slideRightButton);
		mSlideRightButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mCallback.slideFragmentsToRight();
			}
		});

		if (((TwoPanelsBaseActivity) getActivity()).getFragmentsOrientation() == LinearLayout.VERTICAL) {
			updateSlideRightButtonOrientationVertical();
			mSlideRightButton.setImageResource(mDrawableDown);
		} else {
			// Button orientation defined in XML
			mSlideRightButton.setImageResource(mDrawableRight);
		}

		this.mContainer = myFragmentView;
		return myFragmentView;
	}

	public ImageButton getmSlideRightButton() {
		return mSlideRightButton;
	}

	protected void setSliderButtonsDrawables(int down, int right) {
		mDrawableDown = down;
		mDrawableRight = right;
		if (((TwoPanelsBaseActivity) getActivity()).getFragmentsOrientation() == LinearLayout.VERTICAL) {
			mSlideRightButton.setImageResource(mDrawableDown);
		} else {
			mSlideRightButton.setImageResource(mDrawableRight);
		}
	}

	public void updateSlideRightButtonOrientationVertical() {
		if (mSlidersButtonActive) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			mSlideRightButton.setLayoutParams(params);
			mSlideRightButton.setImageResource(mDrawableDown);
		}
	}

	public void updateSlideRightButtonOrientationHorizontal() {
		if (mSlidersButtonActive) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			mSlideRightButton.setLayoutParams(params);
			mSlideRightButton.setImageResource(mDrawableRight);
		}
	}

	public void switchButtonsSliderVisivility() {
		if (mSlidersButtonActive) {
			mSlideRightButton.setVisibility(View.GONE);
			mSlidersButtonActive = false;
		} else {
			mSlideRightButton.setVisibility(View.VISIBLE);
			mSlidersButtonActive = true;
		}
	}

	public void setSliderClickable(Boolean clickable) {
		mSlideRightButton.setClickable(clickable);
	}

}
