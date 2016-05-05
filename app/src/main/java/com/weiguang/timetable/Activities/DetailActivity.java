package com.weiguang.timetable.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.weiguang.timetable.R;

public class DetailActivity extends BaseActivity {
    private static final String TAG = DetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get the selected day that is passed from MainActivity
        Intent intent = getIntent();
        String selectedDay = intent.getStringExtra(SELECTED_DAY_TAG);
        setSelectedDay(selectedDay);

        //Update the detailFragment List
        updateDetailFragment();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //If user rotates to landscape close this activity
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        }
    }
}
