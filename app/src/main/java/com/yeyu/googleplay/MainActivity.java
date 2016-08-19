package com.yeyu.googleplay;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Adapter;

import com.yeyu.googleplay.Fragment.BaseFragment;
import com.yeyu.googleplay.Fragment.FragmentFactory;
import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.View.PagerTab;

public class MainActivity extends BaseActivity {

    private ViewPager mViewPager;
    private PagerTab mPagerTab;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.activity_main);
        //getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_launcher);

        mPagerTab = (PagerTab) findViewById(R.id.pager_tab);
        mViewPager = (ViewPager) findViewById(R.id.vp_main_viewpager);

        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        mPagerTab.setViewPager(mViewPager);//将指示器与viewpager绑定

        mPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = (BaseFragment)
                        FragmentFactory.createFragment(position);
                //加载页面，七个Fragment
                // 开始加载数据
                fragment.loadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /*

     */

    class MyAdapter extends FragmentPagerAdapter {

        private String[] mTabName;
        public MyAdapter(FragmentManager fm) {
            super(fm);
            mTabName = UIUtils.getStringArray(R.array.tab_names);
        }

        //返回页签标题
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabName[position];
        }

        //页面对象
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        //页面数目
        @Override
        public int getCount() {
            return mTabName.length;
        }
    }
}
