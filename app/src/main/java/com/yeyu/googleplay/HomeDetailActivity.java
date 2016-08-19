package com.yeyu.googleplay;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.yeyu.googleplay.Adapter.holder.DetailAppInfoHolder;
import com.yeyu.googleplay.Adapter.holder.DetailSafeHolder;
import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.View.LoadingPage;
import com.yeyu.googleplay.domain.AppInfo;
import com.yeyu.googleplay.http.proocol.HomeDetailProtocol;

/**
 * Created by gaoyehua on 2016/8/20.
 */
public class HomeDetailActivity extends BaseActivity {

    private LoadingPage mLoadingPage;
    private String packageName;
    private AppInfo data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoadingPage =new LoadingPage(this) {
            @Override
            public View onCreateSuccessView() {
                return HomeDetailActivity.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return HomeDetailActivity.this.onLoad();
            }
        };

        // setContentView(R.layout.activity_main);
        setContentView(mLoadingPage);// 直接将一个view对象设置给activity

        // 获取从HomeFragment传递过来的包名
        packageName = getIntent().getStringExtra("packageName");

        // 开始加载网络数据
        mLoadingPage.loadData();
    }

    public View onCreateSuccessView() {
        //初始化布局
        View view = UIUtils.inflate(R.layout.page_home_detail);

        //初始化应用信息模块
        FrameLayout flDetailAppInfo =(FrameLayout) view.findViewById(R.id.fl_detail_appinfo);
        //动态给帧布局天填充布局
        DetailAppInfoHolder appInfoHolder = new DetailAppInfoHolder();
        flDetailAppInfo.addView(appInfoHolder.getRootView());
        appInfoHolder.setData(data);

        // 初始化安全描述模块
        FrameLayout flDetailSafe = (FrameLayout) view
                .findViewById(R.id.fl_detail_safe);
        DetailSafeHolder safeHolder = new DetailSafeHolder();
        flDetailSafe.addView(safeHolder.getRootView());
        safeHolder.setData(data);
        return view;


    }

    public LoadingPage.ResultState onLoad() {
        //请求网络加载数据
        HomeDetailProtocol protocol =new HomeDetailProtocol(packageName);
        data =protocol.getData(0);

        if(data!=null){
            return LoadingPage.ResultState.STATE_SUCCESS;
        } else {
            return LoadingPage.ResultState.STATE_ERROR;
        }

    }


}
