package com.weiguang.timetable.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.weiguang.timetable.Adapters.TimetableDBAdapter;
import com.weiguang.timetable.Fragments.DetailFragment;
import com.weiguang.timetable.Fragments.TimetableDialog;
import com.weiguang.timetable.Models.TimetableItem;
import com.weiguang.timetable.R;

import java.sql.Time;
import java.util.ArrayList;

public class DetailActivity extends BaseActivity {
    private static final String TAG = DetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String selectedDay = intent.getStringExtra("selectedDay");

        setSelectedDay(selectedDay);
        updateDetailFragment();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        }
    }
}
