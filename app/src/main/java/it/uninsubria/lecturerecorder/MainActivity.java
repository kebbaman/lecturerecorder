package it.uninsubria.lecturerecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    //added cardview,recyclerview,supportlibraries dependency
    //added dependency from git of floating button a scomparsa
    //not added butterknife dependency so eyes open! ;)

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
