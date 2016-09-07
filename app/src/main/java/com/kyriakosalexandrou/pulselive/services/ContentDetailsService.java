package com.kyriakosalexandrou.pulselive.services;

import android.os.AsyncTask;
import android.util.Log;

import com.kyriakosalexandrou.pulselive.models.BasicItem;
import com.kyriakosalexandrou.pulselive.models.ItemWithDetails;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class ContentDetailsService extends AsyncTask<String, String, ItemWithDetails> {
    private static final String TAG = ContentDetailsService.class.getName();
    private static String JSON_ITEM_KEY = "item";
    private static String JSON_BODY_KEY = "body";
    private BasicItem mBasicItemRequested;

    private ContentDetailServiceCallback mContentDetailServiceCallback;
    private ContentServiceHelper mContentServiceHelper;

    public ContentDetailsService(final BasicItem basicItem, final ContentDetailServiceCallback contentDetailServiceCallback, final String errorMsg) {
        mBasicItemRequested = basicItem;
        mContentDetailServiceCallback = contentDetailServiceCallback;
        mContentServiceHelper = new ContentServiceHelper(errorMsg);
    }

    public void requestData() {
        String url = ContentServiceHelper.BASE_URL + "//dynamic.pulselive.com/test/native/content/" + mBasicItemRequested.getId() + ".json";
        execute(url);
    }

    @Override
    protected ItemWithDetails doInBackground(String... args) {
        ItemWithDetails itemWithDetails = null;

        try {
            String result = mContentServiceHelper.fetchUrlData(args[0]);
            itemWithDetails = getItemDetails(result);

        } catch (Exception e) {
            cancel(true);
            e.printStackTrace();

        } finally {
            mContentServiceHelper.mUrlConnection.disconnect();
        }

        return itemWithDetails;
    }

    private ItemWithDetails getItemDetails(String jsonResult) {
        ItemWithDetails itemWithDetails = null;

        try {
            JSONObject jsonContainerObject = (JSONObject) new JSONTokener(jsonResult).nextValue();
            JSONObject jsonItemObject = jsonContainerObject.getJSONObject(JSON_ITEM_KEY);

            int id = jsonItemObject.getInt(ContentServiceHelper.JSON_ID_KEY);
            String title = jsonItemObject.getString(ContentServiceHelper.JSON_TITLE_KEY);
            String subtitle = jsonItemObject.getString(ContentServiceHelper.JSON_SUBTITLE_KEY);
            String body = jsonItemObject.getString(JSON_BODY_KEY);
            String date = jsonItemObject.getString(ContentServiceHelper.JSON_DATE_KEY);

            /*
            assume that the data might change when retrieving them, therefore we rebuild the ContentItemWithoutDetails from scratch.
            we could have reused the values of the ContentItemWithoutDetails that we already have from when we retrieved the ContentList data.
             */
            itemWithDetails = new ItemWithDetails(new BasicItem(id, title, subtitle, date), body);

        } catch (JSONException e) {
            cancel(true);
            Log.e(TAG, e.getMessage());
        }

        return itemWithDetails;
    }

    @Override
    protected void onPostExecute(ItemWithDetails result) {
        if (!isCancelled()) {
            mContentDetailServiceCallback.onContentDetailRequestSuccess(result);
        } else {
            //in case it wasn't able to cancel the action before the onPostExecute got called
            mContentDetailServiceCallback.onContentDetailRequestFailure(mBasicItemRequested, mContentServiceHelper.getErrorMsg());
        }
    }

    @Override
    protected void onCancelled() {
        if (isCancelled()) {
            mContentDetailServiceCallback.onContentDetailRequestFailure(mBasicItemRequested, mContentServiceHelper.getErrorMsg());
        }
    }

    public interface ContentDetailServiceCallback {
        void onContentDetailRequestSuccess(ItemWithDetails itemWithDetails);

        void onContentDetailRequestFailure(BasicItem basicItemRequested, String errorMsg);
    }
}