package com.yeyu.googleplay.Adapter.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yeyu.googleplay.R;
import com.yeyu.googleplay.Utils.UIUtils;
import com.yeyu.googleplay.domain.AppInfo;

/**
 * Created by gaoyehua on 2016/8/21.
 */
public class DetailDesHolder extends BaseHolder<AppInfo> {

    private TextView tvDes;
    private TextView tvAuthor;
    private ImageView ivArrow;
    private RelativeLayout rlToggle;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.layout_detail_desinfo);

        tvDes = (TextView) view.findViewById(R.id.tv_detail_des);
        tvAuthor = (TextView) view.findViewById(R.id.tv_detail_author);
        ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
        rlToggle = (RelativeLayout) view.findViewById(R.id.rl_detail_toggle);

        rlToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        return view;
    }

    @Override
    public void refreshView(AppInfo data) {

        tvDes.setText(data.des);
        tvAuthor.setText(data.author);

        //放在消息队列中执行，解决只有3行时却显示7行的bug
        tvDes.post(new Runnable() {
            @Override
            public void run() {
                int height = getShortHeight();
                mParams = (LinearLayout.LayoutParams) tvDes.getLayoutParams();
                mParams.height = height;
                tvDes.setLayoutParams(mParams);
            }
        });

    }

    private boolean isOpen = false;
    private LinearLayout.LayoutParams mParams;

    /*
    应用详情的开关
     */
    protected void toggle() {
        int shortHeight = getShortHeight();
        int longHeight = getLongHeight();
        //属性动画
        ValueAnimator animator = null;

        if (isOpen) {
            //关闭
            isOpen = false;
            if (longHeight > shortHeight) {
                //大于七行时，启动动画
                animator = ValueAnimator.ofInt(longHeight, shortHeight);
            }

        } else {
            // 打开
            isOpen = true;
            if (longHeight > shortHeight) {// 只有描述信息大于7行,才启动动画
                animator = ValueAnimator.ofInt(shortHeight, longHeight);
            }
        }

        if (animator != null) {
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Integer height = (Integer) valueAnimator.getAnimatedValue();
                    mParams.height = height;
                    tvDes.setLayoutParams(mParams);
                }
            });

            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    //动画结束
                    final ScrollView scrollView = getScrollView();

                    //为了运行的稳定和安全，可以将滑动到底部的方法放到消息队列中执行
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(View.FOCUS_DOWN);//滑动到底部
                        }
                    });
                    if (isOpen) {
                        ivArrow.setImageResource(R.drawable.arrow_up);
                    } else {
                        ivArrow.setImageResource(R.drawable.arrow_down);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            animator.setDuration(200);
            animator.start();
        }
    }

    /*
    获取7行字体的高度
     */
    private int getShortHeight() {
        //模拟一个七行的字体高度
        int width = tvDes.getMeasuredWidth();//宽度

        TextView view = new TextView(UIUtils.getContext());
        view.setText(getData().des);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        view.setMaxLines(7);//最大行数

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width,
                View.MeasureSpec.EXACTLY);//宽不变，确定值
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000,
                View.MeasureSpec.AT_MOST);//高度包裹内容，跪一个参数暂时写2000
        // ，也可以写屏幕宽度

        view.measure(widthMeasureSpec, heightMeasureSpec);

        return view.getMeasuredHeight();
    }

    /*
    获取所有描述信息字体的高度
     */
    private int getLongHeight() {

        int width = tvDes.getMeasuredWidth();//宽度

        TextView view = new TextView(UIUtils.getContext());
        view.setText(getData().des);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        //view.setMaxLines(7);//最大行数

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width,
                View.MeasureSpec.EXACTLY);//宽不变，确定值
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000,
                View.MeasureSpec.AT_MOST);//高度包裹内容，跪一个参数暂时写2000
        // ，也可以写屏幕宽度

        view.measure(widthMeasureSpec, heightMeasureSpec);

        return view.getMeasuredHeight();

    }

    /*
    获取scrollView，

	// 获取ScrollView, 一层一层往上找,
	// 知道找到ScrollView后才返回;注意:一定要保证父控件或祖宗控件有ScrollView,否则死循环
     */
    private ScrollView getScrollView() {
        ViewParent parent = tvDes.getParent();

        while (!(parent instanceof ScrollView)) {

            parent = parent.getParent();
        }

        return (ScrollView) parent;
    }
}
