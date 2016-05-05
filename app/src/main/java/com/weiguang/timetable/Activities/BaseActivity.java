package com.weiguang.timetable.Activities;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.weiguang.timetable.Adapters.TimetableDBAdapter;
import com.weiguang.timetable.Fragments.DeleteTimetableDialog;
import com.weiguang.timetable.Fragments.DetailFragment;
import com.weiguang.timetable.Fragments.AddTimetableDialog;
import com.weiguang.timetable.Models.TimetableItem;
import com.weiguang.timetable.R;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getName();
    protected static final String SELECTED_DAY_TAG = "selectedDay";

    //Database adapter for retrieving timetableItems
    private TimetableDBAdapter timetableDBAdapter;

    //Variable to keep track of the day that is being selected
    private String selectedDay = "";

    //Getter and setter for selectedDay
    public String getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialising the database adapter
        timetableDBAdapter = new TimetableDBAdapter(this);
        timetableDBAdapter.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close the database adapter
        timetableDBAdapter.close();
    }

    //Function to get the detailFragment if available and update List
    public void updateDetailFragment() {
        DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id
                        .detail_fragment);

        //DetailFragment will be null in MainActivity portrait orientation do nothing
        if (detailFragment == null)
            return;

        //Get timetableItems with the selected day
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

    //Show dialog for user to fill in their timetable item
    public void showAddTimetableDialog() {
        AddTimetableDialog dialog = AddTimetableDialog.newInstance();
        dialog.show(getSupportFragmentManager(), AddTimetableDialog.class.getName());
    }

    //Show comfirmation dialog to delete timetable item
    public void showDeleteTimetableDialog(TimetableItem timetableItem) {
        DeleteTimetableDialog dialog = DeleteTimetableDialog.newInstance(timetableItem);
        dialog.show(getSupportFragmentManager(), DeleteTimetableDialog.class.getName());
    }

    //Add timetable item to database
    public void onAddTimetableItem(TimetableItem timetableItem) {
        timetableDBAdapter.insertTimetable(timetableItem);
        Toast.makeText(this, R.string.add_successful, Toast.LENGTH_LONG).show();
        updateDetailFragment();
    }

    //Remove timetable item from database
    public void onDeleteTimetableItem(TimetableItem timetableItem) {
        timetableDBAdapter.removeTimetable(timetableItem);
        Toast.makeText(this, R.string.delete_successful, Toast.LENGTH_LONG).show();
        updateDetailFragment();
    }
}
