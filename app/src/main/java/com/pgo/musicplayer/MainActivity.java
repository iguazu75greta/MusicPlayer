package com.pgo.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private  static final   String  TAG = "LOGPGO";

    /** Var globales **/

    MediaPlayer mediaPlayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"onCreate: ");

        mediaPlayer =   MediaPlayer.create(this,R.raw.sound);
//        mediaPlayer =   MediaPlayer.create(this,R.raw.sound_old);
//        mediaPlayer.start();

        position();
        volume();


    }

    public void play(View view){
        Log.i(TAG,"play: ");
        mediaPlayer.start();
    }


    public void pause(View view){
        Log.i(TAG,"pause: ");
        mediaPlayer.pause();
    }


    public void position (){
        Log.i(TAG,"position: ");
//        mediaPlayer.pause();

        SeekBar sbPosition  =   findViewById(R.id.sbPosition);

        sbPosition.setMax(mediaPlayer.getDuration());
        sbPosition.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG,"Position dans le morceau : " + Integer.toString(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i(TAG, "onStartTrackingTouch: ");
                pause(sbPosition);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i(TAG, "onStopTrackingTouch: ");
                play(sbPosition);
                mediaPlayer.seekTo(sbPosition.getProgress());

            }
        });

        // Partie 2 déplacement automatique
        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
//                Log.i(TAG, "run: ");
                sbPosition.setProgress(mediaPlayer.getCurrentPosition());

            }
        }, 0,300);

    }

    public void volume()
    {
        SeekBar         sbVolume        =   findViewById(R.id.sbVolume);
        AudioManager    audioManager    =   (AudioManager) getSystemService(AUDIO_SERVICE);

        int volumeMax   =   audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        sbVolume.setMax(volumeMax);

        int currentVolume   =   audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        sbVolume.setProgress(currentVolume);

        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}