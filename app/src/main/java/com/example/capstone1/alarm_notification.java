package com.example.capstone1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class alarm_notification extends AppCompatActivity {
    Button stopAlarm, snooze;
    Calendar c = Calendar.getInstance();;
    Calendar myAlarmDate = Calendar.getInstance();
    Timer mTimer;
    Ringtone ringtone = alarmreceiver.ringtone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_notification);
        stopAlarm = findViewById(R.id.stop_button);
        snooze = findViewById(R.id.snooze_button);

        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snoozeAlarm();
                Toast.makeText(alarm_notification.this, "Alarm snoozed for 15 minutes", Toast.LENGTH_SHORT).show();

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
        Intent intent = new Intent(this, alarmreceiver.class);
        Intent intentpage = new Intent(this, today_page_recycler.class );
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Ringtone ringtone = alarmreceiver.ringtone;
        Timer timer = alarmreceiver.mTimer;
        timer.cancel();
        ringtone.stop();
        startActivity(intentpage);
    }

    private void snoozeAlarm()
    {
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.MINUTE, 15);
        Intent intent = new Intent(this, alarmreceiver.class);
        Intent intentpage = new Intent(this, today_page_recycler.class );
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        Ringtone ringtone = alarmreceiver.ringtone;
        Timer timer = alarmreceiver.mTimer;
        timer.cancel();
        ringtone.stop();
        startActivity(intentpage);
    }




}