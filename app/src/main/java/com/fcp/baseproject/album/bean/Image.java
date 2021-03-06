package com.fcp.baseproject.album.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片实体
 * Created by Nereo on 2015/4/7.
 */
public class Image implements Parcelable {
    public String path;
    public String name;
    public long time;

    Image(){

    }

    public Image(String path, String name, long time){
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        try {
            return this.path.equalsIgnoreCase(((Image) o).path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }


    @Override
    public int describeContents() {
        return 3;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(name);
        dest.writeLong(time);
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            Image image = new Image();
            image.path = source.readString();
            image.name = source.readString();
            image.time = source.readLong();
            return image;
        }
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
