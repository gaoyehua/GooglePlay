package com.yeyu.googleplay.Fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yeyu.googleplay.Adapter.MyBaseAdapter;
import com.yeyu.googleplay.Adapter.holder.BaseHolder;
import com.yeyu.googleplay.Adapter.holder.HomeHeaderHolder;
import com.yeyu.googleplay.Adapter.holder.HomeHolder;
import com.yeyu.googleplay.HomeDetailActivity;
import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.View.LoadingPage;
import com.yeyu.googleplay.View.MyListView;
import com.yeyu.googleplay.domain.AppInfo;
import com.yeyu.googleplay.http.proocol.HomeProtocol;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页
 * 
 * @author Kevin
 * @date 2015-10-27
 */
public class HomeFragment extends BaseFragment {
	private ArrayList<AppInfo> data;

	private ArrayList<String> mPictureList;
	// 如果加载数据成功, 就回调此方法, 在主线程运行
	@Override
	public View onCreateSuccessView() {
		//homepager的listview
		MyListView view = new MyListView(UIUtils.getContext());

		//添加头布局
		HomeHeaderHolder header =new HomeHeaderHolder();
		view.addHeaderView(header.getRootView());//先添加头布局，再设置Adapter

		view.setAdapter(new HomeAdapter(data));

		if(mPictureList !=null){
			header.setData(mPictureList);
		}
/*
设置主页条目的点击事件
 */
		view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				AppInfo appInfo =data.get(position-1);//去掉头布局

				if(appInfo!=null){
					Intent intent =new Intent(UIUtils.getContext(), HomeDetailActivity.class);
					intent.putExtra("packageName",appInfo.packageName);
					startActivity(intent);
				}

			}
		});


		return view;
	}

	// 运行在子线程,可以直接执行耗时网络操作
	@Override
	public LoadingPage.ResultState onLoad() {
		// 请求网络
		// 请求网络, HttpClient, HttpUrlConnection, XUtils
//		 data = new ArrayList<AppInfo>();
//		 for (int i = 0; i < 20; i++) {
//		 data.add("dhu");
//		 }

		HomeProtocol protocol =new HomeProtocol();
		data =protocol.getData(0);
		mPictureList=protocol.getPictureList();

		return check(data);
	}

	/*
	ListView的适配器
	 */
	class HomeAdapter extends MyBaseAdapter<AppInfo> {


		public HomeAdapter(ArrayList<AppInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder<AppInfo> getHolder(int position) {
			return new HomeHolder();
		}

		@Override
		public ArrayList<AppInfo> onLoadMore() {

			HomeProtocol protocol =new HomeProtocol();

			ArrayList<AppInfo> moreData =protocol.getData(getListSize());

			return moreData;
		}
	}

}