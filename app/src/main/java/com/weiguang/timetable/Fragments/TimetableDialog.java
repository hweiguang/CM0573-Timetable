package com.weiguang.timetable.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.weiguang.timetable.Activities.BaseActivity;
import com.weiguang.timetable.Activities.DetailActivity;
import com.weiguang.timetable.Activities.MainActivity;
import com.weiguang.timetable.Models.TimetableItem;
import com.weiguang.timetable.R;

import java.sql.Time;

/**
 * Created by WeiGuang on 22/04/2016.
 */
public class TimetableDialog extends DialogFragment {
    private static final String TAG = TimetableDialog.class.getName();

    public static TimetableDialog newInstance(TimetableItem timetableItem) {
        TimetableDialog dialog = new TimetableDialog();

        Bundle args = new Bundle();

        if (timetableItem != null)
            args.putParcelable("TimetableItem", timetableItem);
        dialog.setArguments(args);

        return dialog;
    }

    private TimetableItem getTimetableItemArguments() {
        return getArguments().getParcelable("TimetableItem");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = inflater.inflate(R.layout.add_timetable_dialog, null);

        final EditText moduleCodeEditText = (EditText) view.findViewById(R.id
                .module_code_edit_text);
        final EditText dayEditText = (EditText) view.findViewById(R.id.day_edit_text);
        final EditText startTimeEditText = (EditText) view.findViewById(R.id.start_time_edit_text);
        final EditText durationEditText = (EditText) view.findViewById(R.id.duration_edit_text);
        final EditText typeEditText = (EditText) view.findViewById(R.id.type_edit_text);
        final EditText roomEditText = (EditText) view.findViewById(R.id.room_edit_text);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TimetableDialog.this.getDialog().cancel();
                    }
                });

        String positiveButtonText = "";

        final TimetableItem timetableItem = getTimetableItemArguments();

        //TimetableItem is not null edit timetableItem
        if (timetableItem != null) {
            builder.setTitle("Edit item");
            positiveButtonText = "Edit";

            //Update edit text with timetableItem data
            moduleCodeEditText.setText(timetableItem.getModuleCode());
            dayEditText.setText(timetableItem.getDay());
            startTimeEditText.setText(timetableItem.getStartTime());
            durationEditText.setText(timetableItem.getDuration());
            typeEditText.setText(timetableItem.getType());
            roomEditText.setText(timetableItem.getRoom());
        }
        //Else add new timetableItem
        else {
            builder.setTitle("Add new item");
            positiveButtonText = "Add";
        }

        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Variable to store new or to be edited timetableItem
                TimetableItem newTimetableItem;
                boolean isEdit;

                //Create new timetableItem instance
                if (timetableItem == null) {
                    newTimetableItem = new TimetableItem();
                    isEdit = false;
                }
                //Assign the timetableItem to be edited
                else {
                    newTimetableItem = timetableItem;
                    isEdit = true;
                }
                newTimetableItem.setModuleCode(moduleCodeEditText.getText().toString());
                newTimetableItem.setDay(dayEditText.getText().toString());
                newTimetableItem.setStartTime(startTimeEditText.getText().toString());
                newTimetableItem.setDuration(durationEditText.getText().toString());
                newTimetableItem.setType(typeEditText.getText().toString());
                newTimetableItem.setRoom(roomEditText.getText().toString());

                BaseActivity baseActivity = (BaseActivity) getActivity();
                if (isEdit) {
                    baseActivity.onEditTimetableItem(newTimetableItem);
                } else {
                    baseActivity.onAddTimetableItem(newTimetableItem);
                }
            }
        });
        return builder.create();
    }
}
