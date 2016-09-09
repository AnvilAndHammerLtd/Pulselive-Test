
package com.kyriakosalexandrou.pulselive.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemWithDetails implements Parcelable {
    // using composition to minimise duplication
    private BasicItem mBasicItem;
    private String mBody;

    public ItemWithDetails(BasicItem basicItem, String body) {
        mBasicItem = basicItem;
        mBody = body;
    }

    public BasicItem getBasicItem() {
        return mBasicItem;
    }

    public String getBody() {
        return mBody;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(mBasicItem, flags);
        out.writeString(mBody);
    }

    public static final Parcelable.Creator<ItemWithDetails> CREATOR
            = new Parcelable.Creator<ItemWithDetails>() {
        public ItemWithDetails createFromParcel(Parcel in) {
            return new ItemWithDetails(in);
        }

        public ItemWithDetails[] newArray(int size) {
            return new ItemWithDetails[size];
        }
    };

    private ItemWithDetails(Parcel in) {
        mBasicItem = in.readParcelable(BasicItem.class.getClassLoader());
        mBody = in.readString();
    }
}
