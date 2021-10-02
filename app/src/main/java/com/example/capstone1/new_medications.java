package com.example.capstone1;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.capstone1.databinding.ActivityNewMedicationsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class new_medications extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    EditText medication, dosage, inventory;
    Button buttonsavemedication;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    final int start = 1;
    final int end = 2;
    String userId, startdate;
    Spinner spinnertypeunit, spinnerfrequencymedication;
    Calendar calendar = Calendar.getInstance();;
    private DatePickerDialog datePickerDialog, datePickerDialog2;
    private Button dateButton, endDateButton;
    Calendar c;
    static final SimpleDateFormat format = new SimpleDateFormat("M/dd/yyyy");
    Button timeButtonmedtst;
    int hour, minute;
    int year, month, day, choice, typechoice, frequencychoide;
    String dateToday = String.valueOf(android.text.format.DateFormat.format("M/dd/yyyy", new java.util.Date()));




    private static final String TAG = "new_medications";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medications);
        createNotificationChannel();
        medication = findViewById(R.id.medicine_Box);
        dosage = findViewById(R.id.DosageBox);
        inventory = findViewById(R.id.inventoryBox);
        dateButton = findViewById(R.id.startButton_date);
        endDateButton = findViewById(R.id.endButton_one);
        spinnertypeunit = findViewById(R.id.type_spinner_one);
        spinnerfrequencymedication = findViewById(R.id.frequency_spinner_ten);
        buttonsavemedication = findViewById(R.id.save_medication_button);
        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        timeButtonmedtst = findViewById(R.id.timeButtonmed);
        userId = rootAuthen.getCurrentUser().getUid();

        ArrayAdapter<String> adapterFrequency = new ArrayAdapter<String>(new_medications.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array
                .frequency));
        adapterFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfrequencymedication.setAdapter(adapterFrequency);

        ArrayAdapter<String> adapterUnit = new ArrayAdapter<String>(new_medications.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type));

        adapterUnit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertypeunit.setAdapter(adapterUnit);

        spinnertypeunit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typechoice = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerfrequencymedication.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        timeButtonmedtst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        buttonsavemedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Medication = medication.getText().toString().trim();
                String Dosage = dosage.getText().toString();
                String Inventory = inventory.getText().toString().trim();
                String Time = timeButtonmedtst.getText().toString().trim();
                String StartDate =  dateButton.getText().toString().trim();
                String EndDate = endDateButton.getText().toString().trim();
                Map<String, Object> user = new HashMap<>();
                user.put("Medication", Medication);
                user.put("Dosage", Dosage);
                user.put("InventoryMeds", Inventory);
                user.put("Time", Time);
                user.put("StartDate", getDateFromString(StartDate));
                user.put("EndDate", getDateFromString(EndDate));
                user.put("MedicineType", typechoice);
                user.put("Frequency", frequencychoide);
                user.put("PillStatic", Integer.parseInt(Inventory));
                startAlarm(c);

                if (TextUtils.isEmpty(Medication)) {
                    medication.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(Dosage) ){
                    dosage.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(Inventory)) {
                    inventory.setError("This field is required");
                    return;
                }
                if(spinnertypeunit.getSelectedItemPosition()==0){
                    Toast.makeText(getApplicationContext(), "Please select type", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(spinnerfrequencymedication.getSelectedItemPosition()==0){
                    Toast.makeText(getApplicationContext(), "Please select frequency", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(timeButtonmedtst.getText().toString().equals("Set Time")){
                    Toast.makeText(getApplicationContext(), "Please select Time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(dateButton.getText().toString().equals("Start")){
                    Toast.makeText(getApplicationContext(), "Please select Start Date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(endDateButton.getText().toString().equals("End")){
                    Toast.makeText(getApplicationContext(), "Please select End Date", Toast.LENGTH_SHORT).show();
                    return;
                }



                fstore.collection("users").document(userId).collection("New Medications")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(new_medications.this, "New Medication added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onSuccess: failed");
                            }
                        });
                Log.d("class", "Hour and minute" + dateToday);

            }
        });
    }


    public void Medication_To_Home(View view) {
        Intent intent = new Intent(new_medications.this, home_page.class);
        startActivity(intent);
    }

    public void Medication_To_OCR(View view) {
        Intent intent = new Intent(new_medications.this, optical_character_recognition.class);
        startActivity(intent);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyApp";
            String description = "My app manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("MyApp", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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


        if (day<10)
        {
            return month + "/" +"0" +day + "/" + year;
        }
        else
        {
            return month + "/"  +day + "/" + year;
        }

    }

    public void openDatePicker() {
        datePickerDialog.show();
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

    private void updateTimeText(Calendar c)
    {
        String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        timeButtonmedtst.setText(timeText);
    }

    private void startAlarm(Calendar c)
    {
        if(dateToday.equals(dateButton.getText().toString()))
        {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, alarmreceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 123, intent, 0);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        }
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

  /*  public void popTimePicker (View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
                //String time = hour + ":" + minute;
                timeButtonmedtst.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, false);
        timePickerDialog.setTitle("Set Time");
        timePickerDialog.show();
        calendar = Calendar.getInstance();
        Log.d("class", "Hour and minute" + calendar);
        month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month+=1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };


        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());


    private String makeDateString(int day, int month, int year)
    {
        return month + "/" + day + "/" + year;
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}*/
