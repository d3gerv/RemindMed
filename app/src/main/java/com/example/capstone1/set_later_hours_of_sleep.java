package com.example.capstone1;

import static com.example.capstone1.user_information.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class set_later_hours_of_sleep extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    int choice, frequencychoide;
    private Button dateButton, endDateButton, timeButtonBPLater, saveHOSbutton;
    final int start = 1;
    final int end = 2;
    int id ;
    private DatePickerDialog datePickerDialog;
    String userId, startdate, enddate ;
    int alarmYear, alarmMonth, alarmDay,alarmHour,alarmMin;
    Calendar calendar = Calendar.getInstance();
    Calendar c;
    Calendar myAlarmDate = Calendar.getInstance();
    Spinner frequencyBP;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    static final SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_later_hours_of_sleep);


        dateButton = findViewById(R.id.duration_start_hos_later);
        endDateButton = findViewById(R.id.duration_end_hos_later);
        frequencyBP = findViewById(R.id.frequency_set_later_hos);
        timeButtonBPLater = findViewById(R.id.time_later_hos);
        saveHOSbutton = findViewById(R.id.save_hos_later);
        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = rootAuthen.getCurrentUser().getUid();



        ArrayAdapter<String> adapterFrequency = new ArrayAdapter<String>(set_later_hours_of_sleep.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array
                .frequency));
        adapterFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencyBP.setAdapter(adapterFrequency);

        frequencyBP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                frequencychoide = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = 1;
                initDatePicker();
                openDatePicker();
            }
        });
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = 2;
                initDatePicker();
                openDatePicker();
            }
        });


        timeButtonBPLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        saveHOSbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String Time = timeButtonBPLater.getText().toString().trim();
                    String StartDate =  dateButton.getText().toString().trim();
                    String EndDate = endDateButton.getText().toString().trim();
                    String frequencyName = frequencyBP.getSelectedItem().toString();
                    Map<String, Object> user = new HashMap<>();
                    user.put("HMName", "Sleep");
                    user.put("Time", Time);
                    user.put("StartDate", getDateFromString(StartDate));
                    user.put("EndDate", getDateFromString(EndDate));
                    user.put("Frequency", frequencychoide);
                    user.put("FrequencyTitle", frequencyName);
                    user.put("Hour", c.get(Calendar.HOUR_OF_DAY));
                    user.put("Minute", c.get(Calendar.MINUTE));
                    user.put("idCode", id);

                    if (Time.equals("Set Time"))
                    {
                        Toast.makeText(getApplicationContext(), "Please select Time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if ( frequencyBP.getSelectedItemPosition() == 0)
                    {
                        Toast.makeText(getApplicationContext(), "Please select frequency", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (StartDate.equals("Start")) {
                        Toast.makeText(getApplicationContext(), "Please select Start Date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (EndDate.equals("End")) {
                        Toast.makeText(getApplicationContext(), "Please select End Date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        setDate(myAlarmDate);
                    }
                    if (getDateFromString(startdate).after(getDateFromString(enddate))) {
                        Toast.makeText(getApplicationContext(), "Start Date should be before End Date", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (myAlarmDate.getTimeInMillis() < System.currentTimeMillis()) {
                        Toast.makeText(set_later_hours_of_sleep.this, "Set the time and date to the future", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else
                    {
                        startAlarm(myAlarmDate);
                    }



                    fstore.collection("users").document(userId).collection("Health Measurement Alarm")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(set_later_hours_of_sleep.this, "New HM added", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onSuccess: failed");
                                }
                            });

                    startActivity(new Intent(set_later_hours_of_sleep.this, home_page.class));
                }catch (Exception e){
                    Toast.makeText(set_later_hours_of_sleep.this, "Please input data in all fields", Toast.LENGTH_SHORT).show();

                }



            }
        });
    }

    private void startAlarm(Calendar c)
    {

        myAlarmDate.setTimeInMillis(System.currentTimeMillis());
        myAlarmDate.set(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMin);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
        Intent intent = new Intent(this, alarmreceivermeasurement.class);
        id = new Random().nextInt(1000000);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        intentArray.add(pendingIntent);
    }

    private void setDate(Calendar c)
    {
        myAlarmDate.setTimeInMillis(System.currentTimeMillis());
        myAlarmDate.set(alarmYear, alarmMonth, alarmDay, alarmHour, alarmMin);
    }


    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month+=1;
                if (choice == start)
                {
                    startdate = makeDateString(day, month, year);
                    alarmYear = year;
                    alarmMonth = month-1;
                    alarmDay = day;
                    dateButton.setText(startdate);
                }
                else if (choice == end)
                {
                    enddate = makeDateString(day, month, year);
                    endDateButton.setText(enddate);

                }
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
    }

    private String makeDateString(int day, int month, int year)
    {
        return month + "/" + day + "/" + year;
    }

    public void openDatePicker() {
        datePickerDialog.show();
    }

    private void updateTimeText(Calendar c)
    {
        String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        timeButtonBPLater.setText(timeText);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        alarmHour = hourOfDay;
        alarmMin = minute;
        updateTimeText(c);
    }
    public Date getDateFromString(String dateToSave) {
        try {
            Date date = format.parse(dateToSave);
            return date ;
        } catch (ParseException e){
            return null ;
        }
    }
    public void latersleep_To_lhm(View view) {
        Intent intent = new Intent(set_later_hours_of_sleep.this, setLaterHM.class);
        startActivity(intent);
    }
}