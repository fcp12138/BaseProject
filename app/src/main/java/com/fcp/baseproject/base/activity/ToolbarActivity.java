package com.fcp.baseproject.base.activity;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fcp.baseproject.R;

/**
 * Toolbar注入Activity
 * Created by fcp on 2016/3/4.
 */
public class ToolbarActivity extends SystemBarActivity {

    /**
     * 默认标题
     */
    private TextView titleText;
    /**
     * 默认左右
     */
    private TextView leftText,rightText;
    protected LayoutInflater layoutInflater ;
    /**
     * 高度
     */
    private int ToolbarHeight = 50;

    /**
     * 使用默认toolbar
     * @param layoutResID activity布局
     */
    public void setContentViewWithToolbar(int layoutResID) {
        View toolbarLayoutView = this.setContentViewWithToolbar(layoutResID, R.layout.base_activity_toolbar_default_layout);
        titleText = (TextView) toolbarLayoutView.findViewById(R.id.base_activity_toolbar_default_center);
        leftText = (TextView) toolbarLayoutView.findViewById(R.id.base_activity_toolbar_default_left);
        rightText = (TextView) toolbarLayoutView.findViewById(R.id.base_activity_toolbar_default_right);
        //默认
        leftText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化布局,使用自定义toolbar布局
     * @param layoutResID activity布局
     * @param toolbarLayout toolbar布局（如R.layout.actionbar_base_toolbar_layout）
     * @return toolbarLayoutView （根据这个findViewById得到其中的控件）
     */
    public View setContentViewWithToolbar(int layoutResID ,int toolbarLayout){
        layoutInflater = LayoutInflater.from(this);
        //whole layout
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setFitsSystemWindows(true);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        //toolbar
        View toolbarLayoutView = initToolbar(toolbarLayout, rootLayout);
        //user_layout
        rootLayout.addView(initUserLayout(layoutResID), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //setContentView
        setContentView(rootLayout);
        return toolbarLayoutView;
    }

    /**
     * 使用原生Toolbar
     * @param layoutResID activity布局
     */
    public Toolbar setContentViewWithOriginalToolbar(int layoutResID){
        layoutInflater = LayoutInflater.from(this);
        //whole layout
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setFitsSystemWindows(true);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        //toolbar
        Toolbar toolbar = new Toolbar(this);
        toolbar.setBackgroundResource(getToolbarColorSource());
        setSupportActionBar(toolbar);
        rootLayout.addView(toolbar, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //user_layout
        rootLayout.addView(initUserLayout(layoutResID), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //setContentView
        setContentView(rootLayout);
        return toolbar;
    }


    /**
     * 自定义toolbar layout
     */
    private View initToolbar(int toolbarLayout , ViewGroup root) {
        /*View view = layoutInflater.inflate(R.layout.actionbar_base_layout, root, true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.actionbar_base_layout_toolbar);*/
        Toolbar toolbar = new Toolbar(this);
        toolbar.setBackgroundResource(getToolbarColorSource());
        View toolbarLayoutView = layoutInflater.inflate(toolbarLayout, toolbar , true);
        setSupportActionBar(toolbar);
        float scale = this.getResources().getDisplayMetrics().density;
        root.addView(toolbar,ViewGroup.LayoutParams.MATCH_PARENT,(int) (ToolbarHeight * scale + 0.5f));
        return toolbarLayoutView;
    }

    /**
     * 用户的布局
     * @param layoutResID 布局id
     * @return View
     */
    protected View initUserLayout(int layoutResID) {
        return layoutInflater.inflate(layoutResID,null);
    }

    protected int getToolbarColorSource(){
        return R.color.toolbar_default_color;
    }

    @Override
    protected int getSystemBarColorSource() {
        return getToolbarColorSource();
    }

    protected TextView getLeftBtn() {
        return leftText;
    }

    public TextView getTitleText() {
        return titleText;
    }

    protected TextView getRightBtn() {
        return rightText;
    }
}
