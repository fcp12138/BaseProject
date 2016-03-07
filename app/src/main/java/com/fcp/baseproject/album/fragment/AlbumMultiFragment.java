package com.fcp.baseproject.album.fragment;

import android.view.View;
import android.widget.Toast;

import com.fcp.baseproject.album.adapter.ImageGridAdapter;
import com.fcp.baseproject.album.adapter.ImageMultiAdapter;
import com.fcp.baseproject.album.bean.Image;

/**
 * 多选
 * Created by fcp on 2016/3/5.
 */
public class AlbumMultiFragment extends AlbumFragment{


    @Override
    ImageGridAdapter createGridAdapter() {
        return new ImageMultiAdapter(getContext(),resultList,isShowCamera);
    }

    @Override
    void gridClickPicture(View view, int i) {
        Image image = mImageAdapter.getItem(i);
        if(image == null)return;
        if (resultList.contains(image)) {//已经有了
            resultList.remove(image);
            if (mCallBackListener != null) {
                mCallBackListener.onImageUnselected(image);
            }
        } else {
            // 判断选择数量问题
            if(mDesireImageCount == resultList.size()){
                Toast.makeText(getActivity(), "已经达到最高选择数量", Toast.LENGTH_SHORT).show();
                return;
            }
            resultList.add(image);
            if (mCallBackListener != null) {
                mCallBackListener.onImageSelected(image);
            }
        }
        ((ImageMultiAdapter)mImageAdapter).changeSelectStatue(i, view, mGridView);//更换状态
    }
}
