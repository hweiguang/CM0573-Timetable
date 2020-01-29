package com.weiguang.timetable.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.weiguang.timetable.Activities.BaseActivity;
import com.weiguang.timetable.Models.TimetableItem;
import com.weiguang.timetable.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WeiGuang on 22/04/2016.
 */
public class AddTimetableDialog extends DialogFragment {
    private static final String TAG = AddTimetableDialog.class.getName();
    private EditText moduleCodeEditText;
    private EditText dayEditText;
    private EditText startTimeEditText;
    private EditText durationEditText;
    private EditText typeEditText;
    private EditText roomEditText;

    //Create a new instance of the dialog
    public static AddTimetableDialog newInstance() {
        AddTimetableDialog dialog = new AddTimetableDialog();
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Initialising the edit text views
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_timetable_dialog, null);
        moduleCodeEditText = (EditText) view.findViewById(R.id.module_code_edit_text);
        dayEditText = (EditText) view.findViewById(R.id.day_edit_text);
        startTimeEditText = (EditText) view.findViewById(R.id.start_time_edit_text);
        durationEditText = (EditText) view.findViewById(R.id.duration_edit_text);
        typeEditText = (EditText) view.findViewById(R.id.type_edit_text);
        roomEditText = (EditText) view.findViewById(R.id.room_edit_text);

        //Building the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.add_title_text)
                .setView(view)
                //Passing null to prevent auto closure of the dialog
                .setPositiveButton(R.string.add_button_text, null)
                .setNegativeButton(R.string.cancel_button_text, new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AddTimetableDialog.this.getDialog().cancel();
                            }
                        });

        //Create the dialog
        Dialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            //Get reference to the positive button and only dismiss the dialog after validation
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog
                        .BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (validateEditText()) {
                            //Create new TimetableItem to be added to database
                            TimetableItem newTimetableItem = new TimetableItem();
                            newTimetableItem.setModuleCode(moduleCodeEditText.getText().toString());
                            newTimetableItem.setDay(dayEditText.getText().toString());
                            newTimetableItem.setStartTime(startTimeEditText.getText().toString());
                            newTimetableItem.setDuration(durationEditText.getText().toString());
                            newTimetableItem.setType(typeEditText.getText().toString());
                            newTimetableItem.setRoom(roomEditText.getText().toString());

                            //Pass the TimetableTime to Activity and add to database
                            BaseActivity baseActivity = (BaseActivity) getActivity();
                            baseActivity.onAddTimetableItem(newTimetableItem);

                            //Dismiss dialog
                            AddTimetableDialog.this.getDialog().dismiss();
                        }
                    }
                });
            }
        });
        return dialog;
    }

    private boolean validateEditText() {
        //Ensures all edit text are filled up
        if (moduleCodeEditText.getText().toString().equals("") ||
                dayEditText.getText().toString().equals("") ||
                startTimeEditText.getText().toString().equals("") ||
                durationEditText.getText().toString().equals("") ||
                typeEditText.getText().toString().equals("") ||
                roomEditText.getText().toString().equals("")) {
            Toast.makeText(getActivity(), R.string.ensure_all_field_are_filled, Toast
                    .LENGTH_LONG).show();
            return false;
        }

        //Making sure the module code input has 2 characters and 3 digits
        Pattern pattern = Pattern.compile("[A-Za-z]{2}[0-9]{3}");
        Matcher matcher = pattern.matcher(moduleCodeEditText.getText().toString());
        if (!matcher.find() || moduleCodeEditText.getText().toString().length() > 5) {
            Toast.makeText(getActivity(), R.string.ensure_module_code_format, Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        //Ensure the day entered is valid
        if (!dayEditText.getText().toString().equals("Monday") &&
                !dayEditText.getText().toString().equals("Tuesday") &&
                !dayEditText.getText().toString().equals("Wednesday") &&
                !dayEditText.getText().toString().equals("Thursday") &&
                !dayEditText.getText().toString().equals("Friday")) {
            Toast.makeText(getActivity(), R.string.please_enter_valid_day, Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        //Ensure the time entered is between 9 and 17
        int startTime = Integer.valueOf(startTimeEditText.getText().toString());
        if (startTime < 9 || startTime > 17) {
            Toast.makeText(getActivity(), R.string.please_enter_valid_time, Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        //Ensure the type entered is either Lecture, Practical or Seminar
        if (!typeEditText.getText().toString().equals("Lecture") &&
                !typeEditText.getText().toString().equals("Practical") &&
                !typeEditText.getText().toString().equals("Seminar")) {
            Toast.makeText(getActivity(), R.string.please_enter_valid_type, Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        //Ensure only 10 characters are entered
        if (roomEditText.getText().toString().length() > 10) {
            Toast.makeText(getActivity(), R.string.please_enter_valid_room, Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        return true;
    }
}
