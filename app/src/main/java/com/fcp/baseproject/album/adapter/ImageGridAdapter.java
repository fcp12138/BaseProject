package com.fcp.baseproject.album.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.fcp.baseproject.R;
import com.fcp.baseproject.album.bean.Image;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片适配器
 * Created by fcp on 2016/3/6.
 */
public abstract class ImageGridAdapter extends BaseAdapter{

    protected static final int TYPE_CAMERA = 0;
    protected static final int TYPE_NORMAL = 1;

    protected boolean showCamera = true;
    protected LayoutInflater mInflater;

    protected List<Image> mImages = new ArrayList<>();
    protected List<Image> mSelectedImages = new ArrayList<>();

    protected DisplayImageOptions mDisplayImageOptions;

    public ImageGridAdapter(Context context,List<Image> mSelectedImages, boolean showCamera) {
        this.showCamera = showCamera;
        mInflater = LayoutInflater.from(context);
        this.mSelectedImages = mSelectedImages;
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_album_default_error)            // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_album_default_error)          // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_album_default_error)               // 设置图片加载或解码过程中发生错误显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)                            // 设置图片的解码类型//
                .cacheInMemory(true)                                            // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(false)                                             // 设置下载的图片是否缓存在SD卡中
                .build();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(showCamera){
            return position == 0 ? TYPE_CAMERA : TYPE_NORMAL;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getCount() {
        return showCamera ? mImages.size()+1 : mImages.size();
    }

    @Override
    public Image getItem(int position) {
        if(showCamera){
            if(position == 0){
                return null;
            }
            return mImages.get(position-1);
        }else{
            return mImages.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public void setShowCamera(boolean b){
        if(showCamera == b) return;
        showCamera = b;
        notifyDataSetChanged();
    }

    public boolean isShowCamera(){
        return showCamera;
    }

    /**
     * 设置数据集
     */
    public void setData(List<Image> images) {
        mSelectedImages.clear();
        if(images != null && images.size()>0){
            mImages = images;
        }else{
            mImages.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 通过图片路径设置默认选择
     */
    public void setDefaultSelected(ArrayList<Image> resultList) {
        for(Image image : resultList){
            if(image != null){
                mSelectedImages.add(image);
            }
        }
        if(mSelectedImages.size() > 0){
            notifyDataSetChanged();
        }
    }

    /**
     * 更新数据
     */
    public void updata(List<Image> images){
        if(images != null && images.size()>0){
            mImages = images;
        }else{
            mImages.clear();
        }
        notifyDataSetChanged();
    }


}
