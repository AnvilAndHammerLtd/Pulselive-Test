
package com.kyriakosalexandrou.pulselive.models;

import android.util.Log;

import com.kyriakosalexandrou.pulselive.services.ContentBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class ContentList extends ContentBase {
    private static final String TAG = ContentList.class.getName();
    private static String RESPONSE_ARRAY_TEXT = "items";
    private List<ContentListItem> mItems = new ArrayList<>();

    public ContentList(String jsonResult) {
        convertJsonToPojo(jsonResult);
    }

    @Override
    protected void convertJsonToPojo(String jsonResult) {
        try {
            JSONObject contentList = (JSONObject) new JSONTokener(jsonResult).nextValue();
            JSONArray items = contentList.getJSONArray(RESPONSE_ARRAY_TEXT);

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);

                int id = (int) item.get(RESPONSE_ID_TEXT);
                String title = (String) item.get(RESPONSE_TITLE_TEXT);
                String subtitle = (String) item.get(RESPONSE_SUBTITLE_TEXT);
                String date = (String) item.get(RESPONSE_DATE_TEXT);

                mItems.add(i, new ContentListItem(id, title, subtitle, date));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * @return The items
     */
    public List<ContentListItem> getItems() {
        return mItems;
    }
}