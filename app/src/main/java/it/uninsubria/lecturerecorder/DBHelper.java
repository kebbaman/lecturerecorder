package it.uninsubria.lecturerecorder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String LOG_TAG = "DBOpenHelper";
    private static DBHelper sInstance; //singleton instance

    private static final String SQL_CREATE_TABLE_SAVED_RECORDINGS = "create table "
        + DBContract.SavedRecording.TABLE_NAME + " ("
        + DBContract.SavedRecording._ID + " integer primary key autoincrement, "
        + DBContract.SavedRecording.COLUMN_NAME_NAME + " text not null, "
        + DBContract.SavedRecording.COLUMN_NAME_PATH + " text not null,"
        + DBContract.SavedRecording.COLUMN_NAME_LENGTH + " integer,"
        + DBContract.SavedRecording.COLUMN_NAME_TIME_ADDED + " integer"
        + ");";

    private Context context;



    public static synchronized DBHelper getInstance(Context context)
    {
    // Use the application context, which will ensure that you
    // don't accidentally leak an Activity's context.
        if (sInstance == null)
            sInstance = new DBHelper(context.getApplicationContext());
        return sInstance;
    }

    private DBHelper(Context context) {
        //super(context, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
        super(context,DBContract.DATABASE_NAME,null,DBContract.DATABASE_VERSION);
        this.context = context;
    }

    /*public DBHelper(Context context)
    {
        super(context,DBContract.DATABASE_NAME,null,DBContract.DATABASE_VERSION);
        this.context = context;

    }*/

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_TABLE_SAVED_RECORDINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists "+DBContract.DATABASE_NAME);
    }
}
