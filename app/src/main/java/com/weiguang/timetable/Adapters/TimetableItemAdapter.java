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
import com.weiguang.timetable.Models.TimetableItem;

import java.util.ArrayList;

/**
 * Created by WeiGuang on 22/04/2016.
 */
public class TimetableItemAdapter extends ArrayAdapter<TimetableItem> {
    private static final String TAG = TimetableItemAdapter.class.getName();

    private final Context context;
    private final ArrayList<TimetableItem> timetableItemArrayList;

    public TimetableItemAdapter(Context context, ArrayList<TimetableItem> timetableItemArrayList) {
        super(context, R.layout.list_timetable_row, timetableItemArrayList);
        this.context = context;
        this.timetableItemArrayList = timetableItemArrayList;
    }

    @Override
    public int getCount() {
        return this.timetableItemArrayList.size();
    }

    @Override
    public TimetableItem getItem(int index) {
        return this.timetableItemArrayList.get(index);
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        //Setting up the view to display individual timetable item
        final TimetableItem timetableItem = getItem(index);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_timetable_row, parent, false);

        TextView moduleTextView = (TextView) rowView.findViewById(R.id.module_code_text_view);
        moduleTextView.setText(String.format("%s%s", moduleTextView.getText(), timetableItem
                .getModuleCode()));

        TextView startTimeTextView = (TextView) rowView.findViewById(R.id.start_time_text_view);
        startTimeTextView.setText(String.format("%s%s", startTimeTextView.getText(),
                timetableItem.getStartTime()));

        TextView durationTextView = (TextView) rowView.findViewById(R.id.duration_text_view);
        durationTextView.setText(String.format("%s%s hour", durationTextView.getText(),
                timetableItem.getDuration()));

        TextView typeTextView = (TextView) rowView.findViewById(R.id.type_text_view);
        typeTextView.setText(String.format("%s%s", typeTextView.getText(), timetableItem.getType
                ()));

        TextView roomTextView = (TextView) rowView.findViewById(R.id.room_text_view);
        roomTextView.setText(String.format("%s%s", roomTextView.getText(), timetableItem.getRoom
                ()));

        return rowView;
    }
}
