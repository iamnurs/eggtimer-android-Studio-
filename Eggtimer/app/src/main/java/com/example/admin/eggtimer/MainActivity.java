package com.example.admin.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controlButton;
    CountDownTimer timer;
    boolean isActive = false;

    public void timerUpdate(int progress){
        int minute = progress/60;
        int second = progress - minute*60;
        String s = Integer.toString(second);

        if(second < 10)
            s = "0" + s;

        timerTextView.setText(Integer.toString(minute) + ":" + s);
    }

    public void controlTimer (View view){
        if(!isActive) {
            isActive = true;
            timerSeekBar.setEnabled(false);
            controlButton.setText("Stop!");

            timer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                public void onTick(long millisUntilFinished) {
                    timerUpdate((int) millisUntilFinished / 1000);
                }

                public void onFinish() {
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();
                    isActive = false;
                    timerTextView.setText("0:30");
                    timerSeekBar.setProgress(30);
                    controlButton.setText("Go!");
                    timerSeekBar.setEnabled(true);
                }
            }.start();
        } else {
            timer.cancel();
            isActive = false;
            timerTextView.setText("0:30");
            timerSeekBar.setProgress(30);
            controlButton.setText("Go!");
            timerSeekBar.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        controlButton = (Button) findViewById(R.id.controlButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timerUpdate(progress);
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
