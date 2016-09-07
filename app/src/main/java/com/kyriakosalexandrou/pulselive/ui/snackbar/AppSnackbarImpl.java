package com.kyriakosalexandrou.pulselive.ui.snackbar;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

public class AppSnackbarImpl implements AppSnackbar {
    private Snackbar mSnackbar;

    public AppSnackbarImpl(
            @NonNull View view, @NonNull CharSequence description,
            @NonNull CharSequence actionBtnText, @Snackbar.Duration int duration,
            View.OnClickListener onClickListener) {

        mSnackbar = Snackbar
                .make(view, description, duration)
                .setAction(actionBtnText, onClickListener);

        configureSnackbarStyle(mSnackbar);
    }

    private void configureSnackbarStyle(Snackbar snackbar) {
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
    }

    @Override
    public void showSnackbar() {
        mSnackbar.dismiss();
        mSnackbar.show();
    }

    @Override
    public void dismissSnackbar() {
        mSnackbar.dismiss();
    }
}