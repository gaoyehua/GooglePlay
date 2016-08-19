package com.yeyu.googleplay.Fragment;


import android.view.View;
import android.widget.ListView;

import com.yeyu.googleplay.Adapter.MyBaseAdapter;
import com.yeyu.googleplay.Adapter.holder.AppHolder;
import com.yeyu.googleplay.Adapter.holder.BaseHolder;
import com.yeyu.googleplay.Adapter.holder.HomeHolder;
import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.View.LoadingPage;
import com.yeyu.googleplay.View.MyListView;
import com.yeyu.googleplay.domain.AppInfo;
import com.yeyu.googleplay.http.proocol.AppProtocol;
import com.yeyu.googleplay.http.proocol.HomeProtocol;

import java.util.ArrayList;

/**
 * 应用
 * @author Kevin
 * @date 2015-10-27
 */
public class AppFragment extends BaseFragment {
	private ArrayList<AppInfo> data;

	// 如果加载数据成功, 就回调此方法, 在主线程运行
	@Override
	public View onCreateSuccessView() {
		//Apppager的listview
		MyListView view = new MyListView(UIUtils.getContext());
		view.setAdapter(new AppAdapter(data));
		return view;
	}

	// 运行在子线程,可以直接执行耗时网络操作
	@Override
	public LoadingPage.ResultState onLoad() {

		AppProtocol protocol =new AppProtocol();
		data =protocol.getData(0);

		return check(data);
	}

	/*
	ListView的适配器
	 */
	class AppAdapter extends MyBaseAdapter<AppInfo> {


		public AppAdapter(ArrayList<AppInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder<AppInfo> getHolder(int position) {
			return new AppHolder();
		}

		@Override
		public ArrayList<AppInfo> onLoadMore() {

			AppProtocol protocol =new AppProtocol();

			ArrayList<AppInfo> moreData =protocol.getData(getListSize());

			return moreData;
		}
	}


}
