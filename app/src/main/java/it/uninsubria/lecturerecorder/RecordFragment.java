package it.uninsubria.lecturerecorder;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
    private static final int RECORD_AUDIO_PERMISSION_CODE = 100;
    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 200;
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 300;

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
                if (!checkRecordingPermission())
                    requestRecordingPermission();

                if(!checkWriteToStoragePermission())
                    requestStorageWritePermission();

                if(!checkReadFromStoragePermission())
                    requestStorageReadPermission();

                onRecord(recordingStartedFlag);
            }
        });

    }


    private void onRecord(Boolean flag)
    {
        Intent recordingIntent = new Intent(getActivity(), RecordingService.class);
        if(flag)
        {
            recordButton.setImageResource(R.drawable.ic_white_stop);
            Toast.makeText(getActivity(),"Recording",Toast.LENGTH_LONG).show();
            File path = new File(Environment.getExternalStorageDirectory() + "/Recordings");
            if(!path.exists())
                path.mkdir(); //comando unix per la creazione di una nuova cartella/file
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();

            getActivity().startService(recordingIntent);

            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //In questo modo lo schermo rimane acceso durante la registrazione.
            recordingStatus.setText("Recording");
            recordingStartedFlag = false;
        }
        else
        {
            recordButton.setImageResource(R.drawable.ic_white_rec);
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            timeWhenPaused = 0;
            recordingStatus.setText("Tap the button to start recording");
            getActivity().stopService(recordingIntent);
            recordingStartedFlag = true;
        }
    }

    private Boolean checkRecordingPermission()
    {
        return ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)) == (PackageManager.PERMISSION_GRANTED));
    }

    private Boolean checkWriteToStoragePermission()
    {
        return ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) == (PackageManager.PERMISSION_GRANTED));
    }

    private Boolean checkReadFromStoragePermission()
    {
        return ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) == (PackageManager.PERMISSION_GRANTED));
    }


    private void requestRecordingPermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.RECORD_AUDIO))
        {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Audio recording permission needed")
                    .setMessage("You have to grant the audio recording permission in order to record a lecture.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[] {Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else
        {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_PERMISSION_CODE);
        }


    }

    private void requestStorageWritePermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Exernal storage writing permission needed")
                    .setMessage("You have to grant the write to storage permission in order to record a lecture.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else
        {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_PERMISSION_CODE);
        }

    }
    private void requestStorageReadPermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Audio recording permission needed")
                    .setMessage("You have to grant the audio recording permission in order to record a lecture.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else
        {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case RECORD_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "REC Permission GRANTED", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "REC Permission DENIED", Toast.LENGTH_SHORT).show();
                }
                break;
            case WRITE_EXTERNAL_STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "WRITE EX Permission GRANTED", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "WRITE EX Permission DENIED", Toast.LENGTH_SHORT).show();
                }
                break;
            case READ_EXTERNAL_STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "READ EX Permission GRANTED", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "READ EX Permission DENIED", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


}
