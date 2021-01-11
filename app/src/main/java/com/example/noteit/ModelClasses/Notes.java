package com.example.noteit.ModelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Notes implements Parcelable {
    private String title;
    private String note;
    private String time;
    private String id;

    public Notes(String title, String note, String time, String id) {
        this.title = title;
        this.note = note;
        this.time = time;
        this.id = id;
    }

    public Notes() {
    }

    protected Notes(Parcel in) {
        title = in.readString();
        note = in.readString();
        time = in.readString();
        id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(note);
        dest.writeString(time);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
