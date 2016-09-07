package com.kyriakosalexandrou.pulselive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kyriakosalexandrou.pulselive.App;
import com.kyriakosalexandrou.pulselive.R;
import com.kyriakosalexandrou.pulselive.models.ItemWithDetails;

public class ContentDetailFragment extends BaseFragment {
    public static final String TAG = ContentDetailFragment.class.getName();
    public static String EXTRA_CONTENT_DETAIL = "content_detail";

    private TextView mId;
    private TextView mTitle;
    private TextView mSubtitle;
    private TextView mDate;
    private TextView mBody;

    public static ContentDetailFragment instance(ItemWithDetails itemWithDetails) {
        ContentDetailFragment contentDetailFragment = new ContentDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CONTENT_DETAIL, itemWithDetails);
        contentDetailFragment.setArguments(bundle);

        return contentDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_detail, container, false);
        bindViews(view);

        ItemWithDetails itemWithDetails = (ItemWithDetails) getArguments().get(EXTRA_CONTENT_DETAIL);
        setUpContentDetail(itemWithDetails);
        return view;
    }

    private void bindViews(View view) {
        mId = (TextView) view.findViewById(R.id.id_value);
        mTitle = (TextView) view.findViewById(R.id.title_value);
        mSubtitle = (TextView) view.findViewById(R.id.subtitle_value);
        mBody = (TextView) view.findViewById(R.id.body_value);
        mDate = (TextView) view.findViewById(R.id.date_value);
    }

    private void setUpContentDetail(ItemWithDetails itemWithDetails) {
        mId.setText(App.getContext().getResources().getString(R.string.single_number, itemWithDetails.getBasicItem().getId()));
        mTitle.setText(itemWithDetails.getBasicItem().getTitle());
        mSubtitle.setText(itemWithDetails.getBasicItem().getSubtitle());
        mBody.setText(itemWithDetails.getBody());
        mDate.setText(itemWithDetails.getBasicItem().getDate());
    }
}