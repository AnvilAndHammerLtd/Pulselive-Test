package com.kyriakosalexandrou.pulselive.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.kyriakosalexandrou.pulselive.R;
import com.kyriakosalexandrou.pulselive.ui.fragments.ContentListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        goToContentListFragment();
    }

    private void goToContentListFragment() {
        FragmentManager fm = getSupportFragmentManager();
        ContentListFragment fragment = new ContentListFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment, fragment, ContentListFragment.TAG);
        ft.commit();
        fm.executePendingTransactions();
    }
}
