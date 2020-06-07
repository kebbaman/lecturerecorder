package it.uninsubria.lecturerecorder;

import android.provider.BaseColumns;

public class DBContract
{
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "saved_recordings.db";

    private DBContract() {}


    static abstract class SavedRecording implements BaseColumns
    {
        static final String TABLE_NAME = "recordings";
        static final String COLUMN_NAME_NAME = "name";
        static final String COLUMN_NAME_PATH = "path";
        static final String COLUMN_NAME_LENGTH = "length";
        static final String COLUMN_NAME_TIME_ADDED = "time_added";
    }
}
