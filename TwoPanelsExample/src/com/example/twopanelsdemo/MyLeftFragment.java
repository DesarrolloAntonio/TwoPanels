package com.example.twopanelsdemo;

import com.desarrollodroide.twopanels.LeftFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MyLeftFragment extends LeftFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		LinearLayout linear = new LinearLayout(getActivity());
		LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.myleft_fragment, linear, true);
		LinearLayout linearInParent = (LinearLayout) mContainer.findViewById(R.id.linearLeft);
		linearInParent.addView(linear);

		return this.mContainer;
	}

}