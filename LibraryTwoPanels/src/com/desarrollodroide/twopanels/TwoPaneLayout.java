/***
  Copyright (c) 2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Android Development_
    http://commonsware.com/Android
 */

package com.desarrollodroide.twopanels;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TwoPaneLayout extends LinearLayout implements OnTouchListener {
	private static final int ANIM_DURATION = 500;
	private View mLeft = null;
	private View mRight = null;
	private int mLeftWidth = 0;
	private int mRightWidth = 0;
	private int mLeftHeight = -1;
	private int mRightHeight = -1;
	private Context mContext;
	private int mScreenHeight, mScreenWidth;
	private float mLeftWeight, mRrightWeight;
	private int mActionbarHeight;
	private int mStatusBarHeight;
	private ImageView mSliderBar;
	private int mSizeSliderinVIew = 0;
	private int horizontalDrawable, verticalDrawable;

	// Default slider size
	private int mSliderBarConst = 25;

	// Default slider visibility
	private Boolean mIsSliderVisible = true;
	private float mPointerOffset;

	// Default size of the slider
	private int pixelsValue = 15; // Default size of the slider

	public TwoPaneLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
	}

	@Override
	public void onFinishInflate() {
		super.onFinishInflate();
		mLeft = getChildAt(0);
		mSliderBar = (ImageView) getChildAt(1);
		mRight = getChildAt(2);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((TwoPanelsBaseActivity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		mScreenHeight = ((TwoPanelsBaseActivity) mContext).getScreenHeight();
		mScreenWidth = ((TwoPanelsBaseActivity) mContext).getScreenWidth();
		mLeftWeight = ((TwoPanelsBaseActivity) mContext).getmLeftWeight();
		mRrightWeight = ((TwoPanelsBaseActivity) mContext).getmRightWeight();
		mStatusBarHeight = ((TwoPanelsBaseActivity) mContext).getStatusBarHeight();
		mActionbarHeight = ((TwoPanelsBaseActivity) mContext).getActionBarHeight();
		mScreenHeight = mScreenHeight - mActionbarHeight - mStatusBarHeight;
		if (((TwoPanelsBaseActivity) mContext).getFragmentsOrientation() == LinearLayout.VERTICAL) {
			setOrientation(VERTICAL);
		} else {
			setOrientation(HORIZONTAL);
		}

		float d = mContext.getResources().getDisplayMetrics().density;
		int size = (int) (pixelsValue * d);
		mSliderBarConst = size;
		setParamsValues();
		mSliderBar.setOnTouchListener(this);
		if (mIsSliderVisible) {
			mSizeSliderinVIew = mSliderBarConst;
		}
	}

	public void setBaseOrientation(int orientation) {
		if (orientation == LinearLayout.HORIZONTAL) {
			if (getOrientation() == LinearLayout.VERTICAL) {
				changeOrientation(LinearLayout.HORIZONTAL);
			}
		} else if (orientation == LinearLayout.VERTICAL) {
			if (getOrientation() == LinearLayout.HORIZONTAL) {
				changeOrientation(LinearLayout.VERTICAL);
			}
		}
	}

	private void changeOrientation(int orientation) {
		// mFragmentsOrientation = orientation;
		setOrientation(orientation);
		setParamsValues();
		// updateButtonSliderOrientation();
		// updateWidgetsOnOrientationChange(mIsLeftShowing, mIsRightShowing,
		// false);
	}

	public void setParamsValues() {
		horizontalDrawable = R.drawable.slider_horizontal;
		verticalDrawable = R.drawable.slider_vertical;

		LinearLayout.LayoutParams leftParams = (LinearLayout.LayoutParams) mLeft.getLayoutParams();
		LinearLayout.LayoutParams rightParams = (LinearLayout.LayoutParams) mRight.getLayoutParams();
		LinearLayout.LayoutParams slidersParams = (LinearLayout.LayoutParams) mSliderBar.getLayoutParams();

		if (getOrientation() == LinearLayout.VERTICAL) {
			rightParams.width = LayoutParams.MATCH_PARENT;
			leftParams.width = LayoutParams.MATCH_PARENT;
			rightParams.height = mRightHeight;
			leftParams.height = mLeftHeight;
			slidersParams.width = LayoutParams.MATCH_PARENT;
			slidersParams.height = mSliderBarConst;
			mSliderBar.setBackgroundResource(horizontalDrawable);
		} else {
			rightParams.width = mRightWidth;
			leftParams.width = mLeftWidth;
			rightParams.height = LayoutParams.MATCH_PARENT;
			leftParams.height = LayoutParams.MATCH_PARENT;
			slidersParams.width = mSliderBarConst;
			slidersParams.height = LayoutParams.MATCH_PARENT;
			mSliderBar.setBackgroundResource(verticalDrawable);
		}

	}

	public void setSlidersDrawables(int verticalDrawable, int horizontalDrawable) {
		this.verticalDrawable = verticalDrawable;
		this.horizontalDrawable = horizontalDrawable;
		if (getOrientation() == LinearLayout.VERTICAL) {
			mSliderBar.setBackgroundResource(horizontalDrawable);

		} else {
			mSliderBar.setBackgroundResource(verticalDrawable);
		}
	}

	public void setLeftRightWidth() {
		mLeftWidth = mLeft.getWidth();
		mRightWidth = mRight.getWidth();
	}

	public void setLeftRightHeight() {
		mLeftHeight = mLeft.getHeight();
		mRightHeight = mRight.getHeight();
	}

	public void updateWidgetsOnOrientationChange(Boolean isLeftShowing, Boolean isRightShowing, Boolean isScreenRotation) {
		int virtualSliderSize = 0;
		if (mIsSliderVisible) {
			virtualSliderSize = mSliderBarConst;
		}

		float preWidth;
		float preHeight;

		if (!isRightShowing) {
			Log.v("1", "1");
			preWidth = (float) mScreenWidth / (mScreenWidth - (mRightWidth + virtualSliderSize));
			preHeight = (float) mScreenHeight / (mScreenHeight - (mRightHeight + virtualSliderSize));
		} else {
			Log.v("3", "3");
			preWidth = (float) mScreenWidth / mLeftWidth;
			preHeight = (float) mScreenHeight / mLeftHeight;
		}

		mScreenHeight = ((TwoPanelsBaseActivity) mContext).getScreenHeight() - ((TwoPanelsBaseActivity) mContext).getActionBarHeight()
				- ((TwoPanelsBaseActivity) mContext).getStatusBarHeight();
		mScreenWidth = ((TwoPanelsBaseActivity) mContext).getScreenWidth();
		float newWidth;
		float newHeight;

		if (isScreenRotation) {
			if (getOrientation() == LinearLayout.VERTICAL) {
				// Log.v("isScreenRotation", "Vertical");
				newHeight = (mScreenHeight / preHeight);
				mLeftWidth = mScreenWidth;
				mRightWidth = mScreenWidth;
				mLeftHeight = (int) newHeight;
				mRightHeight = (int) mScreenHeight - (mLeftHeight + virtualSliderSize);
			} else {
				// Log.v("isScreenRotation", "Vertical");
				newWidth = (mScreenWidth / preWidth);
				mLeftHeight = mScreenHeight;
				mRightHeight = mScreenHeight;
				mLeftWidth = (int) newWidth;
				mRightWidth = mScreenWidth - (mLeftWidth + virtualSliderSize);
			}
		} else {
			if (getOrientation() == LinearLayout.VERTICAL) {
				// Log.v("!isScreenRotation", "vertical");
				newHeight = (mScreenHeight / preWidth);
				mLeftHeight = (int) newHeight;
				mRightHeight = (int) mScreenHeight - (mLeftHeight + virtualSliderSize);
			} else {
				// Log.v("!isScreenRotation", "Horizontal");
				newWidth = (mScreenWidth / preHeight);
				mLeftWidth = (int) newWidth;
				mRightWidth = mScreenWidth - (mLeftWidth + virtualSliderSize);
			}
		}

		// Set the position of the views
		if (getOrientation() == LinearLayout.HORIZONTAL) {
			if (!isLeftShowing) {
				Log.v("!isLeftShowing", "HORIZONTAL");
				resetWidthWidget(mRight, mScreenWidth);
				resetWidthWidget(mLeft, mLeftWidth);
				if (isScreenRotation) {
					mLeft.setX(-(mLeftWidth + virtualSliderSize));
					mRight.setX(mLeft.getWidth() - mLeftWidth);
					mSliderBar.setX(mLeft.getWidth() - mLeftWidth - virtualSliderSize);
				} else {
					mLeft.setX(-mLeftWidth - virtualSliderSize);
					mLeft.setY(0);
					mSliderBar.setX(-mLeftWidth - virtualSliderSize);
					mSliderBar.setY(mLeftHeight);
					mRight.setX(-mLeftWidth - virtualSliderSize);
					mRight.setY(mLeftHeight + virtualSliderSize);

				}
			} else if (!isRightShowing) {
				resetWidthWidget(mRight, mRightWidth);
				resetWidthWidget(mLeft, mScreenWidth);
				mLeft.setX(0);
			} else {
				resetWidthWidget(mRight, mRightWidth);
				resetWidthWidget(mLeft, mLeftWidth);
				mLeft.setX(0);

				if (isScreenRotation) {
					mRight.setX(mLeft.getWidth() + virtualSliderSize);
				} else {
					mRight.setX(0);
				}
			}
		} else {
			if (!isLeftShowing) {
				Log.v("!isLeftShowing", "vertical");
				resetHeightWidget(mRight, mScreenHeight);
				resetHeightWidget(mLeft, mLeftHeight);
				if (isScreenRotation) {
					mLeft.setY(-mLeftHeight - virtualSliderSize);
					mRight.setY(mLeft.getHeight() - mLeftHeight);
					mSliderBar.setY(mLeft.getHeight() - mLeftHeight - virtualSliderSize);
				} else {
					mSliderBar.setX(mLeft.getWidth());
					mSliderBar.setY(-mLeftHeight - virtualSliderSize);
					mLeft.setX(0);
					mLeft.setY(-mLeftHeight - virtualSliderSize);
					mRight.setX(mLeft.getWidth() + virtualSliderSize);
					mRight.setY(-mLeftHeight - virtualSliderSize);
				}
			} else if (!isRightShowing) {
				Log.v("!isRightShowing", "horizontal");
				resetHeightWidget(mRight, mRightHeight);
				resetHeightWidget(mLeft, mScreenHeight);
				if (isScreenRotation) {
					mLeft.setY(0);
					mRight.setY(mLeft.getHeight() + virtualSliderSize);
				} else {
					mSliderBar.setY(0);
					mLeft.setY(0);
					mRight.setY(0);
				}
			} else {
				Log.v("Panel doble", " ");
				resetHeightWidget(mRight, mRightHeight);
				resetHeightWidget(mLeft, mLeftHeight);
				mLeft.setY(0);

				if (isScreenRotation) {
					mRight.setY(mLeft.getHeight() + virtualSliderSize);
				} else {
					mRight.setY(0);
				}
			}
		}
	}

	public void showLeft() {
		int virtualSliderSize = 0;
		if (mIsSliderVisible) {
			virtualSliderSize = mSliderBarConst;
		}
		if (getOrientation() == LinearLayout.HORIZONTAL) {
			translateWidgetsByX(mLeftWidth + virtualSliderSize, mLeft, mRight, mSliderBar);
			startAnimation(mRight, "MyWidth", mRight.getWidth(), mScreenWidth - mLeftWidth - virtualSliderSize);
		} else {
			translateWidgetsByY(mLeftHeight + virtualSliderSize, mLeft, mRight, mSliderBar);
			startAnimation(mRight, "MyHeight", mRight.getHeight(), mScreenHeight - mLeftHeight - virtualSliderSize);
		}
	}

	public void showRight() {
		int virtualSliderSize = 0;
		if (mIsSliderVisible) {
			virtualSliderSize = mSliderBarConst;
		}
		if (getOrientation() == LinearLayout.HORIZONTAL) {
			startAnimation(mLeft, "MyWidth", mLeft.getWidth(), mScreenWidth - (mRightWidth + virtualSliderSize));
		} else {
			startAnimation(mLeft, "MyHeight", mLeft.getHeight(), mLeftHeight);
		}
	}

	public void hideLeft() {
		int virtualSliderSize = 0;
		if (mIsSliderVisible) {
			virtualSliderSize = mSliderBarConst;
		}
		if (getOrientation() == LinearLayout.HORIZONTAL) {
			resetWidthWidget(mLeft, mLeftWidth);
			resetWidthWidget(mRight, mRightWidth);
			requestLayout();
			// Translate view left off screeen and move right to the left
			translateWidgetsByX(-1 * (mLeftWidth + virtualSliderSize), mLeft, mRight, mSliderBar);
			// Expand right view
			mRight.setClickable(false);
			startAnimation(mRight, "MyWidth", mRight.getWidth(), mScreenWidth);
		} else {
			resetHeightWidget(mLeft, mLeftHeight);
			resetHeightWidget(mRight, mRightHeight);
			requestLayout();
			// Translate bottom view to top
			translateWidgetsByY(-1 * (mLeftHeight + virtualSliderSize), mRight, mLeft, mSliderBar);
			// Expand bottom view
			startAnimation(mRight, "MyHeight", mRight.getHeight(), mScreenHeight);
		}

	}

	public void hideLeftNoAnimate() {
		int virtualSliderSize = 0;
		if (mIsSliderVisible) {
			virtualSliderSize = mSliderBarConst;
		}
		if (getOrientation() == LinearLayout.HORIZONTAL) {
			resetWidthWidget(mLeft, mLeftWidth);
			resetWidthWidget(mRight, mScreenWidth);
			requestLayout();
			mLeft.setX(-1 * (mLeftWidth + virtualSliderSize));
			mSliderBar.setX(mLeft.getWidth() - mLeftWidth - virtualSliderSize);
			mRight.setX(mLeft.getWidth() - mLeftWidth);
		} else {
			resetHeightWidget(mLeft, mLeftHeight);
			resetHeightWidget(mRight, mScreenHeight);
			requestLayout();
			mLeft.setY(-1 * (mLeftHeight + virtualSliderSize));
			mSliderBar.setY(mLeft.getHeight() - mLeftHeight - virtualSliderSize);
			mRight.setY(mLeft.getHeight() - mLeftHeight);
		}
	}

	public void hideRightNoAnimate() {
		int virtualSliderSize = 0;
		if (mIsSliderVisible) {
			virtualSliderSize = mSliderBarConst;
		}
		if (getOrientation() == LinearLayout.HORIZONTAL) {
			Log.v("hideRightNoAnimate", "horizontal");
			resetWidthWidget(mLeft, mScreenWidth);
			resetWidthWidget(mRight, mRightWidth);
			requestLayout();
			mLeft.setX(0);
			mSliderBar.setX(mLeft.getWidth());
			mRight.setX(mLeft.getWidth() + virtualSliderSize);
		} else {
			Log.v("hideRightNoAnimate", "vertical");
			resetHeightWidget(mLeft, mScreenHeight);
			resetHeightWidget(mRight, mRightHeight);
			requestLayout();
			mLeft.setY(0);
			mSliderBar.setY(mLeft.getHeight());
			mRight.setY(mLeft.getHeight() + virtualSliderSize);
		}
	}
	
	public void showTwoPanels() {
		int virtualSliderSize = 0;
		if (mIsSliderVisible) {
			virtualSliderSize = mSliderBarConst;
		}
		if (getOrientation() == LinearLayout.HORIZONTAL) {
			resetWidthWidget(mLeft, mLeftWidth);
			resetWidthWidget(mRight, mRightWidth);
			requestLayout();
			mLeft.setX(0);
			mSliderBar.setX(mLeft.getWidth());
			mRight.setX(mLeft.getWidth()+virtualSliderSize);
		} else {
			resetHeightWidget(mLeft, mLeftHeight);
			resetHeightWidget(mRight, mRightHeight);
			requestLayout();
			mLeft.setY(0);
			mSliderBar.setY(mLeft.getHeight());
			mRight.setY(mLeft.getHeight()+virtualSliderSize);
		}

	}

	public void hideRight() {

		int virtualSliderSize = 0;
		if (mIsSliderVisible) {
			virtualSliderSize = mSliderBarConst;
		}
		if (getOrientation() == LinearLayout.HORIZONTAL) {
			resetWidthWidget(mLeft, mLeftWidth);
			resetWidthWidget(mRight, mRightWidth);
			requestLayout();
			// Expand left view
			startAnimation(mLeft, "MyWidth", mLeft.getWidth() + virtualSliderSize, mScreenWidth);
		} else {
			resetHeightWidget(mLeft, mLeftHeight);
			resetHeightWidget(mRight, mRightHeight);
			requestLayout();
			// Expand left view
			startAnimation(mLeft, "MyHeight", mLeft.getHeight() + virtualSliderSize, mScreenHeight);
		}
	}

	public void setWidthsFromWeight(float leftHeight, float rightHeight) {
		float sumWeights = leftHeight + rightHeight;
		int virtualSliderSize = 0;
		if (mIsSliderVisible) {
			virtualSliderSize = mSliderBarConst;
		}
		mLeftWidth = (int) (((mScreenWidth - virtualSliderSize) / sumWeights) * leftHeight);
		mRightWidth = (int) (((mScreenWidth - virtualSliderSize) / sumWeights) * rightHeight);

		ViewGroup.LayoutParams leftParams = mLeft.getLayoutParams();
		leftParams.width = mLeftWidth;
		mLeft.setLayoutParams(leftParams);
		ViewGroup.LayoutParams RightParams = mRight.getLayoutParams();
		RightParams.width = mRightWidth;
		mRight.setLayoutParams(RightParams);
	}

	public void setHeightsFromWeight(float leftHeight, float rightHeight) {
		float sumWeights = leftHeight + rightHeight;
		int virtualSliderSize = 0;
		if (mIsSliderVisible) {
			virtualSliderSize = mSliderBarConst;
		}
		mLeftHeight = (int) (((mScreenHeight - virtualSliderSize) / sumWeights) * leftHeight);
		mRightHeight = (int) (((mScreenHeight - virtualSliderSize) / sumWeights) * rightHeight);

		ViewGroup.LayoutParams leftParams = mLeft.getLayoutParams();
		leftParams.height = mLeftHeight;
		mLeft.setLayoutParams(leftParams);
		ViewGroup.LayoutParams RightParams = mRight.getLayoutParams();
		RightParams.height = mRightHeight;
		mRight.setLayoutParams(RightParams);
		Log.v("setHeightsFromWeight mLeftHeight: " + mLeftHeight, "mRightHeight: " + mRightHeight);
	}

	private void translateWidgetsByX(int deltaX, View... views) {
		for (final View v : views) {
			v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
			v.animate().translationXBy(deltaX).setDuration(ANIM_DURATION).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					v.setLayerType(View.LAYER_TYPE_NONE, null);
				}
			});
		}
	}

	private void translateWidgetsByY(int deltaY, View... views) {
		for (final View v : views) {
			v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
			v.animate().translationYBy(deltaY).setDuration(ANIM_DURATION).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					v.setLayerType(View.LAYER_TYPE_NONE, null);
				}
			});
		}
	}

	private void resetWidthWidget(View v, int width) {
		LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) v.getLayoutParams();
		p.width = width;
		p.weight = 0;
	}

	private void resetHeightWidget(View v, int height) {
		LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) v.getLayoutParams();
		p.height = height;
		p.weight = 0;
	}

	private void startAnimation(View v, String property, int value1, int value2) {
		ObjectAnimator anim = new ObjectAnimator();
		anim = ObjectAnimator.ofInt(v, property, value1, value2).setDuration(ANIM_DURATION);
		anim.addListener((AnimatorListener) new MyCustomAnimatorListener());
		anim.start();
	}

	public void changeSliderVisitility() {

		// mSliderBar.setVisibility(View.VISIBLE);
		if (mIsSliderVisible) {
			if (getOrientation() == LinearLayout.HORIZONTAL) {
				startAnimation(mSliderBar, "MyWidth", mSliderBarConst, 0);
				startAnimation(mRight, "MyWidth", mRight.getWidth(), mRight.getWidth() + (mSliderBarConst / 2));
				startAnimation(mLeft, "MyWidth", mLeft.getWidth(), mLeft.getWidth() + (mSliderBarConst / 2));
			} else {
				startAnimation(mSliderBar, "MyHeight", mSliderBarConst, 0);
				startAnimation(mRight, "MyHeight", mRight.getHeight(), mRight.getHeight() + (mSliderBarConst / 2));
				startAnimation(mLeft, "MyHeight", mLeft.getHeight(), mLeft.getHeight() + (mSliderBarConst / 2));
			}
			mIsSliderVisible = false;
		} else {
			if (getOrientation() == LinearLayout.HORIZONTAL) {
				startAnimation(mSliderBar, "MyWidth", 0, mSliderBarConst);
				startAnimation(mRight, "MyWidth", mRight.getWidth(), mRight.getWidth() - (mSliderBarConst / 2));
				startAnimation(mLeft, "MyWidth", mLeft.getWidth(), mLeft.getWidth() - (mSliderBarConst / 2));
			} else {
				startAnimation(mSliderBar, "MyHeight", 0, mSliderBarConst);
				startAnimation(mRight, "MyHeight", mRight.getHeight(), mRight.getHeight() - (mSliderBarConst / 2));
				startAnimation(mLeft, "MyHeight", mLeft.getHeight(), mLeft.getHeight() - (mSliderBarConst / 2));
			}
			mIsSliderVisible = true;
		}
	}

	public void updateSliderVisivility() {
		if (mIsSliderVisible) {
			// mSliderBar.setVisibility(View.VISIBLE);
			mSizeSliderinVIew = mSliderBarConst;
		} else {
			// mSliderBar.setVisibility(View.GONE);
			mSizeSliderinVIew = 0;
		}
	}

	public View getLeftView() {
		return (mLeft);
	}

	public View getRightView() {
		return (mRight);
	}

	public void setLeftWeight(float leftWeight) {
		this.mLeftWeight = leftWeight;
	}

	public void setRightWeight(float rightWeight) {
		this.mRrightWeight = rightWeight;
	}

	public ImageView getSliderBar() {
		return mSliderBar;
	}

	public void setmSliderBarConst(int mSliderBarConst) {
		this.mSliderBarConst = mSliderBarConst;
		if (getOrientation() == VERTICAL) {
			resetHeightWidget(mSliderBar, mSliderBarConst);
		} else {
			resetWidthWidget(mSliderBar, mSliderBarConst);
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent me) {
		if (view != mSliderBar) {
			return false;
		}
		if (me.getAction() == MotionEvent.ACTION_DOWN) {
			if (getOrientation() == VERTICAL) {
				mPointerOffset = me.getRawY() - mLeft.getMeasuredHeight();
			} else {
				mPointerOffset = me.getRawX() - mLeft.getMeasuredWidth();
			}
			return true;

		} else if (me.getAction() == MotionEvent.ACTION_MOVE) {
			if (getOrientation() == VERTICAL) {
				setViewsHeight((int) (me.getRawY() - mPointerOffset));
			} else {
				setViewsWidth((int) (me.getRawX() - mPointerOffset));
			}
		}
		return true;
	}

	private boolean setViewsHeight(int height) {
		ViewGroup.LayoutParams leftParams = mLeft.getLayoutParams();
		if (mRight.getMeasuredHeight() < 1 && height > leftParams.height) {
			return false;
		}
		if (height >= 0) {
			if (height + mSliderBarConst <= mScreenHeight) {
				leftParams.height = height;
			}
		}
		mLeft.setLayoutParams(leftParams);

		ViewGroup.LayoutParams rightParams = mRight.getLayoutParams();
		int virtualHeight = mScreenHeight - leftParams.height - mSliderBarConst;
		if (virtualHeight > -1) {
			rightParams.height = mScreenHeight - leftParams.height - mSliderBarConst;
		} else {
			return false;
		}
		mLeftHeight = leftParams.height;
		mRightHeight = rightParams.height;
		mRight.setLayoutParams(rightParams);
		return true;
	}

	private boolean setViewsWidth(int width) {
		ViewGroup.LayoutParams leftParams = (LayoutParams) mLeft.getLayoutParams();
		if (mRight.getMeasuredWidth() < 1 && width > leftParams.width - mSliderBarConst) {
			return false;
		}
		if (width >= 0) {
			if (width + mSliderBarConst <= mScreenWidth) {
				leftParams.width = width;
			}
		}
		mLeft.setLayoutParams(leftParams);

		ViewGroup.LayoutParams rightParams = mRight.getLayoutParams();
		int virtualWidth = mScreenWidth - leftParams.width - mSliderBarConst;
		if (virtualWidth > -1) {
			rightParams.width = mScreenWidth - leftParams.width - mSliderBarConst;
		} else {
			return false;
		}
		mLeftWidth = leftParams.width;
		mRightWidth = rightParams.width;
		mRight.setLayoutParams(rightParams);
		return true;
	}

	private class MyCustomAnimatorListener implements AnimatorListener {
		@Override
		public void onAnimationStart(Animator animation) {
			// Avoid click during animation
			// Manage if fragment extends from RightFragment and LeftFragment
			try {
				((LeftFragment) ((TwoPanelsBaseActivity) mContext).getmLeftFragment()).getmSlideLeftButton().setClickable(false);
				((RightFragment) ((TwoPanelsBaseActivity) mContext).getmRightFragment()).getmSlideRightButton().setClickable(false);
			} catch (Exception e) {
				
			}
		}

		@Override
		public void onAnimationEnd(Animator animation) {
			try {
				((LeftFragment) ((TwoPanelsBaseActivity) mContext).getmLeftFragment()).getmSlideLeftButton().setClickable(true);
				((RightFragment) ((TwoPanelsBaseActivity) mContext).getmRightFragment()).getmSlideRightButton().setClickable(true);
			} catch (Exception e) {
			}
		}

		@Override
		public void onAnimationCancel(Animator animation) {
		}

		@Override
		public void onAnimationRepeat(Animator animation) {
		}

	}

}
