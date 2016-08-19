package com.yeyu.googleplay.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.View.LoadingPage;

import java.util.ArrayList;

/**
 * Created by gaoyehua on 2016/8/16.
 */
public abstract class BaseFragment extends Fragment {

    private LoadingPage mLoadingPage;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // 注意:此处一定要调用BaseFragment的onCreateSuccessView, 否则栈溢出
        mLoadingPage = new LoadingPage(UIUtils.getContext()) {
            @Override
            public View onCreateSuccessView() {
                // 注意:此处一定要调用BaseFragment的onCreateSuccessView, 否则栈溢出
                return BaseFragment.this.onCreateSuccessView();
            }

            @Override
            public ResultState onLoad() {
                return BaseFragment.this.onLoad();
            }
        };
        return mLoadingPage;
    }

    // 加载成功的布局, 必须由子类来实现
    public abstract View onCreateSuccessView();

    // 加载网络数据, 必须由子类来实现
    public abstract LoadingPage.ResultState onLoad();

    //开始加载数据
    public void loadData(){
        if(mLoadingPage!=null){
            mLoadingPage.loadData();
        }
    }

    // 对网络返回数据的合法性进行校验
    public LoadingPage.ResultState check(Object obj) {
        if (obj != null) {
            if (obj instanceof ArrayList) {// 判断是否是集合
                ArrayList list = (ArrayList) obj;

                if (list.isEmpty()) {
                    return LoadingPage.ResultState.STATE_EMPTY;
                } else {
                    return LoadingPage.ResultState.STATE_SUCCESS;
                }
            }
        }

        return LoadingPage.ResultState.STATE_ERROR;
    }
}
