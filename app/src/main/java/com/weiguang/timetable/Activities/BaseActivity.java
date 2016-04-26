package com.weiguang.timetable.Activities;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.weiguang.timetable.Adapters.TimetableDBAdapter;
import com.weiguang.timetable.Fragments.DetailFragment;
import com.weiguang.timetable.Fragments.TimetableDialog;
import com.weiguang.timetable.Models.TimetableItem;
import com.weiguang.timetable.R;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getName();

    private TimetableDBAdapter timetableDBAdapter;
    private String selectedDay = "";

    public String getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        timetableDBAdapter = new TimetableDBAdapter(this);
        timetableDBAdapter.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timetableDBAdapter.close();
    }

    public void updateDetailFragment() {
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id
                        .detail_fragment);

        //DetailFragment will be null in MainActivity portrait orientation do nothing
        if (detailFragment == null)
            return;

        ArrayList<TimetableItem> timetableItemArrayList = timetableDBAdapter.getDayTimetableItems
                (selectedDay);

        int orientation = getResources().getConfiguration().orientation;

        //If orientation is landscape and DetailFragment is not null update accordingly
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            detailFragment.updateData(timetableItemArrayList, selectedDay);
        }
        //Else in portrait mode we need to ensure the current activity displayed is DetailActivity
        else {
            DetailActivity detailActivity = (DetailActivity) detailFragment.getActivity();
            if (detailActivity != null) {
                detailFragment.updateData(timetableItemArrayList, selectedDay);
            }
        }
    }

    public void showTimetableDialog(TimetableItem timetableItem) {
        TimetableDialog dialog = TimetableDialog.newInstance(timetableItem);
        dialog.show(getSupportFragmentManager(), TimetableDialog.class.getName());
    }

    //Database methods
    public void onAddTimetableItem(TimetableItem timetableItem) {
        timetableDBAdapter.insertTimetable(timetableItem);
        Toast.makeText(this, "Added successfully", Toast.LENGTH_LONG).show();
        updateDetailFragment();
    }

    public void onDeleteTimetableItem(TimetableItem timetableItem) {
        timetableDBAdapter.removeTimetable(timetableItem);
        Toast.makeText(this, "Item deleted successfully", Toast.LENGTH_LONG).show();
        updateDetailFragment();
    }

    public void onEditTimetableItem(TimetableItem timetableItem) {
        timetableDBAdapter.updateTimetable(timetableItem);
        Toast.makeText(this, "Item edited successfully", Toast.LENGTH_LONG).show();
        updateDetailFragment();
    }
}
