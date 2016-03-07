package com.fcp.baseproject.album.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fcp.baseproject.R;
import com.fcp.baseproject.album.bean.Folder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件夹Adapter
 * Created by Nereo on 2015/4/7.
 */
public class FolderAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    private List<Folder> mFolders = new ArrayList<>();

    int lastSelected = 0;

    private DisplayImageOptions mDisplayImageOptions;

    public FolderAdapter(Context context){
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_album_default_error)          // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_album_default_error)      // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_album_default_error)           // 设置图片加载或解码过程中发生错误显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)                        //设置图片的解码类型
                .cacheInMemory(true)                                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(false)                                         // 设置下载的图片是否缓存在SD卡中
                .build();
    }

    /**
     * 设置数据集
     */
    public void setData(List<Folder> folders) {
        if(folders != null && folders.size()>0){
            mFolders = folders;
        }else{
            mFolders.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFolders.size()+1;
    }

    @Override
    public Folder getItem(int i) {
        if(i == 0) return null;
        return mFolders.get(i-1);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = mInflater.inflate(R.layout.album_item_list_folder, viewGroup, false);
            holder = new ViewHolder(view);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        if (holder != null) {
            if(i == 0){
                holder.name.setText("所有图片");
                holder.size.setText(getTotalImageSize()+"张");
                if(mFolders.size()>0){
                    Folder f = mFolders.get(0);
                    ImageLoader.getInstance().displayImage("file://"+f.cover.path,holder.cover, mDisplayImageOptions);
                }
            }else {
                holder.bindData(getItem(i));
            }
            if(lastSelected == i){
                holder.indicator.setVisibility(View.VISIBLE);
            }else{
                holder.indicator.setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }

    private int getTotalImageSize(){
        int result = 0;
        if(mFolders != null && mFolders.size()>0){
            for (Folder f: mFolders){
                result += f.images.size();
            }
        }
        return result;
    }

    public void setSelectIndex(int i) {
        if(lastSelected == i) return;
        lastSelected = i;
        notifyDataSetChanged();
    }

    public int getSelectIndex(){
        return lastSelected;
    }

    class ViewHolder{
        ImageView cover;
        TextView name;
        TextView size;
        ImageView indicator;
        ViewHolder(View view){
            cover = (ImageView)view.findViewById(R.id.cover);
            name = (TextView) view.findViewById(R.id.name);
            size = (TextView) view.findViewById(R.id.size);
            indicator = (ImageView) view.findViewById(R.id.indicator);
            view.setTag(this);
        }

        void bindData(Folder data) {
            name.setText(data.name);
            size.setText(data.images.size()+"张");
            // 显示图片
            ImageLoader.getInstance().displayImage("file://"+data.cover.path,cover, mDisplayImageOptions);
        }
    }

}
