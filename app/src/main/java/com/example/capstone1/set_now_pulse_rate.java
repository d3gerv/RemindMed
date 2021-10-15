package com.example.capstone1;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class set_now_pulse_rate extends AppCompatActivity {
    EditText tempVal;
    Button saveTempNow;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId, dateToday, timeToday, time, startdate, enddate;
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
    int alarmYear, alarmMonth, alarmDay, alarmHour,alarmMin, id ,freq;
    Calendar myAlarmDate = Calendar.getInstance();
    Date myDate;
    private measurement_info_today measurement_info_today;
    Date c = Calendar.getInstance().getTime();
    int choice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_now_pulse_rate);
        measurement_info_today = (measurement_info_today) getIntent().getSerializableExtra("measuremy_info_today");
        saveTempNow = findViewById(R.id.save_set_now_pr);
        tempVal = findViewById(R.id.pr_value_set_now);
        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = rootAuthen.getCurrentUser().getUid();

        SimpleDateFormat df = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault());
        dateToday = df.format(c);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        timeToday = timeFormat.format(c);


        saveTempNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Record = tempVal.getText().toString().trim();
                Map<String,Object> user =new HashMap<>();
                int recordInt = Integer.parseInt(Record);

                getData();
                if (choice == 1 && freq == 2)
                {
                    user.put("Name", "Pulserate");
                    user.put("Record",Record);
                    user.put("Date", startdate);
                    user.put("Time", time);
                    moveStartDate();
                    startAlarm(myAlarmDate);

                }
                else if (choice == 1 && freq == 2)
                {
                    user.put("Name", "Pulserate");
                    user.put("Record",Record);
                    user.put("Date", startdate);
                    user.put("Time", time);
                    moveStartDateWeek();
                    startAlarm(myAlarmDate);

                }
                else
                {
                    user.put("Name", "Pulserate");
                    user.put("Record",Record);
                    user.put("Date", dateToday);
                    user.put("Time", timeToday);
                }

                if(recordInt > 37)
                {
                    NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)
                            new NotificationCompat.Builder(set_now_pulse_rate.this, "abnormalbp");
                    mBuilder.setSmallIcon(R.drawable.ic_launcher_background);
                    mBuilder.setContentTitle("Abnormal Measurement");
                    mBuilder.setContentText("You have recently recorded an abnormal measurement");
                    mBuilder.setAutoCancel(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(set_now_pulse_rate.this);
                    notificationManager.notify(7, mBuilder.build());

                    AlertDialog.Builder aBuilder = new AlertDialog.Builder(set_now_pulse_rate.this);
                    aBuilder.setCancelable(true);
                    aBuilder.setTitle("Abnormal Measurement");
                    aBuilder.setMessage("You have recently recorded an abnormal measurement for your pulse rate click ok to see some recommendations to normalize it");

                    aBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });

                    aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(set_now_pulse_rate.this, recommendations.class);
                            intent.putExtra("description", "Pulserate");
                            startActivity(intent);
                        }
                    });

                    aBuilder.show();
                }else{
                    startActivity(new Intent(set_now_pulse_rate.this, home_page.class));

                }


                Log.d("Calendar", "Selected day change " + timeToday );
                fstore.collection("users").document(userId).collection("New Health Measurements")
                        .document("Pulserate").collection("Pulserate")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(set_now_pulse_rate.this, "New Temperature measurement added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Log.d(TAG,"onSuccess: failed");
                            }
                        });
            }
        });
    }

    private void getData() {
        if (getIntent().hasExtra("Time") && getIntent().hasExtra("Date")) {
            time = getIntent().getStringExtra("Time");
            startdate = getIntent().getStringExtra("Date");
            enddate = getIntent().getStringExtra("EndDate");
            choice = getIntent().getIntExtra("fromToday", 1);
            freq = getIntent().getIntExtra("Frequency", 1 );
            alarmHour = getIntent().getIntExtra("Hour", 0);
            alarmMin = getIntent().getIntExtra("Minute", 0);
        }
    }

    private void moveStartDate(){
        getData();
        myDate = getDateFromString(startdate);
        if (!startdate.equals(enddate))
        {
            myDate = DateUtil.addDays(myDate, 1);
        }

        String month = (String) DateFormat.format("MM", myDate);
        String day = (String) DateFormat.format("dd", myDate);
        String year = (String) DateFormat.format("yyyy", myDate);

        alarmMonth = Integer.parseInt(month);
        alarmDay = Integer.parseInt(day);
        alarmYear = Integer.parseInt(year);

        measurement_info_today measurement = new measurement_info_today("Pulserate", time, getDateFromString(startdate),
                getDateFromString(enddate),choice, "Daily");
        if (startdate.equals(enddate)) {
            fstore.collection("users").document(userId).collection("Health Measurement Alarm").document(measurement_info_today.getId()).delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(set_now_pulse_rate.this, "Deleted Alarm", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        } else {
            fstore.collection("users").document(userId).collection("Health Measurement Alarm")
                    .document(measurement_info_today.getId()).update("StartDate", myDate)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            Toast.makeText(set_now_pulse_rate.this, "Confirmed Intake", Toast.LENGTH_LONG).show();
                        }
                    });
        }

    }

    private void moveStartDateWeek() {
        getData();
        myDate = getDateFromString(startdate);
        if (!startdate.equals(enddate) && daysBetween(dateToCalendar(getDateFromString(startdate)), dateToCalendar(getDateFromString(enddate))) > 7)
        {
            myDate = DateUtil.addDays(myDate, 7);
        }
        else
        {
            myDate = getDateFromString(enddate);
        }
        String month = (String) DateFormat.format("MM", myDate);
        String day = (String) DateFormat.format("dd", myDate);
        String year = (String) DateFormat.format("yyyy", myDate);

        alarmMonth = Integer.parseInt(month);
        alarmDay = Integer.parseInt(day);
        alarmYear = Integer.parseInt(year);

        if (startdate.equals(enddate)) {
            fstore.collection("users").document(userId).collection("Health Measurement Alarm").document(measurement_info_today.getId()).delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(set_now_pulse_rate.this, "Deleted Alarm", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        } else {
            fstore.collection("users").document(userId).collection("Health Measurement Alarm")
                    .document(measurement_info_today.getId()).update("StartDate", myDate)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            Toast.makeText(set_now_pulse_rate.this, "Confirmed Intake", Toast.LENGTH_LONG).show();
                        }
                    });
        }

    }

    private void startAlarm(Calendar c)
    {
        getData();
        String month = (String) DateFormat.format("MM", myDate);
        String day = (String) DateFormat.format("dd", myDate);
        String year = (String) DateFormat.format("yyyy", myDate);

        alarmMonth = Integer.parseInt(month);
        alarmDay = Integer.parseInt(day);
        alarmYear = Integer.parseInt(year);
        myAlarmDate.setTimeInMillis(System.currentTimeMillis());
        myAlarmDate.set(alarmYear, alarmMonth-1, alarmDay, alarmHour, alarmMin);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, alarmreceiver.class);
        Intent i = new Intent(this, alarm_notification.class);
        id = new Random().nextInt(1000000);
        i.putExtra("userID", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), pendingIntent);
        //  alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), 24*60*60*1000, pendingIntent);
    }

    public void nowpulse_To_hm(View view) {
        Intent intent = new Intent(set_now_pulse_rate.this, health_measurements.class);
        startActivity(intent);
    }
    public void nowpulse_To_hm1(View view) {
        Intent intent = new Intent(set_now_pulse_rate.this, health_measurements.class);
        startActivity(intent);
    }

    public static long daysBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        long daysBetween = 0;
        while (date.before(endDate)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    private Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

    public Date getDateFromString(String dateToSave) {
        try {
            Date date = dateFormat.parse(dateToSave);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }
}