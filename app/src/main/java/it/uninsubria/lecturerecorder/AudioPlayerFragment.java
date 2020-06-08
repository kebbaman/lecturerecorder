package it.uninsubria.lecturerecorder;


import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class AudioPlayerFragment extends DialogFragment
{
    Recording recording;
    Handler handler = new Handler();
    MediaPlayer mediaPlayer;
    Boolean isPlaying = false;
    long minutes = 0;
    long seconds = 0;

    TextView fileName;
    TextView fileLength;
    FloatingActionButton playButton;
    TextView progressText;
    SeekBar seekBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        recording = (Recording) getArguments().getSerializable("recording");
        minutes = TimeUnit.MILLISECONDS.toMinutes(recording.getLength());
        seconds = TimeUnit.MILLISECONDS.toSeconds(recording.getLength() - TimeUnit.MINUTES.toSeconds(minutes));


    }

    private void setupSeekBar(){
        ColorFilter colorFilter = new LightingColorFilter(getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.colorPrimary));
        seekBar.getProgressDrawable().setColorFilter(colorFilter);
        seekBar.getThumb().setColorFilter(colorFilter);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser)
                {
                    mediaPlayer.seekTo(progress);
                    handler.removeCallbacks(mRunnable);

                    long minutes = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition());
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition())- TimeUnit.MINUTES.toSeconds(minutes);


                    progressText.setText(String.format("%02d:%02d",minutes,seconds));

                    updateSeekBar();
                }
                else if(mediaPlayer == null && fromUser)
                {
                    try {
                        prepareMediaPlayerFromPoint(progress);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    updateSeekBar();

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void prepareMediaPlayerFromPoint(int progress) throws IOException {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(recording.getPath());
        mediaPlayer.prepare();
        seekBar.setMax(mediaPlayer.getDuration());
        mediaPlayer.seekTo(progress);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaying();
            }
        });

    }

    private void updateSeekBar()
    {
        handler.postDelayed(mRunnable,1000);
    }

    private void stopPlaying(){
        playButton.setImageResource(R.drawable.ic_play);
        handler.removeCallbacks(mRunnable);
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;

        seekBar.setProgress(seekBar.getMax());

        isPlaying = false;
        progressText.setText(fileLength.getText());

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_audio_player,null);
        fileName = view.findViewById(R.id.file_name_text);
        fileLength = view.findViewById(R.id.length_text);
        progressText = view.findViewById(R.id.time_progress);
        seekBar = view.findViewById(R.id.seekbar);
        playButton = view.findViewById(R.id.play_button);

        builder.setView(view);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onPlay(isPlaying);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isPlaying = true;

            }
        });

        fileName.setText(recording.getName());
        fileLength.setText(String.format("%02d:%02d",minutes,seconds));
        return builder.create();
    }

    private void onPlay(Boolean isPlaying) throws IOException {
        if(!isPlaying && mediaPlayer == null)
        {
            startPlaying();
        }
        else
        {
            pausePlaying();
        }

    }

    private void pausePlaying()
    {
        playButton.setImageResource(R.drawable.ic_play);
        handler.removeCallbacks(mRunnable);
        mediaPlayer.pause();

    }

    private void startPlaying() throws IOException {
        playButton.setImageResource(R.drawable.ic_play);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(recording.getPath());
        mediaPlayer.prepare();
        seekBar.setMax(mediaPlayer.getDuration());

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaying();
            }
        });

        updateSeekBar();
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mediaPlayer!=null)
            {
                int currentPosition = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(currentPosition);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getCurrentPosition());
                long seconds = TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition())- TimeUnit.MINUTES.toSeconds(minutes);

                progressText.setText(String.format("%02d:%02d",minutes,seconds));
                updateSeekBar();
            }

        }
    };
}
