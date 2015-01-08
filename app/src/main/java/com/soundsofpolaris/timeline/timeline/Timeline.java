package com.soundsofpolaris.timeline.timeline;

import android.os.Parcel;
import android.os.Parcelable;

public class Timeline implements Parcelable{
    private long mGid;
    private String mTitle;
    private String mDesc;
    private int mColor;
    private String mImageFileName;
    private int mTotalEvents;

    public Timeline(long gid, String title, String desc, int color, String imageFileName, int totalEvents) {
        mGid = gid;
        mTitle = title;
        mDesc = desc;
        mColor = color;
        mImageFileName = imageFileName;
        mTotalEvents = totalEvents;
    }

    public long getId() {
        return mGid;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDesc;
    }

    public int getColor() {
        return mColor;
    }

    public String getImageFileName() {
        return mImageFileName;
    }

    public int getTotalEvents(){ return mTotalEvents; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(mGid);
        out.writeString(mTitle);
        out.writeString(mDesc);
        out.writeInt(mColor);
        out.writeString(mImageFileName);
        out.writeInt(mTotalEvents);
    }

    public static final Parcelable.Creator<Timeline> CREATOR = new Parcelable.Creator<Timeline>() {
        public Timeline createFromParcel(Parcel in) {
            return new Timeline(in.readLong(), in.readString(), in.readString(), in.readInt(), in.readString(), in.readInt());
        }

        public Timeline[] newArray(int size) {
            return new Timeline[size];
        }
    };


}
