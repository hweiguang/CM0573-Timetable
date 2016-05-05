package com.weiguang.timetable.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.weiguang.timetable.Activities.BaseActivity;
import com.weiguang.timetable.Models.TimetableItem;
import com.weiguang.timetable.R;

/**
 * Created by WeiGuang on 04/05/2016.
 */
public class DeleteTimetableDialog extends DialogFragment {
    private static final String TAG = DeleteTimetableDialog.class.getName();
    private static final String DELETE_ARGS_TAG = "delete";

    //Create a instance of the dialog with the timetable item to be deleted
    public static DeleteTimetableDialog newInstance(TimetableItem deleteTimetableItem) {
        DeleteTimetableDialog dialog = new DeleteTimetableDialog();
        Bundle args = new Bundle();
        args.putParcelable(DELETE_ARGS_TAG, deleteTimetableItem);
        dialog.setArguments(args);
        return dialog;
    }

    //Getter to get the timetable item to be deleted
    private TimetableItem getTimetableItemArguments() {
        return getArguments().getParcelable(DELETE_ARGS_TAG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Building the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.delete_title_text)
                .setPositiveButton(R.string.delete_button_text, new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BaseActivity baseActivity = (BaseActivity) getActivity();
                        baseActivity.onDeleteTimetableItem(getTimetableItemArguments());
                    }
                })
                .setNegativeButton(R.string.cancel_button_text, new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DeleteTimetableDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
