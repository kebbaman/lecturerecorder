package it.uninsubria.lecturerecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
{
    //added cardview,recyclerview,supportlibraries dependency
    //added dependency from git of floating button a scomparsa
    //not added butterknife dependency so eyes open! ;)

    //ricordati di togliere le vecchie dipendenze per i costum tablayout di github

    TabLayout tabLayout;
    ViewPager viewPager;
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

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        //Aggiunta del RecordFragment e FileViewerFragment al ViewPageAdapter
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(RecordFragment.newInstance(),"RECORD");
        viewPagerAdapter.addFragment(FileViewerFragment.newInstance(),"SAVED RECORDINGS");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);





    }
}
