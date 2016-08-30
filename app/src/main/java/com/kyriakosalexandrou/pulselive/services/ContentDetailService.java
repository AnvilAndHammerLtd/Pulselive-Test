package com.kyriakosalexandrou.pulselive.services;

import com.kyriakosalexandrou.pulselive.models.ContentDetail;

public class ContentDetailService extends ServiceBase {
    private static final String TAG = ContentDetailService.class.getName();
    private final int mItemId;

    public ContentDetailService(int itemId, ContentDetailServiceCallback contentDetailServiceCallback, String errorMsg) {
        super(contentDetailServiceCallback, errorMsg);
        mItemId = itemId;
    }

    public void requestData() {
        String url = ServiceBase.BASE_URL + "//dynamic.pulselive.com/test/native/content/" + mItemId + ".json";
        execute(url);
    }

    @Override
    protected void onPostExecute(String result) {
        ContentDetail contentDetail = new ContentDetail(result);
        ((ContentDetailServiceCallback) mServiceBaseCallback).onContentDetailSuccess(contentDetail);
    }

    public interface ContentDetailServiceCallback extends ServiceBaseCallback {
        void onContentDetailSuccess(ContentDetail contentDetail);
    }
}