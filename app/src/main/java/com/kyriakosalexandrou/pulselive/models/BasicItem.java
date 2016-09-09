
package com.kyriakosalexandrou.pulselive.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BasicItem implements Parcelable {
    private Integer mId;
    private String mTitle;
    private String mSubtitle;
    private String mDate;

    public BasicItem(Integer id, String title, String subtitle, String date) {
        mId = id;
        mTitle = title;
        mSubtitle = subtitle;
        mDate = date;
    }

    public Integer getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public String getDate() {
        return mDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mId);
        out.writeString(mTitle);
        out.writeString(mSubtitle);
        out.writeString(mDate);
    }

    public static final Parcelable.Creator<BasicItem> CREATOR
            = new Parcelable.Creator<BasicItem>() {
        public BasicItem createFromParcel(Parcel in) {
            return new BasicItem(in);
        }

        public BasicItem[] newArray(int size) {
            return new BasicItem[size];
        }
    };

    private BasicItem(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mSubtitle = in.readString();
        mDate = in.readString();
    }
}
