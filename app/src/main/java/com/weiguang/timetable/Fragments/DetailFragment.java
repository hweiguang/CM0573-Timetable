package com.weiguang.timetable.Fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.weiguang.timetable.Adapters.TimetableItemAdapter;
import com.weiguang.timetable.Models.TimetableItem;
import com.weiguang.timetable.R;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        //Setting up the list view
        listView = (ListView) view.findViewById(R.id.detail_list_view);
        timetableItemArrayAdapter = new TimetableItemAdapter(getActivity(), timetableItemArrayList);
        listView.setAdapter(timetableItemArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Get the selected item and show confirmation to delete item
                TimetableItem timetableItem = timetableItemArrayList.get(i);
                BaseActivity baseActivity = (BaseActivity) getActivity();
                baseActivity.showDeleteTimetableDialog(timetableItem);
            }
        });
        noLessonTextView = (TextView) view.findViewById(R.id.no_lessons_text_view);
        return view;
    }

    public void updateData(ArrayList<TimetableItem> timetableItemArrayList, String
            selectedDay) {
        //Clear existing items in the ArrayList
        this.timetableItemArrayList.clear();

        //Add the items from the ArrayList that is passed in
        //This has to be done because notifyDataSetChanged() will only works if there is items
        // being added and removed
        //Setting the ArrayList directly with the ArrayList that is passed in does not work
        for (TimetableItem timetableItem : timetableItemArrayList) {
            this.timetableItemArrayList.add(timetableItem);
        }
        timetableItemArrayAdapter.notifyDataSetChanged();

        //Display message to user to select a day if the user has not selected one yet in
        // landscape mode
        if (selectedDay == null || selectedDay.equals("")) {
            noLessonTextView.setText(R.string.please_select_day);
        } else {
            //Show a text view that says there is no lesson if there is no item to show
            if (timetableItemArrayList.size() == 0) {
                noLessonTextView.setText(String.format("%s %s", getString(R.string
                                .you_have_no_lesson),
                        selectedDay.toLowerCase()));
                noLessonTextView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
            } else {
                //Else show the items
                listView.setVisibility(View.VISIBLE);
                noLessonTextView.setVisibility(View.INVISIBLE);
            }
            //Set the title to the day that is selected
            getActivity().setTitle(selectedDay);
        }
    }
}
