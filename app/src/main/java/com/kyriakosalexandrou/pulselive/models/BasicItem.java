
package com.kyriakosalexandrou.pulselive.models;

import java.io.Serializable;

public class BasicItem implements Serializable {
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
}
