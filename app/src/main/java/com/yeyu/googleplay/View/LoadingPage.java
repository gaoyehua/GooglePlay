package com.yeyu.googleplay.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.yeyu.googleplay.R;
import com.yeyu.googleplay.Utils.UIUtils;

/**
 * Created by gaoyehua on 2016/8/16.
 */
public abstract class LoadingPage extends FrameLayout {

    private static final int STATE_LOAD_UNDO = 1;// 未加载
    private static final int STATE_LOAD_LOADING = 2;// 正在加载
    private static final int STATE_LOAD_ERROR = 3;// 加载失败
    private static final int STATE_LOAD_EMPTY = 4;// 数据为空
    private static final int STATE_LOAD_SUCCESS = 5;// 加载成功

    private int mCurrentState = STATE_LOAD_UNDO;// 当前状态

    private View mLoadingPage;
    private View mErrorPage;
    private View mEmptyPage;
    private View mSuccessPage;

    public LoadingPage(Context context) {
        super(context);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /*
    初始化布局
     */
    private void initView(){
        //初始化加载中的布局
        if(mLoadingPage ==null){
            mLoadingPage = UIUtils.inflate(R.layout.page_loading);
            addView(mLoadingPage);
        }
        if(mEmptyPage ==null){
            mEmptyPage =UIUtils.inflate(R.layout.page_empty);
            addView(mEmptyPage);
        }

        //初始化加载错误，请重试
        if (mErrorPage ==null){
            mErrorPage =UIUtils.inflate(R.layout.page_error);
            //设置点击事件
            Button btnRetry =(Button) mErrorPage.findViewById(R.id.btn_retry);
            btnRetry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //重新加载网络数据
                    loadData();
                }
            });

            addView(mErrorPage);
        }

        showRightPage();
    }

    /*
    根据当前布局，显示正确的布局
     */
    private void showRightPage() {

        mLoadingPage.
                setVisibility(mCurrentState ==STATE_LOAD_LOADING || mCurrentState==STATE_LOAD_UNDO ? VISIBLE:GONE);
        mErrorPage.setVisibility(mCurrentState ==STATE_LOAD_ERROR ?VISIBLE:GONE);
        mEmptyPage.setVisibility(mCurrentState ==STATE_LOAD_EMPTY ?VISIBLE:GONE);

        //当处于成功的状态时
        if(mSuccessPage ==null && mCurrentState ==STATE_LOAD_SUCCESS){
            mSuccessPage =onCreateSuccessView();
            if(mSuccessPage !=null){
                addView(mSuccessPage);
            }
        }
        if(mSuccessPage !=null){
            mSuccessPage.setVisibility(mCurrentState ==STATE_LOAD_SUCCESS ?VISIBLE:GONE);
        }
    }

    //加载数据
    public void loadData(){
        if(mCurrentState !=STATE_LOAD_LOADING){
            //如果当前没有加载，就开始加载数据
            mCurrentState =STATE_LOAD_LOADING;
            new  Thread(){
                @Override
                public void run() {
                    super.run();
                    final ResultState resultState =onLoad();

                    //UI更新在主线程
                    UIUtils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            if(resultState!=null){
                                mCurrentState=resultState.getState();
                                //根据最新的状态来刷新页面
                                showRightPage();
                            }
                        }
                    });
                }
            }.start();
        }
    }


    public abstract View onCreateSuccessView();

    // 加载网络数据, 返回值表示请求网络结束后的状态
    public abstract ResultState onLoad();

    public enum ResultState {
        STATE_SUCCESS(STATE_LOAD_SUCCESS), STATE_EMPTY(STATE_LOAD_EMPTY), STATE_ERROR(
                STATE_LOAD_ERROR);

        private int state;

        private ResultState(int state) {
            this.state = state;
        }

        public int getState() {
            return state;
        }
    }

}
