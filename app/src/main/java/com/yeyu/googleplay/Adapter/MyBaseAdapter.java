package com.yeyu.googleplay.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.yeyu.googleplay.Adapter.holder.BaseHolder;
import com.yeyu.googleplay.Adapter.holder.MoreHolder;
import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.View.PagerTab;

import java.util.ArrayList;

/**
 * Created by gaoyehua on 2016/8/16.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    private static final int TYPE_NORMAl =0;// 正常布局
    private static final int TYPE_MORE =1;//  加载更多

    private ArrayList<T> data;

    public MyBaseAdapter(ArrayList<T> data){
        this.data =data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int posotion) {
        return data.get(posotion);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //返回布局类型的个数
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //返回布局类型
    @Override
    public int getItemViewType(int position) {
        if(position ==getCount() -1){
            //最后一个条目
            return TYPE_MORE;
        }else {
            return getInnerType(position);
        }
    }

    //子类可以重写此方法来更改返回的布局类型
    public int getInnerType(int position){
        return TYPE_NORMAl;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        BaseHolder holder;
        if(view ==null){
            // 1. 加载布局文件
            // 2. 初始化控件 findViewById
            // 3. 打一个标记tag
            if(getItemViewType(position) ==TYPE_MORE){
                //加载更多类型
                holder =new MoreHolder(hasMore());
            }else {
                holder =getHolder(position);
            }
        }else {
            holder =(BaseHolder) view.getTag();
        }

        //4.刷新数据
        if(getItemViewType(position) !=TYPE_MORE){
            holder.setData(getItem(position));
        }else {
            //加载更多布局
            MoreHolder moreHolder =(MoreHolder) holder;
            if(moreHolder.getData() ==MoreHolder.STATE_MORE_MORE){
                loadMore(moreHolder);
            }
        }

        return holder.getRootView();
    }

    // 子类可以重写此方法来决定是否可以加载更多
    public boolean hasMore() {
        return true;// 默认都是有更多数据的
    }
    // 返回当前页面的holder对象, 必须子类实现
    public abstract BaseHolder<T> getHolder(int position);

    //标记是否在加载更多
    private boolean isLoadMore =false;
    //加载更多
    public void loadMore(final MoreHolder holder){
        if(!isLoadMore){
            isLoadMore=true;
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    final ArrayList<T> moreData =onLoadMore();

                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if(moreData!=null){
                                //每页二十条数据，小于则是到了最后一条
                                if(moreData.size()<20){
                                    holder.setData(MoreHolder.STATE_MORE_NONE);
                                    Toast.makeText(UIUtils.getContext(),
                                            "没有更多数据了", Toast.LENGTH_SHORT)
                                            .show();
                                } else {
                                    // 还有更多数据
                                    holder.setData(MoreHolder.STATE_MORE_MORE);
                                }
                                //将更多的数据追加到集合中
                                data.addAll(moreData);
                                MyBaseAdapter.this.notifyDataSetChanged();
                            }else {
                                //加载更多失败
                                holder.setData(MoreHolder.STATE_MORE_ERROR);
                            }
                            isLoadMore =false;
                        }
                    });
                }
            }.start();

        }

    }
    //加载更对的数据则有子类实现
    public abstract ArrayList<T> onLoadMore();

    //获取当前集合的大小
    public int getListSize(){
        return data.size();
    }
}
