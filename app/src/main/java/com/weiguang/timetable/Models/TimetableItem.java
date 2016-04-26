package com.weiguang.timetable.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WeiGuang on 22/04/2016.
 */
public class TimetableItem implements Parcelable {
    private String identity;
    private String moduleCode;
    private String day;
    private String startTime;
    private String duration;
    private String type;
    private String room;

    //Parcelable methods
    private TimetableItem(Parcel in) {
        this.identity = in.readString();
        this.moduleCode = in.readString();
        this.day = in.readString();
        this.startTime = in.readString();
        this.duration = in.readString();
        this.type = in.readString();
        this.room = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(identity);
        out.writeString(moduleCode);
        out.writeString(day);
        out.writeString(startTime);
        out.writeString(duration);
        out.writeString(type);
        out.writeString(room);
    }

    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<TimetableItem> CREATOR = new Parcelable
            .Creator<TimetableItem>() {
        public TimetableItem createFromParcel(Parcel in) {
            return new TimetableItem(in);
        }

        public TimetableItem[] newArray(int size) {
            return new TimetableItem[size];
        }
    };

    public TimetableItem() {
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
