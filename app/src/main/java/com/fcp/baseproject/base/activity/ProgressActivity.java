package com.fcp.baseproject.base.activity;

import android.view.View;
import android.widget.FrameLayout;

import com.fcp.baseproject.base.view.ProgressWheel;

/**
 * 有等待进度条的Activity
 * Created by fcp on 2016/3/5.
 */
public class ProgressActivity extends ToolbarActivity {

    private ProgressWheel mProgressWheel;
    private View content;

    @Override
    protected View initUserLayout(int layoutResID) {
        content = layoutInflater.inflate(layoutResID, null);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.addView(content , FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        //进度圈
        mProgressWheel = new ProgressWheel(this);
        mProgressWheel.setBarColor(0xFF0088FF);
        mProgressWheel.spin();
        frameLayout.addView(mProgressWheel , FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT);
        return frameLayout;
    }

    protected void showContent(){
        if(mProgressWheel!=null){
            mProgressWheel.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
        }
    }

    protected void showProgress(){
        if(mProgressWheel!=null){
            mProgressWheel.setVisibility(View.VISIBLE);
            content.setVisibility(View.INVISIBLE);
        }
    }


}
