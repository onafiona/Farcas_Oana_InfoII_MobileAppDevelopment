package com.example.lab9ex2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView timeDisplay;
    private int sec=0;
    private boolean isrunning=false;
    private final Handler handler=new Handler();

    private final Runnable updateTimer=new Runnable() {
        @Override
        public void run() {
            if(isrunning){
                sec++;
                int h=sec/3600;
                int min=(sec%3600)/60;
                int remainingsec=sec%60;
                String time=String.format("%02d:%02d:%02d", h, min, remainingsec);
                timeDisplay.setText(time);
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        timeDisplay=findViewById(R.id.timeDisplay);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void startStopwatch(View view){
        if(!isrunning){
            isrunning=true;
            handler.post(updateTimer);
        }
    }

    public void stopStopwatch(View view){
        if(isrunning){
            isrunning=false;
        }
    }

    public void resetStopwatch(View view){
        isrunning=false;
        sec=0;
        timeDisplay.setText("00:00:00");
    }
}