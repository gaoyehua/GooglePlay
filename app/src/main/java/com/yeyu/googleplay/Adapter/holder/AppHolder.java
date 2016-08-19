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
import com.yeyu.googleplay.http.HttpHelper;

/**
 * Created by gaoyehua on 2016/8/16.
 */
public class AppHolder extends BaseHolder<AppInfo> {

    private TextView tvName;
    private TextView tvSize;
    private TextView tvDes;
    private ImageView ivIcon;
    private RatingBar rbStar;
    private BitmapUtils mBitmapUtils;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_home);
        // 2. 初始化控件
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvSize = (TextView) view.findViewById(R.id.tv_size);
        tvDes = (TextView) view.findViewById(R.id.tv_des);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        rbStar = (RatingBar) view.findViewById(R.id.rb_star);

        //加载图片
        mBitmapUtils = BitmapHelper.getmBitmapUtils();



        return view;
    }

    @Override
    public void refreshView(AppInfo data) {
        tvName.setText(data.name);
        tvSize.setText(Formatter.formatFileSize(UIUtils.getContext(),data.size));
        tvDes.setText(data.des);
        rbStar.setRating(data.stars);

        //展示图片
        mBitmapUtils.display(ivIcon, HttpHelper.URL+"image?name="
                +data.iconUrl);
    }
}
