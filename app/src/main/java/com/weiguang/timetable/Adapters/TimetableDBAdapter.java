package com.weiguang.timetable.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import com.weiguang.timetable.Models.TimetableItem;

import java.util.ArrayList;

/**
 * Created by WeiGuang on 21/04/2016.
 */
public class TimetableDBAdapter {
    private static final String TAG = TimetableDBAdapter.class.getName();

    private static final String DATABASE_NAME = "timetableList.db";
    private static final String DATABASE_TABLE = "timetableItems";
    private static final int DATABASE_VERSION = 1;

    public static final String KEY_ID = "_id";
    public static final String KEY_MODULE_CODE = "module_code";
    public static final String KEY_DAY = "day";
    public static final String KEY_START_TIME = "start_time";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_TYPE = "type";
    public static final String KEY_ROOM = "room";

    private TimetableDBOpenHelper dbHelper;
    private SQLiteDatabase db;
    private final Context context;

    public TimetableDBAdapter(Context context) {
        this.context = context;
        this.dbHelper = new TimetableDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        dbHelper.close();
    }

    //Database queries
    public ArrayList<TimetableItem> getDayTimetableItems(String selectedDay) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " + KEY_DAY + " =" +
                " '" + selectedDay + "'" + " ORDER BY '" + KEY_START_TIME + " '", null);

        ArrayList<TimetableItem> timetableItemArrayList = new ArrayList<TimetableItem>();

        if (cursor.moveToFirst()) {
            do {
                String identity = cursor.getString(cursor
                        .getColumnIndex
                                (TimetableDBAdapter.KEY_ID));
                String moduleCode = cursor.getString(cursor
                        .getColumnIndex
                                (TimetableDBAdapter.KEY_MODULE_CODE));
                String day = cursor.getString(cursor
                        .getColumnIndex
                                (TimetableDBAdapter.KEY_DAY));
                String startTime = cursor.getString(cursor
                        .getColumnIndex
                                (TimetableDBAdapter.KEY_START_TIME));
                String duration = cursor.getString(cursor
                        .getColumnIndex
                                (TimetableDBAdapter.KEY_DURATION));
                String type = cursor.getString(cursor
                        .getColumnIndex
                                (TimetableDBAdapter.KEY_TYPE));
                String room = cursor.getString(cursor
                        .getColumnIndex
                                (TimetableDBAdapter.KEY_ROOM));

                TimetableItem timetableItem = new TimetableItem();
                timetableItem.setIdentity(identity);
                timetableItem.setModuleCode(moduleCode);
                timetableItem.setDay(day);
                timetableItem.setStartTime(startTime);
                timetableItem.setDuration(duration);
                timetableItem.setType(type);
                timetableItem.setRoom(room);

                timetableItemArrayList.add(timetableItem);
            } while (cursor.moveToNext());
        }
        return timetableItemArrayList;
    }

    //Insert a new timetable
    public boolean insertTimetable(TimetableItem timetableItem) {
        // Create a new row of values to insert.
        ContentValues newValue = new ContentValues();

        // Assign values for each row.
        newValue.put(KEY_MODULE_CODE, timetableItem.getModuleCode());
        newValue.put(KEY_DAY, timetableItem.getDay());
        newValue.put(KEY_START_TIME, timetableItem.getStartTime());
        newValue.put(KEY_DURATION, timetableItem.getDuration());
        newValue.put(KEY_TYPE, timetableItem.getType());
        newValue.put(KEY_ROOM, timetableItem.getRoom());

        return db.insert(DATABASE_TABLE, null, newValue) > 0;
    }

    // Remove a timetable based on its index
    public boolean removeTimetable(TimetableItem timetableItem) {
        return db.delete(DATABASE_TABLE, KEY_ID + "=" + timetableItem.getIdentity(), null) > 0;
    }

    // Update a timetable
    public boolean updateTimetable(TimetableItem timetableItem) {
        ContentValues newValue = new ContentValues();

        newValue.put(KEY_MODULE_CODE, timetableItem.getModuleCode());
        newValue.put(KEY_DAY, timetableItem.getDay());
        newValue.put(KEY_START_TIME, timetableItem.getStartTime());
        newValue.put(KEY_DURATION, timetableItem.getDuration());
        newValue.put(KEY_TYPE, timetableItem.getType());
        newValue.put(KEY_ROOM, timetableItem.getRoom());

        return db.update(DATABASE_TABLE, newValue, KEY_ID + "=" + timetableItem.getIdentity(),
                null) > 0;
    }


    private static class TimetableDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE =
                "CREATE TABLE " + DATABASE_TABLE + " (" +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_MODULE_CODE + " TEXT NOT NULL, " +
                        KEY_DAY + " TEXT NOT NULL, " +
                        KEY_START_TIME + " TEXT NOT NULL, " +
                        KEY_DURATION + " TEXT NOT NULL, " +
                        KEY_TYPE + " TEXT NOT NULL, " +
                        KEY_ROOM + " TEXT NOT NULL" +
                        ");";

        public TimetableDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory
                factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }
}
