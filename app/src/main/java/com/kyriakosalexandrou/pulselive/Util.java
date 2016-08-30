package com.kyriakosalexandrou.pulselive;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

/**
 * Useful generic re-usable methods
 */
public class Util {
    public static void showSnackbar(
            @NonNull View view, @NonNull CharSequence description,
            @NonNull CharSequence actionBtnText, @Snackbar.Duration int duration,
            View.OnClickListener onClickListener) {

        Snackbar snackbar = Snackbar
                .make(view, description, duration)
                .setAction(actionBtnText, onClickListener);

        configureSnackbarStyle(snackbar);
        snackbar.show();
    }

    private static void configureSnackbarStyle(Snackbar snackbar) {
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.DKGRAY);
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
    }
}