package com.fcp.baseproject.album;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.fcp.baseproject.R;
import com.fcp.baseproject.album.bean.Image;
import com.fcp.baseproject.album.fragment.AlbumFragment;
import com.fcp.baseproject.album.fragment.AlbumMultiFragment;
import com.fcp.baseproject.album.fragment.AlbumSingleFragment;
import com.fcp.baseproject.base.activity.ToolbarActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * 相册Activity
 * Created by fcp on 2016/3/5.
 */
public class AlbumActivity extends ToolbarActivity implements AlbumFragment.CallBackListener {

    /** 最大图片选择次数，int类型，默认9 */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /** 图片选择模式，默认多选 */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /** 是否显示相机，默认显示 */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /** 选择结果，返回为 ArrayList&lt;String&gt; 图片路径集合  */
    public static final String EXTRA_RESULT = "select_result";

    /** 单选 */
    public static final int MODE_SINGLE = 0;
    /** 多选 */
    public static final int MODE_MULTI = 1;

    private ArrayList<String> resultList = new ArrayList<>();
    private int mDefaultCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithToolbar(R.layout.album_main_activity);
        //判断
        Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, 9);
        int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);//模式
        boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        //相册入参
        Bundle bundle = new Bundle();
        bundle.putInt(AlbumActivity.EXTRA_SELECT_COUNT, mDefaultCount);//选择的最大数量
        bundle.putBoolean(AlbumActivity.EXTRA_SHOW_CAMERA, isShow);//是否开启相机
        //选择的Fragment
        AlbumFragment fragment;
        if(mode == MODE_SINGLE){
            fragment = (AlbumFragment) Fragment.instantiate(this, AlbumSingleFragment.class.getName(), bundle);
            fragment.setCallBackListener(this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.image_frame_layout, fragment)
                    .commit();
            getRightBtn().setVisibility(View.INVISIBLE);
        }else {
            fragment = (AlbumFragment) Fragment.instantiate(this, AlbumMultiFragment.class.getName(), bundle);
            fragment.setCallBackListener(this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.image_frame_layout, fragment)
                    .commit();
            getRightBtn().setBackgroundResource(R.drawable.selector_album_action_btn);
            if(resultList == null || resultList.size()<=0){
                getRightBtn().setText("完成");
                getRightBtn().setEnabled(false);
            }else{
                getRightBtn().setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
                getRightBtn().setEnabled(true);
            }
        }
        //初始化标题栏
        getLeftBtn().setText("返回");
        getTitleText().setText("相册");
        getRightBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resultList != null && resultList.size() >0){
                    // 返回已选择的图片数据
                    Intent data = new Intent();
                    data.putStringArrayListExtra(EXTRA_RESULT, resultList);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }

    @Override
    public void onSingleImageSelected(Image image) {
        Intent data = new Intent();
        resultList.add(image.path);
        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onImageSelected(Image image) {
        if(!resultList.contains(image.path)) {
            resultList.add(image.path);
        }
        // 有图片之后，改变按钮状态
        if(resultList.size() > 0){
            getRightBtn().setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
            if(!getRightBtn().isEnabled()){
                getRightBtn().setEnabled(true);
            }
        }
    }

    @Override
    public void onImageUnselected(Image image) {
        if(resultList.contains(image.path)){
            resultList.remove(image.path);
            getRightBtn().setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
        }else{
            getRightBtn().setText("完成(" + resultList.size() + "/" + mDefaultCount + ")");
        }
        // 当为选择图片时候的状态
        if(resultList.size() == 0){
            getRightBtn().setText("完成");
            getRightBtn().setEnabled(false);
        }
    }

    @Override
    public void onCameraShot(File imageFile) {
        if(imageFile != null) {
            Intent data = new Intent();
            resultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(EXTRA_RESULT, resultList);
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
