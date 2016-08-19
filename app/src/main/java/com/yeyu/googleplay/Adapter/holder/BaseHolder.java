package com.yeyu.googleplay.Adapter.holder;

import android.view.View;
import android.widget.BaseAdapter;

/**
 * 自定义封装的ListVIew的容器
 *
 * Created by gaoyehua on 2016/8/16.
 */
public abstract class BaseHolder<T> {

    private View mRootView; //一个Item的根布局

    private T data;
    //当new这个布局时，加载这个布局
    public BaseHolder(){
        mRootView =initView();
        //3.打一个tag标记
        mRootView.setTag(this);
    }

    //1.加载布局，
    // 2.初始化控件
    public abstract View initView();
    //返回Item的布局对象
    public View getRootView(){
        return mRootView;
    }

    //设置当前的Item的数据
    public void setData(T data){
        this.data =data;
        refreshView(data);
    }
    //获取当前的Item对象
    public T getData(){
        return data;
    }

    //4.刷新数据
    public abstract void refreshView(T data);


}
