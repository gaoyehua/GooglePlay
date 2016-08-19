package com.yeyu.googleplay.Fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yeyu.googleplay.Utils.LogUtils;
import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.View.Fly.ShakeListener;
import com.yeyu.googleplay.View.Fly.StellarMap;
import com.yeyu.googleplay.View.LoadingPage;
import com.yeyu.googleplay.http.proocol.RecommendProtocol;

import java.util.ArrayList;
import java.util.Random;

/**
 * 推荐
 * @author Kevin
 * @date 2015-10-27
 */
public class RecommendFragment extends BaseFragment {

	private ArrayList<String> data;
	@Override
	public View onCreateSuccessView() {
		final StellarMap stellar=new StellarMap(UIUtils.getContext());
		stellar.setAdapter(new recommendAdapter());
		//将控件设置为6列9行显示
		stellar.setRegularity(6,9);
		//设置内边距
		int pandding =UIUtils.dip2px(10);
		stellar.setPadding(pandding,pandding,pandding,pandding);

		//设置默认的数据
		stellar.setGroup(0,true);

		final ShakeListener shake =new ShakeListener(UIUtils.getContext());
		shake.setOnShakeListener(new ShakeListener.OnShakeListener() {
			@Override
			public void onShake() {
				stellar.zoomIn();//跳到下一个界面
			}
		});

		return stellar;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		RecommendProtocol protocol =new RecommendProtocol();
		data =protocol.getData(0);

		return check(data);
	}

	class recommendAdapter implements StellarMap.Adapter{

		//返回组的数量
		@Override
		public int getGroupCount() {
			return 2;
		}
		//返回某组的数量
		@Override
		public int getCount(int group) {
			int count =data.size()/getGroupCount();
			if(group ==getGroupCount() -1){
				//最后一页，除不尽加载到下一页
				count+=data.size()%getGroupCount();
			}
			return count;
		}

		@Override
		public View getView(int group, int position, View convertView) {
			//
			position+=(group)*getCount(group -1);

			final String keyword =data.get(position);

			TextView view =new TextView(UIUtils.getContext());
			view.setText(keyword);

			//随机
			Random random =new Random();
			//随机字体大小,16-25
			int size =16+random.nextInt(10);
			view.setTextSize(size);
			//随机颜色，r,g,b->0-255,30-230,颜色不能过亮或过暗
			int r =30+random.nextInt(200);
			int g =30+random.nextInt(200);
			int b =30+random.nextInt(200);
			view.setTextColor(Color.rgb(r,g,b));

			view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(UIUtils.getContext(), keyword,
							Toast.LENGTH_SHORT).show();
				}
			});

			return view;
		}

		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn) {
			LogUtils.e("isZoomIn:"+isZoomIn);
			if(isZoomIn){
				//往下滑加载上一页
				if(group>0){
					group--;
				}else {
					//跳到最后一页
					group=getGroupCount() -1;
				}
			}else {
				if(group<getGroupCount()-1){
					//往上滑
					group++;
				}else {
					//加载第一页
					group =0;
				}
			}

			return group;
		}
	}

}
