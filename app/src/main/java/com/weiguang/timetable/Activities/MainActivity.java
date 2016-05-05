package com.weiguang.timetable.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.weiguang.timetable.R;

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
        //Save the selected day to be use when returning to this activity
        outState.putString(SELECTED_DAY_TAG, getSelectedDay());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Restore the previously selected day
        String selectedDay = savedInstanceState.getString(SELECTED_DAY_TAG);
        setSelectedDay(selectedDay);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            updateDetailFragment();
        }
    }

    //Menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu for adding timetable item
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Show add timetable dialog when the menu option add timetable is selected
            case R.id.add_timetable:
                showAddTimetableDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Show detail activity if in portrait mode and pass the selected day to it
    public void showDetailActivity() {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(SELECTED_DAY_TAG, getSelectedDay());
        startActivity(intent);
    }
}
