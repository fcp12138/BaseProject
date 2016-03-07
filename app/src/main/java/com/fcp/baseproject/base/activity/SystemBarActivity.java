package com.fcp.baseproject.base.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.fcp.baseproject.R;

/**
 * 沉浸式状态栏Activity
 * Created by fcp on 2016/3/4.
 */
public class SystemBarActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && showSystemBar()) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(getSystemBarColorSource());//通知栏所需颜色
            // 设置状态栏的文字颜色
            tintManager.setNavigationBarTintEnabled(true);
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 获得系统状态栏颜色
     */
    protected int getSystemBarColorSource(){
        return R.color.system_bar_default_color;
    }


    /**
     * 显示系统状态栏
     * @return boolean
     */
    protected boolean showSystemBar(){
        return true;
    }

}
