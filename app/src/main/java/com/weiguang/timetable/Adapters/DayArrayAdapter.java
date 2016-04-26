package com.weiguang.timetable.Adapters;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.weiguang.timetable.R;

import java.util.ArrayList;

/**
 * Created by WeiGuang on 22/04/2016.
 */
public class DayArrayAdapter extends ArrayAdapter<String> {
    private static final String TAG = DayArrayAdapter.class.getName();

    private final Context context;
    private final ArrayList<String> dayArrayList;

    public DayArrayAdapter(Context context, ArrayList<String> dayArrayList) {
        super(context, R.layout.list_day_row, dayArrayList);
        this.context = context;
        this.dayArrayList = dayArrayList;
    }

    @Override
    public int getCount() {
        return this.dayArrayList.size();
    }

    @Override
    public String getItem(int index) {
        return this.dayArrayList.get(index);
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        final String day = getItem(index);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_day_row, parent, false);

        TextView dayTextView = (TextView) rowView.findViewById(R.id.day_text_view);
        dayTextView.setText(day);

        return rowView;
    }
}
