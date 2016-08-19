package com.yeyu.googleplay.Utils;


import com.lidroid.xutils.BitmapUtils;

/**
 * Created by gaoyehua on 2016/8/18.
 */
public class BitmapHelper {

    private static BitmapUtils mBitmapUtils =null;
    //单例模式,懒汉模式
    public static BitmapUtils getmBitmapUtils(){
        if(mBitmapUtils ==null){
            synchronized (BitmapHelper.class){
                if(mBitmapUtils ==null){
                    mBitmapUtils =new BitmapUtils(UIUtils.getContext());
                }
            }
        }

        return mBitmapUtils;
    }


}
