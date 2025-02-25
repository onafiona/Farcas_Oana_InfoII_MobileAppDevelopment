package com.example.lab3_ex2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private boolean isOn=true;
    private boolean auto=false;
    private ConstraintLayout bckgr;
    private Switch AutoModeSwitch;
    private static final String CHANNEL_ID = "light_notification";
    private int colorindex=0;
    private int [] colors={Color.WHITE, Color.RED, Color.BLUE, Color.GREEN};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bckgr=findViewById(R.id.main);
        bckgr.setBackgroundColor(Color.YELLOW);
        AutoModeSwitch=findViewById(R.id.AutoModeSwitch);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS},101);
        }
        NotifChannel();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void IsAutoOn(View view)
    {
        auto=((Switch) view).isChecked();
        if(auto)
            autoLight();
    }
    public void switchLight(View view)
    {
        if(auto) return;

        if(isOn)
        {
            bckgr.setBackgroundColor(Color.DKGRAY);
            isOn=false;
        }
        else
        {
            bckgr.setBackgroundColor(Color.YELLOW);
            isOn=true;
        }
    }

    public void autoLight()
    {
        int hour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if(hour>=7 && hour<18) {
            bckgr.setBackgroundColor(Color.WHITE);
            sendNotification("Good morning! Do you need more light?");
        }
        else if(hour>=18 && hour <22)
            bckgr.setBackgroundColor(Color.rgb(255,165,0));
        else {
            bckgr.setBackgroundColor(Color.DKGRAY);
            sendNotification("Good night! The light has turned off automatically.");
        }
    }

    private void NotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Light Notifications", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(String message) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent= new Intent(this, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Light Control")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        manager.notify(1, builder.build());
    }
    public void ChangeCustomColor(View view)
    {
        if(auto) return;
        bckgr.setBackgroundColor(colors[colorindex]);
        colorindex=(colorindex+1)%colors.length;
    }
}