package com.kyriakosalexandrou.pulselive.services;

import com.kyriakosalexandrou.pulselive.models.ContentList;

public class ContentListService extends ServiceBase {
    private static final String TAG = ContentListService.class.getName();

    public ContentListService(ContentListServiceCallback contentListServiceCallback, String errorMsg) {
        super(contentListServiceCallback, errorMsg);
    }

    public void requestData() {
        String url = ServiceBase.BASE_URL + "//dynamic.pulselive.com/test/native/contentList.json";
        execute(url);
    }

    @Override
    protected void onPostExecute(String result) {
        ContentList contentList = new ContentList(result);
        ((ContentListServiceCallback) mServiceBaseCallback).onContentListSuccess(contentList);
    }

    public interface ContentListServiceCallback extends ServiceBaseCallback {
        void onContentListSuccess(ContentList contentList);
    }
}