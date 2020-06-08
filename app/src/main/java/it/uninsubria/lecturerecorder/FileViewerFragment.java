package it.uninsubria.lecturerecorder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class FileViewerFragment extends Fragment {

    RecyclerView recyclerView;
    DBAdapter dbAdapter;
    ArrayList<Recording> recordings;
    FileViewerAdapter fileViewerAdapter;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        dbAdapter = DBAdapter.getInstance(getContext());
        dbAdapter.open();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        recordings = dbAdapter.getAllRecordings();

        if(recordings.size() == 0)
        {
            Toast.makeText(getActivity(),"No recordings",Toast.LENGTH_LONG).show();
            return;
        }

        fileViewerAdapter = new FileViewerAdapter(getActivity(),recordings,linearLayoutManager);
        recyclerView.setAdapter(fileViewerAdapter);



    }

    /*
    @Override
    public void onResume()
    {
        super.onResume();
        recordings = dbAdapter.getAllRecordings();
        fileViewerAdapter.updateRecordingList(recordings);
        Toast.makeText(getActivity(),"RESUME",Toast.LENGTH_LONG).show();
    }*/

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            dbAdapter.open();
            recordings = dbAdapter.getAllRecordings();
            fileViewerAdapter.updateRecordingList(recordings);
        }
    }
}
