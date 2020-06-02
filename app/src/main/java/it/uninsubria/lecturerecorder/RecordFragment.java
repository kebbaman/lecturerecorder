package it.uninsubria.lecturerecorder;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordFragment extends Fragment
{

    //doppione di newIstance
    public static RecordFragment getIstance()
    {
        RecordFragment fragment = new RecordFragment();
        return fragment;
    }
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
    public void onAttach(@NonNull Context context) {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false);

    }
}
