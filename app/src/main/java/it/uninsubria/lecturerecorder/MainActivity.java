package it.uninsubria.lecturerecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

        //Aggiunta della costum_toolbar alla main activity
        Toolbar toolbar =  (Toolbar) findViewById(R.id.costum_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }
}
