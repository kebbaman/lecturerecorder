package it.uninsubria.lecturerecorder;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;

public class RecordingService extends Service
{
    MediaRecorder mediaRecorder;
    long startingTimeMillis = 0;
    long elapsedMillis = 0;

    File file;
    String fileName;
    DBAdapter dbAdapter;

    @Override
    public void onCreate()
    {
        super.onCreate();
        dbAdapter = DBAdapter.getInstance(getApplicationContext());
        dbAdapter.open();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        fileName = intent.getStringExtra("Title");
        startRecording();
        return START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    private void startRecording()
    {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        fileName +=ts;  //momento espresso in millisecondi, è un modo per assicurarsi che il nome sia univoco.
        file = new File(Environment.getExternalStorageDirectory()+ "/Recordings/"+fileName+".mp3");

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(file.getAbsolutePath());
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setAudioChannels(1);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            startingTimeMillis = System.currentTimeMillis();

        } catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Fail\n"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void stopRecording()
    {
        mediaRecorder.stop();
        elapsedMillis = System.currentTimeMillis() - startingTimeMillis;
        mediaRecorder.release();
        Toast.makeText(getApplicationContext(),"Recording saved: "+file.getAbsolutePath(),Toast.LENGTH_LONG).show();

        //aggiunta a database sqlite
        Recording recording = new Recording(fileName,file.getAbsolutePath(),elapsedMillis,System.currentTimeMillis());

        dbAdapter.addRecording(recording);
    }

    @Override
    public void onDestroy()
    {
        if(mediaRecorder != null)
            stopRecording();
        dbAdapter.close();
        super.onDestroy();
    }
}
