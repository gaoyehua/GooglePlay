package com.yeyu.googleplay.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.yeyu.googleplay.R;
import com.yeyu.googleplay.Utils.LogUtils;

/**
 * 工具类：
 * 自定义控件，加载图片时，按比例展示，完美适配所有屏幕
 * <p/>
 * Created by gaoyehua on 2016/8/19.
 */
public class RatioLayout extends FrameLayout {

    private float ratio;

    public RatioLayout(Context context) {
        super(context);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取属性值
        // 当自定义属性时, 系统会自动生成属性相关id, 此id通过R.styleable来引用
        TypedArray typedArray = context.obtainStyledAttributes
                (attrs, R.styleable.RatioLayout);
        //// id = 属性名_具体属性字段名称 (此id系统自动生成)
        ratio = typedArray.getFloat(R.styleable.RatioLayout_ratio, -1);
        //回收typedArray，提高性能
        typedArray.recycle();
        LogUtils.e("ratio:" + ratio);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    测量宽高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 1. 获取宽度
        // 2. 根据宽度和比例ratio, 计算控件的高度
        // 3. 重新测量控件

        //1000000000000000000000111001110
        System.out.println("widthMeasureSpec:" + widthMeasureSpec);

        // MeasureSpec.AT_MOST; 至多模式, 控件有多大显示多大, wrap_content
        // MeasureSpec.EXACTLY; 确定模式, 类似宽高写死成dip, match_parent
        // MeasureSpec.UNSPECIFIED; 未指定模式.
        int width =MeasureSpec.getSize(widthMeasureSpec);//获取宽度值
        int widthMode =MeasureSpec.getMode(widthMeasureSpec);
        int height =MeasureSpec.getSize(heightMeasureSpec);
        int heightMode =MeasureSpec.getMode(heightMeasureSpec);

        //宽度确定，高度不确定，ratio合法，计算高度值
        if(widthMode ==MeasureSpec.EXACTLY
                && heightMode !=MeasureSpec.EXACTLY && ratio >0){

            int imageWidth =width -getPaddingLeft()-getPaddingRight();

            int imageHeight =(int)(imageWidth/ratio +0.5f);
            //控件高度
            height =imageHeight+getPaddingTop()+getPaddingBottom();

            //根据最新的高度来重新生成heightMeasureSpec
            heightMeasureSpec =MeasureSpec.makeMeasureSpec(height,
                    MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
