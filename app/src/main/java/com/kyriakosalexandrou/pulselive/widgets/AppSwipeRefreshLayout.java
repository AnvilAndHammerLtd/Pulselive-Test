package com.kyriakosalexandrou.pulselive.widgets;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

import com.kyriakosalexandrou.pulselive.R;

public class AppSwipeRefreshLayout extends SwipeRefreshLayout {

    public AppSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public AppSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setColorSchemeResources(
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light,
                R.color.bondi_blue,
                R.color.mine_shaft
        );
    }
}
