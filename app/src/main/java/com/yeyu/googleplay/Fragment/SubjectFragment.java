package com.yeyu.googleplay.Fragment;

import android.view.View;

import com.yeyu.googleplay.Adapter.MyBaseAdapter;
import com.yeyu.googleplay.Adapter.holder.BaseHolder;
import com.yeyu.googleplay.Adapter.holder.SubjectHolder;
import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.View.LoadingPage;
import com.yeyu.googleplay.View.MyListView;
import com.yeyu.googleplay.domain.SubjectInfo;
import com.yeyu.googleplay.http.proocol.SubjectProtocol;

import java.util.ArrayList;


/**
 * 专题
 * @author Kevin
 * @date 2015-10-27
 */
public class SubjectFragment extends BaseFragment {

	private ArrayList<SubjectInfo> data;

	//加载布局
	@Override
	public View onCreateSuccessView() {
		MyListView view =new MyListView(UIUtils.getContext());
		view.setAdapter(new SubjectAdapter(data));

		return view;
	}

	//加载数据
	@Override
	public LoadingPage.ResultState onLoad() {

		SubjectProtocol protocol =new SubjectProtocol();
		data =protocol.getData(0);
		return check(data);
	}

	/*
	MyListView的适配器
	 */
	class SubjectAdapter extends MyBaseAdapter<SubjectInfo>{

		public SubjectAdapter(ArrayList<SubjectInfo> data) {
			super(data);
		}

		@Override
		public BaseHolder<SubjectInfo> getHolder(int position) {
			return new SubjectHolder();
		}

		@Override
		public ArrayList<SubjectInfo> onLoadMore() {
			SubjectProtocol protocol =new SubjectProtocol();
			ArrayList<SubjectInfo> moreData =protocol.getData(getListSize());

			return moreData;
		}
	}

}
