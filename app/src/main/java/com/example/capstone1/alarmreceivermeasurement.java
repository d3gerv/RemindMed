package com.example.capstone1;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

public class alarmreceivermeasurement extends BroadcastReceiver {
    static Ringtone ringtone1;
    static Timer mTimer;


    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
        mediaPlayer.start();
        Intent i = new Intent(context, alarm_notification_measurements.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i , 0 );

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(context, "MyApp")
                .setSmallIcon(R.drawable.logoicon)
                .setContentTitle("Record your measurements")
                .setContentText("Its time to record your measurements")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
        ringtone1 = RingtoneManager.getRingtone(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        ringtone1.play();
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (!ringtone1.isPlaying()) {
                    ringtone1.play();
                }
            }
        }, 1000*1, 1000*1);


        context.startActivity(i);
    }
}
