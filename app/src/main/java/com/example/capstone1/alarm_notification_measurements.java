package com.example.capstone1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;

public class alarm_notification_measurements extends AppCompatActivity {
    Button stopAlarm, snooze;
    Calendar c = Calendar.getInstance();;
    Calendar myAlarmDate = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_notification_measurements);
        stopAlarm = findViewById(R.id.stop_button_measurements);
        snooze = findViewById(R.id.snooze_button_measurements);
        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snoozeAlarm();
                Toast.makeText(alarm_notification_measurements.this, "Alarm snoozed for 15 minutes", Toast.LENGTH_SHORT).show();

            }
        });
        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAlarm();
            }
        });
    }

    private void stopAlarm()
    {
        Intent intent = new Intent(this, alarmreceivermeasurement.class);
        Intent intentpage = new Intent(this, today_page_recycler.class );
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Ringtone ringtone = alarmreceivermeasurement.ringtone1;
        Timer timer = alarmreceivermeasurement.mTimer;
        timer.cancel();
        ringtone.stop();
        startActivity(intentpage);
    }

    private void snoozeAlarm()
    {
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.MINUTE, 15);
        Intent intent = new Intent(this, alarmreceivermeasurement.class);
        Intent intentpage = new Intent(this, today_page_recycler.class );
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        alarmManager.cancel(pendingIntent);
        Ringtone ringtone = alarmreceivermeasurement.ringtone1;
        Timer timer = alarmreceivermeasurement.mTimer;
        timer.cancel();
        ringtone.stop();
        startActivity(intentpage);
    }
}