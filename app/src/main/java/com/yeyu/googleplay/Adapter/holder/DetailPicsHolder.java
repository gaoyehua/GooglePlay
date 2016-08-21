package com.yeyu.googleplay.Adapter.holder;

import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.yeyu.googleplay.R;
import com.yeyu.googleplay.Utils.BitmapHelper;
import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.domain.AppInfo;
import com.yeyu.googleplay.http.HttpHelper;

import java.util.ArrayList;

/**
 * Created by gaoyehua on 2016/8/21.
 */
public class DetailPicsHolder extends BaseHolder<AppInfo>{

    private ImageView[] IvPics;
    private BitmapUtils mBitmap;

    @Override
    public View initView() {
        View view =UIUtils.inflate(R.layout.layout_detail_picinfo);

        IvPics =new ImageView[5];
        IvPics[0] =(ImageView)  view.findViewById(R.id.iv_pic1);
        IvPics[1] =(ImageView)  view.findViewById(R.id.iv_pic2);
        IvPics[2] =(ImageView)  view.findViewById(R.id.iv_pic3);
        IvPics[3] =(ImageView)  view.findViewById(R.id.iv_pic4);
        IvPics[4] =(ImageView)  view.findViewById(R.id.iv_pic5);

        mBitmap = BitmapHelper.getmBitmapUtils();

        return view;
    }

    @Override
    public void refreshView(AppInfo data) {

        final ArrayList<String> screen =data.screen;

        for (int i=0;i<5;i++){
            if(i <screen.size()){
                mBitmap.display(IvPics[i], HttpHelper.URL+"image?name="
                        +screen.get(i));
                //				ivPics[i].setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						//跳转activity, activity展示viewpager
//						//将集合通过intent传递过去, 当前点击的位置i也可以传过去
//						Intent intent = new Intent();
//						intent.putExtra("list", screen);
//					}
//				});
            }else {
                IvPics[i].setVisibility(View.GONE);
            }

        }

    }
}
