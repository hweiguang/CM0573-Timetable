package com.weiguang.timetable.Fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.weiguang.timetable.Activities.DetailActivity;
import com.weiguang.timetable.Activities.MainActivity;
import com.weiguang.timetable.Adapters.DayArrayAdapter;
import com.weiguang.timetable.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    private static final String TAG = ListFragment.class.getName();
    private MainActivity mainActivity;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView) view.findViewById(R.id.day_list_view);
        final ArrayList<String> days = new ArrayList<String>(Arrays.asList(getResources()
                .getStringArray(R.array.days)));
        DayArrayAdapter arrayAdapter = new DayArrayAdapter(mainActivity, days);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainActivity.setSelectedDay(days.get(position));

                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    mainActivity.updateDetailFragment();
                } else {
                    mainActivity.showDetailActivity();
                }
            }
        });
        return view;
    }
}
