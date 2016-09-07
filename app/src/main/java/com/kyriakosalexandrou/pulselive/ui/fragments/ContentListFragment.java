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
import com.kyriakosalexandrou.pulselive.models.BasicItem;
import com.kyriakosalexandrou.pulselive.models.ItemWithDetails;
import com.kyriakosalexandrou.pulselive.services.ContentDetailsService;
import com.kyriakosalexandrou.pulselive.services.ContentListService;
import com.kyriakosalexandrou.pulselive.ui.adapters.ContentListAdapter;
import com.kyriakosalexandrou.pulselive.ui.snackbar.AppSnackbarImpl;
import com.kyriakosalexandrou.pulselive.widgets.AppSwipeRefreshLayout;

import java.util.List;

public class ContentListFragment extends BaseFragment implements ContentListAdapter.ContentListAdapterCallback, ContentListService.ContentListServiceCallback, ContentDetailsService.ContentDetailServiceCallback {
    public static final String TAG = ContentListFragment.class.getName();

    private RecyclerView mContentListRecycler;
    private AppSwipeRefreshLayout mAppSwipeRefreshLayout;

    private List<BasicItem> mBasicItems;
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
    public void onContentListRequestSuccess(List<BasicItem> basicItem) {
        mAppSwipeRefreshLayout.setRefreshing(false);
        mAppSnackbar.dismissSnackbar();
        updateContentList(basicItem);
    }

    @Override
    public void onContentListRequestFailure(String errorMsg) {
        mAppSwipeRefreshLayout.setRefreshing(false);

        mAppSnackbar = new AppSnackbarImpl(mCoordinatorLayout, errorMsg, getResources().getString(R.string.retry), Snackbar.LENGTH_INDEFINITE, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestContentListData();
            }
        });

        mAppSnackbar.showSnackbar();
    }

    @Override
    public void onContentDetailRequestFailure(final BasicItem basicItemRequested, String errorMsg) {
        mAppSwipeRefreshLayout.setRefreshing(false);

        mAppSnackbar = new AppSnackbarImpl(mCoordinatorLayout, errorMsg, getResources().getString(R.string.retry), Snackbar.LENGTH_INDEFINITE, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestContentDetailData(basicItemRequested);
            }
        });

        mAppSnackbar.showSnackbar();
    }

    private void updateContentList(List<BasicItem> basicItems) {
        mBasicItems = basicItems;
        mContentListAdapter.clear();
        mContentListAdapter.addAll(mBasicItems);
    }

    @Override
    public void onContentListItemClicked(BasicItem basicItem) {
        requestContentDetailData(basicItem);
    }

    private void requestContentDetailData(final BasicItem basicItem) {
        mAppSwipeRefreshLayout.setRefreshing(true);
        ContentDetailsService contentDetailsService = new ContentDetailsService(basicItem, this, getString(R.string.request_failure_content_detail, basicItem.getId()));
        contentDetailsService.requestData();
    }

    @Override
    public void onContentDetailRequestSuccess(final ItemWithDetails itemWithDetails) {
        mAppSwipeRefreshLayout.setRefreshing(false);

        mAppSnackbar.dismissSnackbar();
        goToContentDetailFragment(itemWithDetails);
    }

    private void goToContentDetailFragment(ItemWithDetails itemWithDetails) {
        FragmentManager fm = getFragmentManager();
        ContentDetailFragment fragment = ContentDetailFragment.instance(itemWithDetails);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment, fragment, ContentDetailFragment.TAG);
        ft.addToBackStack(ContentDetailFragment.TAG);
        ft.commit();
        fm.executePendingTransactions();
    }
}