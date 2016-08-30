package com.kyriakosalexandrou.pulselive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;

import com.kyriakosalexandrou.pulselive.R;

public class BaseFragment extends Fragment {
    protected CoordinatorLayout mCoordinatorLayout;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCoordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator_layout);
    }
}
