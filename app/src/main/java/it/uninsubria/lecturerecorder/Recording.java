package it.uninsubria.lecturerecorder;

import android.content.ContentValues;

public class Recording
{
    private String name;
    private String path;

    private long length;
    private long time_added;

    public Recording(String name, String path, long length, long time_added) {
        this.name = name;
        this.path = path;
        this.length = length;
        this.time_added = time_added;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public void setTime_added(long time_added) {
        this.time_added = time_added;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public long getLength() {
        return length;
    }

    public long getTime_added() {
        return time_added;
    }

    public ContentValues getAsContentValue() {
        ContentValues cv = new ContentValues();
        cv.put(DBContract.SavedRecording.COLUMN_NAME_NAME, this.name);
        cv.put(DBContract.SavedRecording.COLUMN_NAME_PATH, this.path);
        cv.put(DBContract.SavedRecording.COLUMN_NAME_LENGTH, this.length);
        cv.put(DBContract.SavedRecording.COLUMN_NAME_TIME_ADDED, this.time_added);
        return cv;
    }
}
