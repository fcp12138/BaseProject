package com.fcp.baseproject.album.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fcp.baseproject.R;
import com.fcp.baseproject.album.bean.Image;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.List;

/**
 * 多选适配器
 * Created by fcp on 2016/3/6.
 */
public class ImageMultiAdapter extends ImageGridAdapter {


    public ImageMultiAdapter(Context context, List<Image> mSelectedImages, boolean showCamera) {
        super(context, mSelectedImages, showCamera);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if(type == TYPE_CAMERA){
            convertView = mInflater.inflate(R.layout.album_item_list_camera, parent, false);
            convertView.setTag(null);
        }else if(type == TYPE_NORMAL){
            ViewHold holde;
            if(convertView == null){
                convertView = mInflater.inflate(R.layout.album_item_list_multi, parent, false);
                holde = new ViewHold(convertView);
            }else{
                holde = (ViewHold) convertView.getTag();
                if(holde == null){
                    convertView = mInflater.inflate(R.layout.album_item_list_multi, parent, false);
                    holde = new ViewHold(convertView);
                }
            }
            holde.bindData(getItem(position));
        }

        return convertView;
    }

    /**
     * 改变选择的状态
     */
    public void changeSelectStatue(int position, View convertView, ViewGroup parent){
        ViewHold holde;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.album_item_list_multi, parent, false);
            holde = new ViewHold(convertView);
        }else{
            holde = (ViewHold) convertView.getTag();
            if(holde == null){
                convertView = mInflater.inflate(R.layout.album_item_list_multi, parent, false);
                holde = new ViewHold(convertView);
            }
        }
        holde.change(getItem(position));
    }




    class ViewHold{

        ImageView image;
        ImageView indicator;
        View mask;

        ViewHold(View view){
            image = (ImageView) view.findViewById(R.id.image);
            indicator = (ImageView) view.findViewById(R.id.checkmark);
            mask = view.findViewById(R.id.mask);
            view.setTag(this);
        }

        void bindData(Image data){
            if(data == null) return;
            // 处理单选和多选状态
            change(data);
            File imageFile = new File(data.path);
            // 显示图片
            ImageLoader.getInstance().displayImage("file://"+imageFile.getAbsolutePath(),image, mDisplayImageOptions);
        }

        private void change(Image data){
            if(mSelectedImages.contains(data)){
                // 设置选中状态
                indicator.setImageResource(R.mipmap.ic_album_btn_selected);
                mask.setVisibility(View.VISIBLE);
            }else{
                // 未选择
                indicator.setImageResource(R.mipmap.ic_album_btn_unselected);
                mask.setVisibility(View.GONE);
            }
        }


    }
}
