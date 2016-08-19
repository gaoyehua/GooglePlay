package com.yeyu.googleplay.Fragment;

import android.support.v4.app.Fragment;

import java.util.HashMap;

/**
 * Created by gaoyehua on 2016/8/16.
 */
public class FragmentFactory {

    private static HashMap<Integer, Fragment> mFragmentMap =new HashMap<Integer, Fragment>();

    public static Fragment createFragment(int position){
        //先从集合中取，没有再创建
        Fragment fragment =mFragmentMap.get(position);

        if(fragment ==null){
            switch (position){
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new AppFragment();
                    break;
                case 2:
                    fragment = new GameFragment();
                    break;
                case 3:
                    fragment = new SubjectFragment();
                    break;
                case 4:
                    fragment = new RecommendFragment();
                    break;
                case 5:
                    fragment = new CategoryFragment();
                    break;
                case 6:
                    fragment = new HotFragment();
                    break;

                default:
                      fragment =new HomeFragment();
                    break;
            }
            mFragmentMap.put(position,fragment);
        }
        return fragment;
    }


}



