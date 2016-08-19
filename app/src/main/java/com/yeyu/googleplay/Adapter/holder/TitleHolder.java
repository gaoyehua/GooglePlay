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
import com.yeyu.googleplay.domain.CategoryInfo;
import com.yeyu.googleplay.http.HttpHelper;

/**
 * Created by gaoyehua on 2016/8/16.
 */
public class TitleHolder extends BaseHolder<CategoryInfo> {


    public TextView tvTitle;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_title);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        return view;
    }

    @Override
    public void refreshView(CategoryInfo data) {
        tvTitle.setText(data.title);
    }
}
