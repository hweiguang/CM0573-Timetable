package com.weiguang.timetable.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.weiguang.timetable.Fragments.TimetableDialog;
import com.weiguang.timetable.Fragments.DetailFragment;
import com.weiguang.timetable.Fragments.ListFragment;
import com.weiguang.timetable.R;
import com.weiguang.timetable.Models.TimetableItem;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateDetailFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("selectedDay", getSelectedDay());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String selectedDay = savedInstanceState.getString("selectedDay");
        setSelectedDay(selectedDay);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            updateDetailFragment();
        }
    }

    //Menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_timetable:
                showTimetableDialog(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Navigation
    public void showDetailActivity() {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("selectedDay", getSelectedDay());
        startActivity(intent);
    }
}
