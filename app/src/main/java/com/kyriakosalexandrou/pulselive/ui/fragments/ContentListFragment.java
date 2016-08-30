package com.kyriakosalexandrou.pulselive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kyriakosalexandrou.pulselive.R;
import com.kyriakosalexandrou.pulselive.Util;
import com.kyriakosalexandrou.pulselive.models.ContentDetail;
import com.kyriakosalexandrou.pulselive.models.ContentList;
import com.kyriakosalexandrou.pulselive.models.ContentListItem;
import com.kyriakosalexandrou.pulselive.services.ContentDetailService;
import com.kyriakosalexandrou.pulselive.services.ContentListService;
import com.kyriakosalexandrou.pulselive.ui.adapters.ContentListAdapter;
import com.kyriakosalexandrou.pulselive.widgets.AppSwipeRefreshLayout;

public class ContentListFragment extends BaseFragment implements ContentListAdapter.ContentListAdapterCallback, ContentListService.ContentListServiceCallback, ContentDetailService.ContentDetailServiceCallback {
    public static final String TAG = ContentListFragment.class.getName();

    private RecyclerView mContentListRecycler;
    private AppSwipeRefreshLayout mAppSwipeRefreshLayout;

    private ContentList mContentList;
    private ContentListAdapter mContentListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_list, container, false);
        bindViews(view);

        mContentListAdapter = new ContentListAdapter(this);
        setUpContentListRecycler();
        setUpSwipeRefreshLayout();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestContentListData();
    }

    private void bindViews(View view) {
        mContentListRecycler = (RecyclerView) view.findViewById(R.id.content_list);
        mAppSwipeRefreshLayout = (AppSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
    }

    private void requestContentListData() {
        mAppSwipeRefreshLayout.setRefreshing(true);
        ContentListService contentListService = new ContentListService(this, getString(R.string.request_failure_content_list));
        contentListService.requestData();
    }

    private void setUpContentListRecycler() {
        mContentListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mContentListRecycler.setHasFixedSize(false);
        mContentListRecycler.setAdapter(mContentListAdapter);
    }

    private void setUpSwipeRefreshLayout() {
        mAppSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestContentListData();
            }
        });
    }

    @Override
    public void onContentListSuccess(ContentList contentList) {
        mAppSwipeRefreshLayout.setRefreshing(false);
        updateContentList(contentList);
    }

    @Override
    public void onServiceRequestFailure(String errorMsg) {
        mAppSwipeRefreshLayout.setRefreshing(false);

        Util.showSnackbar(mCoordinatorLayout, errorMsg, getResources().getString(R.string.retry), Snackbar.LENGTH_INDEFINITE, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestContentListData();
            }
        });
    }

    private void updateContentList(ContentList contentList) {
        mContentList = contentList;
        mContentListAdapter.clear();
        mContentListAdapter.addAll(mContentList.getItems());
    }

    @Override
    public void onContentListItemClicked(int position, ContentListItem item) {
        requestContentDetailData(item.getId());
    }

    private void requestContentDetailData(int itemId) {
        mAppSwipeRefreshLayout.setRefreshing(true);
        ContentDetailService contentDetailService = new ContentDetailService(itemId, this, getString(R.string.request_failure_content_detail));
        contentDetailService.requestData();
    }

    @Override
    public void onContentDetailSuccess(ContentDetail contentDetail) {
        mAppSwipeRefreshLayout.setRefreshing(false);
        goToContentDetailFragment(contentDetail);
    }

    private void goToContentDetailFragment(ContentDetail contentDetail) {
        FragmentManager fm = getFragmentManager();
        ContentDetailFragment fragment = ContentDetailFragment.instance(contentDetail);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment, fragment, ContentDetailFragment.TAG);
        ft.addToBackStack(ContentDetailFragment.TAG);
        ft.commit();
        fm.executePendingTransactions();
    }
}