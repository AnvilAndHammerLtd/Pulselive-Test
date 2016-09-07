
package com.kyriakosalexandrou.pulselive.models;

import java.io.Serializable;

public class ItemWithDetails implements Serializable {
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
}
