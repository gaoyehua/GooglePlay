package com.yeyu.googleplay;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

    private ActionBarDrawerToggle toggle;
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
        initActionbar();
    }

    // 初始化actionbar
    private void initActionbar() {
        ActionBar actionbar = getSupportActionBar();

        actionbar.setHomeButtonEnabled(true);// home处可以点击
        actionbar.setDisplayHomeAsUpEnabled(true);// 显示左上角返回键,当和侧边栏结合时展示三个杠图片

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);

        // 初始化抽屉开关
        toggle = new ActionBarDrawerToggle(this, drawer,
                R.drawable.ic_drawer_am, R.string.drawer_open,
                R.string.drawer_close);

        toggle.syncState();// 同步状态, 将DrawerLayout和开关关联在一起
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 切换抽屉
                toggle.onOptionsItemSelected(item);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
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
