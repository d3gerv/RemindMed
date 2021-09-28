package com.example.capstone1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class edit_delete_bloodsugar extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    Button timeButtonEditM, deleteBP;
    int  frequencychoide, choice;
    private Button dateButton, endDateButton, timeButtonBPLater, saveBPbutton;
    final int start = 1;
    final int end = 2;
    private DatePickerDialog datePickerDialog;
    String userId, time, strDate, strEnd, startdate, frequencyDB, fname, placeholderName;
    Calendar calendar = Calendar.getInstance();
    Calendar c;
    Spinner frequencyBP;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    Date startDate, endDate;
    static final SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
    private measurement_info_today measurement_info_today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_bloodsugar);

        measurement_info_today = (measurement_info_today) getIntent().getSerializableExtra("measurement_info");
        dateButton = findViewById(R.id.startButtonbs);
        endDateButton = findViewById(R.id.endButtonbs);
        frequencyBP = findViewById(R.id.frequency_spinner_editbs);
        timeButtonBPLater = findViewById(R.id.time_btn_editbs);
        saveBPbutton = findViewById(R.id.btnsavesugaredit);
        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = rootAuthen.getCurrentUser().getUid();
        deleteBP = findViewById(R.id.deleteBtn);

        getData();
        setData();

        ArrayAdapter<String> adapterFrequency = new ArrayAdapter<String>(edit_delete_bloodsugar.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array
                .frequency));
        adapterFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frequencyBP.setAdapter(adapterFrequency);
        if(frequencyDB!=null)
        {
            int pos = adapterFrequency.getPosition(frequencyDB);
            frequencyBP.setSelection(pos);
        }

        Log.d("helo", "test" + frequencyDB);


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


        saveBPbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAlarm();
            }
        });

        deleteBP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_delete_bloodsugar.this);
                builder.setTitle("Are you sure about deleting this reminder");
                builder.setMessage("Deleting reminders are permanent");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteBPAlarm();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }

        });
    }

    private void getData()
    {
        if(getIntent().hasExtra("Time") && getIntent().hasExtra("Date"))
        {
            time = getIntent().getStringExtra("Time");
            strDate = getIntent().getStringExtra("Date");
            strEnd = getIntent().getStringExtra("EndDate");
            frequencyDB = getIntent().getStringExtra("FrequencyTitle");


        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData()
    {
        dateButton.setText(strDate);
        endDateButton.setText(strEnd);
        timeButtonBPLater.setText(time);


    }

    private void deleteBPAlarm() {
        fstore.collection("users").document(currentFirebaseUser.getUid()).collection("Health Measurement Alarm").document(measurement_info_today.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(edit_delete_bloodsugar.this, "Deleted Alarm", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(edit_delete_bloodsugar.this, home_page.class));
                            finish();

                        }
                    }
                });
    }
    private void updateAlarm() {
        getData();
        fname = frequencyBP.getSelectedItem().toString();
        startDate = getDateFromString(dateButton.getText().toString());
        time = timeButtonBPLater.getText().toString().trim();
        endDate = getDateFromString(endDateButton.getText().toString());
        measurement_info_today m = new measurement_info_today(placeholderName, time, startDate, endDate, frequencychoide, fname);
        fstore.collection("users").document(currentFirebaseUser.getUid()).collection("Health Measurement Alarm")
                .document(measurement_info_today.getId()).update("Frequency", m.getFrequency(),
                "FrequencyTitle", m.getFrequencyTitle(), "StartDate", m.getStartDate(), "Time", m.getTime(), "EndDate", m.getEndDate()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void avoid) {
                Toast.makeText(edit_delete_bloodsugar.this, "Medications Changed", Toast.LENGTH_LONG).show();
                startActivity(new Intent(edit_delete_bloodsugar.this, home_page.class));
                finish();
            }
        });
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
                    dateButton.setText(startdate);
                }
                else if (choice == end)
                {
                    String enddate = makeDateString(day, month, year);
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

}
