package com.kyriakosalexandrou.pulselive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;

import com.kyriakosalexandrou.pulselive.R;
import com.kyriakosalexandrou.pulselive.ui.snackbar.AppSnackbarEmpty;
import com.kyriakosalexandrou.pulselive.ui.snackbar.AppSnackbar;

public class BaseFragment extends Fragment {
    protected CoordinatorLayout mCoordinatorLayout;
    protected AppSnackbar mAppSnackbar = new AppSnackbarEmpty();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCoordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator_layout);
    }
}