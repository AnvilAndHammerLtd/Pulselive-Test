
package com.kyriakosalexandrou.pulselive.models;

import android.util.Log;

import com.kyriakosalexandrou.pulselive.services.ContentBase;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.Serializable;

public class ContentDetail extends ContentBase implements Serializable {
    private static final String TAG = ContentDetail.class.getName();
    private static String RESPONSE_ITEM_TEXT = "item";
    private static String RESPONSE_BODY_TEXT = "body";
    private ContentDetailItem mItem;

    public ContentDetail(String jsonResult) {
        convertJsonToPojo(jsonResult);
    }

    @Override
    protected void convertJsonToPojo(String jsonResult) {
        try {
            JSONObject contentDetail = (JSONObject) new JSONTokener(jsonResult).nextValue();
            JSONObject item = contentDetail.getJSONObject(RESPONSE_ITEM_TEXT);

            int id = item.getInt(RESPONSE_ID_TEXT);
            String title = (String) item.get(RESPONSE_TITLE_TEXT);
            String subtitle = (String) item.get(RESPONSE_SUBTITLE_TEXT);
            String body = (String) item.get(RESPONSE_BODY_TEXT);
            String date = (String) item.get(RESPONSE_DATE_TEXT);

            mItem = new ContentDetailItem(id, title, subtitle, body, date);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * @return The mItem
     */
    public ContentDetailItem getItem() {
        return mItem;
    }
}
