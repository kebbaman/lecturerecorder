package it.uninsubria.lecturerecorder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FileViewerFragment extends Fragment {

    public FileViewerFragment()
    {
        // Required empty public constructor
    }

    public static FileViewerFragment getInstance() {
        FileViewerFragment fragment = new FileViewerFragment();
        return fragment;
    }

    public static FileViewerFragment newInstance() {
        FileViewerFragment fragment = new FileViewerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file_viewer, container, false);
    }
}
