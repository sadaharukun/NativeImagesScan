package com.example.yaoxinxin.imagescan.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yaoxinxin on 16/3/11.
 */
public class ImageBean implements Parcelable {


    private String folderName;

    private int count;

    private String topImagePath;

    public ImageBean(String folderName, int count, String topImagePath) {
        this.folderName = folderName;
        this.count = count;
        this.topImagePath = topImagePath;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTopImagePath() {
        return topImagePath;
    }

    public void setTopImagePath(String topImagePath) {
        this.topImagePath = topImagePath;
    }

    public ImageBean() {
    }

    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
        @Override
        public ImageBean createFromParcel(Parcel in) {
            ImageBean bean = new ImageBean();
            bean.setFolderName(in.readString());
            bean.setCount(in.readInt());
            bean.setTopImagePath(in.readString());
            return bean;
        }

        @Override
        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(folderName);
        dest.writeInt(count);
        dest.writeString(topImagePath);
    }
}
