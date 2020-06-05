package it.uninsubria.lecturerecorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;


public class RecordFragment extends Fragment
{
    Chronometer chronometer;
    TextView recordingStatus;
    FloatingActionButton recordButton;
    Button pauseButton;

    Boolean recordingStartedFlag = true;
    Boolean recordingPausedFlag = true;
    long timeWhenPaused;

    public RecordFragment()
    {
        // Required empty public constructor
    }

    public static RecordFragment newInstance()
    {
        RecordFragment fragment = new RecordFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_record, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        chronometer = getView().findViewById(R.id.chronometer);
        recordingStatus = getView().findViewById(R.id.recordingStatus);
        recordButton = getView().findViewById(R.id.recordButton);
        pauseButton = getView().findViewById(R.id.pauseButton);

        pauseButton.setVisibility(View.GONE);
        recordButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onRecord(recordingStartedFlag);
            }
        });





    }


    private void onRecord(Boolean flag)
    {
        //Intent recordingIntent = new Intent(getActivity(), RecordingService.class);
        if(flag)
        {
            recordButton.setImageResource(R.drawable.ic_white_stop);
            Toast.makeText(getActivity(),"Recording",Toast.LENGTH_LONG).show();
            File path = new File(Environment.getExternalStorageDirectory() + "/Recordings");
            if(!path.exists())
                path.mkdir(); //comando unix per la creazione di una nuova cartella/file
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();

            //getActivity().startService(recordingIntent);

            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //In questo modo lo schermo rimane acceso durante la registrazione.
            //recordingStatus.setText("Recording");
            recordingStartedFlag = false;
        }
        else
        {
            recordButton.setImageResource(R.drawable.ic_white_rec);
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            timeWhenPaused = 0;
            recordingStatus.setText("Tap the button to start recording");
            //getActivity().stopService(recordingIntent);
            recordingStartedFlag = true;
        }
    }
}
