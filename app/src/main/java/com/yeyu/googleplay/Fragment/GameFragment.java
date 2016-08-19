package com.yeyu.googleplay.Fragment;


import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.View.LoadingPage;

/**
 * 游戏
 * @author Kevin
 * @date 2015-10-27
 */
public class GameFragment extends BaseFragment {

	@Override
	public View onCreateSuccessView() {
		TextView view = new TextView(UIUtils.getContext());
		view.setText("GameFragment");
		view.setTextColor(Color.BLACK);
		return view;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		return LoadingPage.ResultState.STATE_SUCCESS;
	}

}
