package com.yeyu.googleplay.Adapter.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.yeyu.googleplay.R;
import com.yeyu.googleplay.Utils.BitmapHelper;
import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.domain.AppInfo;
import com.yeyu.googleplay.domain.SubjectInfo;
import com.yeyu.googleplay.http.HttpHelper;

/**
 * Created by gaoyehua on 2016/8/16.
 */
public class SubjectHolder extends BaseHolder<SubjectInfo> {


    private BitmapUtils mBitmapUtils;
    private ImageView iv_pic;
    private TextView tv_title;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_subject);

        iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        //加载图片
        mBitmapUtils = BitmapHelper.getmBitmapUtils();

        return view;
    }

    @Override
    public void refreshView(SubjectInfo data) {
        tv_title.setText(data.des);
        //展示图片
        mBitmapUtils.display(iv_pic, HttpHelper.URL+"image?name="
                +data.url);
    }
}
