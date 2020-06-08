package it.uninsubria.lecturerecorder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

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

    public ArrayList<Recording> getAllRecordings()
    {
        ArrayList<Recording> recordings = new ArrayList<Recording>();

        Cursor cursor = db.rawQuery("select * from "+DBContract.SavedRecording.TABLE_NAME,null);

        while(cursor.moveToNext())
        {
            Recording recording = new Recording(cursor.getString(1),cursor.getString(2),cursor.getLong(3),cursor.getLong(4));
            recordings.add(recording);
        }

        cursor.close();
        return recordings;
    }
}
