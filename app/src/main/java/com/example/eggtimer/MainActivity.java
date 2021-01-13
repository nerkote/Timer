package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button controllerBtn;
    private EditText timeText;
    private SeekBar timerSeekBar;
    boolean counterIsActive = false;
    private CountDownTimer countDownTimer;
    private static final String TAG = "MainActivity";

    public void updateTimer(int seccondsLeft) {
        int minutes = (int) seccondsLeft/60;
        int seconds = seccondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);

        if (seconds<=9) {
            secondString = "0" + secondString;
        }

        timeText.setText(Integer.toString(minutes) + ":" + secondString);
    }

    public void resetTimer(){
        timeText.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();

        controllerBtn.setText("Go");

        timerSeekBar.setEnabled(true);
        counterIsActive = false;
    }

    public void controlTimer(View view) {
        Log.i(TAG, "controlTimer: pressed");

        if (counterIsActive == false) {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerBtn.setText("stop");


            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long milisUntilFnish) {

                    updateTimer((int) milisUntilFnish / 1000);

                }

                @Override
                public void onFinish() {

                    resetTimer();
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mPlayer.start();

                    Log.i(TAG, "onFinish: timer is finished");

                }
            }.start();
        } else {

            resetTimer();


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.timerSeekBar);
        timeText = findViewById(R.id.timeText);
        controllerBtn = findViewById(R.id.controllerBtn);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                updateTimer(i);





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