package com.yeyu.googleplay.Adapter.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lidroid.xutils.BitmapUtils;
import com.yeyu.googleplay.R;
import com.yeyu.googleplay.Utils.BitmapHelper;
import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.http.HttpHelper;

import java.util.ArrayList;

/**
 * Created by gaoyehua on 2016/8/19.
 */
public class HomeHeaderHolder extends BaseHolder<ArrayList<String>> {

    private  ArrayList<String> data;
    private ViewPager mViewPager;
    private LinearLayout llContainer;

    private int mPreviousPos;
    @Override
    public View initView() {
        //创建一个根布局，相对布局
        RelativeLayout rlroot =new RelativeLayout(UIUtils.getContext());
        //初始化布局参数
        AbsListView.LayoutParams params =new AbsListView.LayoutParams(
                AbsListView.LayoutParams.MATCH_PARENT,UIUtils.dip2px(150));
        rlroot.setLayoutParams(params);

        //viewpager
        mViewPager = new ViewPager(UIUtils.getContext());
        RelativeLayout.LayoutParams vpParams =new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        rlroot.addView(mViewPager,vpParams);//将viewPager添加给相对布局

        //初始化指示器
        llContainer = new LinearLayout(UIUtils.getContext());
        llContainer.setOrientation(LinearLayout.HORIZONTAL);//水平方向

        RelativeLayout.LayoutParams llParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置内边距
        int padding =UIUtils.dip2px(10);
        llContainer.setPadding(padding,padding,padding,padding);

        //添加规则
        llParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//底部对齐
        llParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);//右边对齐

        rlroot.addView(llContainer,llParams);

        return rlroot;
    }

    @Override
    public void refreshView(final ArrayList<String> data) {

        this.data =data;
        //填充ViewPager的数据
        mViewPager.setAdapter(new HomeHeaderAdapter());
        mViewPager.setCurrentItem(data.size()*10000);

        //初始化指示器
        for(int i=0;i<data.size();i++){
            ImageView point =new ImageView(UIUtils.getContext());

            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if(i==0){
                point.setImageResource(R.drawable.indicator_selected);
            }else {
                point.setImageResource(R.drawable.indicator_normal);
                params.leftMargin=UIUtils.dip2px(4);

            }
            point.setLayoutParams(params);
            llContainer.addView(point);

        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //选中的时候
                position =position%data.size();

                //当前点被选中
                ImageView point =(ImageView) llContainer.getChildAt(position);
                point.setImageResource(R.drawable.indicator_selected);

                // 上个点变为不选中
                ImageView prePoint = (ImageView) llContainer
                        .getChildAt(mPreviousPos);
                prePoint.setImageResource(R.drawable.indicator_normal);

                mPreviousPos = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //启动轮播条自动播放
        HomeHeaderTask homeHeaderTask =new HomeHeaderTask();
        homeHeaderTask.start();

    }

    class HomeHeaderTask implements Runnable{

        public void start(){
            //移除之前发送的所有消息
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            UIUtils.getHandler().postDelayed(this,3000);
        }
        @Override
        public void run() {
            int currentItem =mViewPager.getCurrentItem();
            currentItem++;
            mViewPager.setCurrentItem(currentItem);

            //继续发消息延迟三秒，实现内循环
            UIUtils.getHandler().postDelayed(this,3000);
        }
    }

    class HomeHeaderAdapter extends PagerAdapter{

        private BitmapUtils mBitmapUtils;

        public HomeHeaderAdapter(){
            mBitmapUtils = BitmapHelper.getmBitmapUtils();
        }
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position =position%data.size();

            String url =data.get(position);
            ImageView view =new ImageView(UIUtils.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_XY);

            mBitmapUtils.display(view, HttpHelper.URL+"image?name="+url);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
