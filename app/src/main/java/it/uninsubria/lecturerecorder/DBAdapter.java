package it.uninsubria.lecturerecorder;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DBAdapter
{
    private static String TAG = "DBAdapter";
    private static DBAdapter sInstance;

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public static synchronized DBAdapter getInstance(Context context)
    {

        if (sInstance == null)
            sInstance = new DBAdapter(context.getApplicationContext());
        return sInstance;
    }
    private DBAdapter(Context context) {
        this.dbHelper = DBHelper.getInstance(context);
    }

    public DBAdapter open() throws SQLException
    {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
            throw e;
        }
        return this;
    }
    public void close() {
        db.close();
    }

    public Boolean addRecording(Recording recording)
    {

        Log.v(TAG, "Adding recording to the database.");
        try
        {
            ContentValues cv = recording.getAsContentValue();
            db.insert(DBContract.SavedRecording.TABLE_NAME,null,cv);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;

        }

    }
}
