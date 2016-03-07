package com.fcp.baseproject.album.fragment;

import android.view.View;

import com.fcp.baseproject.album.adapter.ImageGridAdapter;
import com.fcp.baseproject.album.adapter.ImageSingleAdapter;
import com.fcp.baseproject.album.bean.Image;

/**\
 *
 * 单选
 * Created by fcp on 2016/3/5.
 */
public class AlbumSingleFragment extends AlbumFragment {

    @Override
    ImageGridAdapter createGridAdapter() {
        return new ImageSingleAdapter(getContext(),resultList,isShowCamera);
    }

    @Override
    void gridClickPicture(View view, int i) {
        Image image =  mImageAdapter.getItem(i);
        // 单选模式
        if(mCallBackListener != null && image != null){
            mCallBackListener.onSingleImageSelected(image);
        }
    }
}
