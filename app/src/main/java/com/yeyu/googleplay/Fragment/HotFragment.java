package com.yeyu.googleplay.Fragment;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.yeyu.googleplay.Utils.DrawableUtils;
import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.View.FlowLayout;
import com.yeyu.googleplay.View.LoadingPage;
import com.yeyu.googleplay.http.proocol.HotProtocol;

import java.util.ArrayList;
import java.util.Random;

/**
 * 排行
 * @author Kevin
 * @date 2015-10-27
 */
public class HotFragment extends BaseFragment {

	private ArrayList<String> data;

	@Override
	public View onCreateSuccessView() {
		ScrollView scrollView =new ScrollView(UIUtils.getContext());
		FlowLayout flow =new FlowLayout(UIUtils.getContext());

		int padding =UIUtils.dip2px(10);
		flow.setPadding(padding,padding,padding,padding);

		flow.setHorizontalSpacing(UIUtils.dip2px(6));//设置水平间距
		flow.setVerticalSpacing(UIUtils.dip2px(8));//设置垂直间距

		for(int i=0;i <data.size();i++){
			final String keyword =data.get(i);
			TextView view =new TextView(UIUtils.getContext());
			view.setText(keyword);

			view.setTextColor(Color.WHITE);
			view.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);//18sp
			view.setPadding(padding,padding,padding,padding);
			view.setGravity(Gravity.CENTER);

			//设置随机颜色
			Random random=new Random();
			int r = 30 + random.nextInt(200);
			int g = 30 + random.nextInt(200);
			int b = 30 + random.nextInt(200);

			int color = 0xffcecece;// 按下后偏白的背景色

			StateListDrawable selector = DrawableUtils.getSelector
					(Color.rgb(r,g,b),color,UIUtils.dip2px(6));
			view.setBackgroundDrawable(selector);
			flow.addView(view);

			// 只有设置点击事件, 状态选择器才起作用
			view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(UIUtils.getContext(), keyword,
							Toast.LENGTH_SHORT).show();
				}
			});

		}
		scrollView.addView(flow);
		return scrollView;
	}

	@Override
	public LoadingPage.ResultState onLoad() {
		HotProtocol protocol =new HotProtocol();
		data =protocol.getData(0);
		return check(data);
	}

}
