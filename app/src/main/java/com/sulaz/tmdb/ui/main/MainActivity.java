package com.sulaz.tmdb.ui.main;

import androidx.annotation.LayoutRes;

import android.os.Bundle;

import com.sulaz.tmdb.R;
import com.sulaz.tmdb.di.base.BaseActivity;
import com.sulaz.tmdb.ui.master.MasterFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @LayoutRes
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new MasterFragment()).commit();
        }
    }
}
