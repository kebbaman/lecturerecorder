package it.uninsubria.lecturerecorder;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

class FileViewerAdapter extends RecyclerView.Adapter<FileViewerAdapter.FileViewerViewHolder> {

    Context context;
    ArrayList<Recording> recordings;
    LinearLayoutManager linearLayoutManager;

    public FileViewerAdapter(Context context, ArrayList<Recording> recordings, LinearLayoutManager linearLayoutManager)
    {
        this.context = context;
        this.recordings = recordings;
        this.linearLayoutManager = linearLayoutManager;
    }


    public class FileViewerViewHolder extends RecyclerView.ViewHolder
    {
        TextView fileName;
        TextView fileLength;
        TextView timeAdded;
        CardView cardView;
        public FileViewerViewHolder(@NonNull View itemView)
        {
            super(itemView);
            fileName = itemView.findViewById(R.id.file_name);
            fileLength = itemView.findViewById(R.id.file_length);
            timeAdded = itemView.findViewById(R.id.file_time_added);
            cardView = itemView.findViewById(R.id.cardView);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AudioPlayerFragment audioPlayerFragment = new AudioPlayerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("recording",recordings.get(getAdapterPosition()));
                    audioPlayerFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    audioPlayerFragment.show(fragmentTransaction,"audio_player_dialog");
                }
            });


        }
    }


    @NonNull
    @Override
    public FileViewerAdapter.FileViewerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recording_item_layout,parent,false);
        return new FileViewerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewerAdapter.FileViewerViewHolder holder, int position)
    {
        Recording recording = recordings.get(position);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(recording.getLength());
        long seconds = TimeUnit.MILLISECONDS.toSeconds(recording.getLength()) - TimeUnit.MINUTES.toSeconds(minutes);

        holder.fileName.setText(recording.getName());
        holder.fileLength.setText(String.format("%02d:%02d",minutes,seconds));
        holder.timeAdded.setText(DateUtils.formatDateTime(context,recording.getTime_added(),DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE| DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_YEAR));
    }

    @Override
    public int getItemCount() {
        return recordings.size();
    }

    public void updateRecordingList (ArrayList<Recording> recordings)
    {
        if (recordings!= null && recordings.size() > 0)
        {
            this.recordings.clear();
            this.recordings.addAll(recordings);
            notifyDataSetChanged();
        }
    }


}
