package com.example.twopanelsdemo;

import com.desarrollodroide.twopanels.TwoPanelsBaseActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends TwoPanelsBaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBaseOrientation(LinearLayout.HORIZONTAL);
		MyRightFragment mRightFragment = new MyRightFragment();
		MyLeftFragment mLeftFragment = new MyLeftFragment();
		getFragmentManager().beginTransaction().add(R.id.right, mRightFragment).commit();
		getFragmentManager().beginTransaction().add(R.id.left, mLeftFragment).commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.horizontal) {
			setBaseOrientation(LinearLayout.HORIZONTAL);
			updateButtonSliderOrientation();
		} else if (itemId == R.id.vertical) {
			setBaseOrientation(LinearLayout.VERTICAL);
			updateButtonSliderOrientation();
		} else if (itemId == R.id.hide_left_animate) {
			slideFragmentsToLeft();
		} else if (itemId == R.id.hide_right_animate) {
			slideFragmentsToRight();
		} else if (itemId == R.id.hide_left) {
			hideLeft();
		} else if (itemId == R.id.hide_right) {
			hideRight();
		} else if (itemId == R.id.show_two_fragments) {
			showTwoFragments();
		} else if (itemId == R.id.switch_slider) {
			switchSliderVisitility();
		} else {
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
}
