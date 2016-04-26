package com.weiguang.timetable.Fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.weiguang.timetable.Activities.BaseActivity;
import com.weiguang.timetable.Activities.DetailActivity;
import com.weiguang.timetable.Activities.MainActivity;
import com.weiguang.timetable.Adapters.TimetableItemAdapter;
import com.weiguang.timetable.Models.TimetableItem;
import com.weiguang.timetable.R;

import java.sql.Time;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private static final String TAG = DetailFragment.class.getName();

    private ArrayList<TimetableItem> timetableItemArrayList = new ArrayList<>();
    private ArrayAdapter<TimetableItem> timetableItemArrayAdapter;
    private ListView listView;
    private TextView noLessonTextView;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Inflate the layout for this fragment
        listView = (ListView) view.findViewById(R.id.detail_list_view);
        timetableItemArrayAdapter = new TimetableItemAdapter(getActivity(), timetableItemArrayList);
        listView.setAdapter(timetableItemArrayAdapter);
        noLessonTextView = (TextView) view.findViewById(R.id.no_lessons_text_view);

        registerForContextMenu(listView);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.detail_activity, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        TimetableItem selectedItem = timetableItemArrayList.get((int) info.id);

        switch (item.getItemId()) {
            case R.id.edit_item:
                onEditItem(selectedItem);
                return true;
            case R.id.delete_item:
                onDeleteItem(selectedItem);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void updateData(ArrayList<TimetableItem> timetableItemArrayList, String
            selectedDay) {
        this.timetableItemArrayList.clear();

        for (TimetableItem timetableItem : timetableItemArrayList) {
            this.timetableItemArrayList.add(timetableItem);
        }
        timetableItemArrayAdapter.notifyDataSetChanged();

        if (selectedDay == null || selectedDay == "") {
            noLessonTextView.setText("Please select a day on the left");
        } else {
            if (timetableItemArrayList.size() == 0) {
                noLessonTextView.setText("You have no lessons on " + selectedDay.toLowerCase());
                noLessonTextView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
            } else {
                listView.setVisibility(View.VISIBLE);
                noLessonTextView.setVisibility(View.INVISIBLE);
            }
            updateTitle(selectedDay);
        }
    }

    private void updateTitle(String newtitle) {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            getActivity().setTitle(newtitle);
        } else {
            getActivity().setTitle(getResources().getString(R.string.app_name));
        }
    }

    private void onEditItem(TimetableItem timetableItem) {
        BaseActivity baseActivity = (BaseActivity) getActivity();
        baseActivity.showTimetableDialog(timetableItem);
    }

    private void onDeleteItem(TimetableItem timetableItem) {
        BaseActivity baseActivity = (BaseActivity) getActivity();
        baseActivity.onDeleteTimetableItem(timetableItem);
    }
}
