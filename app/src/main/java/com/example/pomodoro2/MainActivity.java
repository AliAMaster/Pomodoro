package com.example.pomodoro2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    String mode = "Work";
    int cycle = 0;
    MediaPlayer music = null;
    CountDownTimer Timer_s = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton start = findViewById(R.id.button);
        ImageButton refresh = findViewById(R.id.button2);
        final TextView main_time = findViewById(R.id.textView3);
        music = MediaPlayer.create(getApplicationContext(),R.raw.music);
        music.start();
        music.setLooping(true);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (music.isPlaying()) {
                    controller();
                    music.pause();
                }
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_Cycle(1);
                cycle = 0;
                set_Mode("Work");
                if (Timer_s!=null) Timer_s.cancel();
                main_time.setText("00:00");
                music.start();
            }
        });
    }

    public String stringter(long millis) {
        millis = millis/1000;
        long min = millis/60;
        long sec = millis%60;
        String mins , secs;
        if (min>9) { mins = String.valueOf(min); }
        else { mins = "0" + String.valueOf(min); }
        if (sec>9) { secs = String.valueOf(sec); }
        else { secs = "0" + String.valueOf(sec); }
        return mins + ":" + secs;
    }

    public void setTimer(int min) {
        final TextView main_time = findViewById(R.id.textView3);
        Timer_s = new CountDownTimer(min*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                main_time.setText(stringter(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                music.start();
            }
        }.start();
    }

    public void set_Mode(String mod) {
        TextView Mode = findViewById(R.id.textView);
        Mode.setText(mod);
        mode = mod;
    }

    public void set_Cycle(int cyc) {
        TextView Cycle = findViewById(R.id.textView2);
        Cycle.setText(String.valueOf(cyc));
        cycle = cyc;
    }

    public void controller() {
        if (cycle == 0) {
            set_Cycle(1);
            setTimer(25);
        } else {
            if (mode.equals("Work")) {
                set_Mode("Break");
                if (cycle == 4) setTimer(15);
                else setTimer(5);
            } else {
                set_Mode("Work");
                setTimer(25);
                if (cycle == 4) set_Cycle(1);
                else set_Cycle(cycle + 1);
            }
        }
    }
}