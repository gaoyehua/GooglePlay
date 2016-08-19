package com.yeyu.googleplay.Utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import com.yeyu.googleplay.R;

/**
 * Created by gaoyehua on 2016/8/19.
 */
public class DrawableUtils {

    //获取shape对象
    public static GradientDrawable getGradientDrawable(int color,int radius){
        //xml文件中对应此类
        GradientDrawable shape =new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);//矩形
        shape.setCornerRadius(radius);//圆角半径
        shape.setColor(color);//颜色

        return shape;
    }

    //获取状态选择器
    public static StateListDrawable getSelector(Drawable normal,Drawable press){
        StateListDrawable selector =new StateListDrawable();
        selector.addState(new int[] {android.R.attr.state_pressed},press);
        selector.addState(new int[] {},normal);//默认图片
        return selector;
    }

    //获取状态选择器
    public static StateListDrawable getSelector(int normal, int press, int radius) {
        GradientDrawable bgNormal = getGradientDrawable(normal, radius);
        GradientDrawable bgPress = getGradientDrawable(press, radius);
        StateListDrawable selector = getSelector(bgNormal, bgPress);
        return selector;

    }
}
