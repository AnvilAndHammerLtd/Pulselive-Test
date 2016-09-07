package com.kyriakosalexandrou.pulselive.services;

import android.os.AsyncTask;
import android.util.Log;

import com.kyriakosalexandrou.pulselive.models.BasicItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class ContentListService extends AsyncTask<String, String, List<BasicItem>> {
    private static final String TAG = ContentListService.class.getName();
    private static String JSON_ITEMS_KEY = "items";
    private ContentListServiceCallback mContentListServiceCallback;
    private ContentServiceHelper mContentServiceHelper;

    public ContentListService(ContentListServiceCallback contentListServiceCallback, String errorMsg) {
        mContentServiceHelper = new ContentServiceHelper(errorMsg);
        mContentListServiceCallback = contentListServiceCallback;
    }

    public void requestData() {
        String url = ContentServiceHelper.BASE_URL + "//dynamic.pulselive.com/test/native/contentList.json";
        execute(url);
    }

    @Override
    protected List<BasicItem> doInBackground(String... args) {
        List<BasicItem> basicItems = null;

        try {
            String result = mContentServiceHelper.fetchUrlData(args[0]);
            basicItems = getItems(result);

        } catch (Exception e) {
            cancel(true);
            e.printStackTrace();

        } finally {
            mContentServiceHelper.mUrlConnection.disconnect();
        }

        return basicItems;
    }

    private List<BasicItem> getItems(String jsonResult) {
        List<BasicItem> basicItem = new ArrayList<>();

        try {
            JSONObject jsonContainerObject = (JSONObject) new JSONTokener(jsonResult).nextValue();
            JSONArray jsonItemsArray = jsonContainerObject.getJSONArray(JSON_ITEMS_KEY);

            for (int i = 0; i < jsonItemsArray.length(); i++) {
                JSONObject item = jsonItemsArray.getJSONObject(i);

                int id = item.getInt(ContentServiceHelper.JSON_ID_KEY);
                String title = item.getString(ContentServiceHelper.JSON_TITLE_KEY);
                String subtitle = item.getString(ContentServiceHelper.JSON_SUBTITLE_KEY);
                String date = item.getString(ContentServiceHelper.JSON_DATE_KEY);

                basicItem.add(new BasicItem(id, title, subtitle, date));
            }

            return basicItem;
        } catch (JSONException e) {
            cancel(true);
            Log.e(TAG, e.getMessage());
        }

        return basicItem;
    }

    @Override
    protected void onPostExecute(List<BasicItem> result) {
        if (!isCancelled()) {
            mContentListServiceCallback.onContentListRequestSuccess(result);
        } else {
            //in case it wasn't able to cancel the action before the onPostExecute got called
            mContentListServiceCallback.onContentListRequestFailure(mContentServiceHelper.getErrorMsg());
        }
    }

    @Override
    protected void onCancelled() {
        if (isCancelled()) {
            mContentListServiceCallback.onContentListRequestFailure(mContentServiceHelper.getErrorMsg());
        }
    }

    public interface ContentListServiceCallback {
        void onContentListRequestSuccess(List<BasicItem> basicItem);

        void onContentListRequestFailure(String errorMsg);
    }
}