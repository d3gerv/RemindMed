package com.example.capstone1;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class edit_delete_medications extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    EditText medName, medInventory, dosageBoxET;
    static final SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
    String title, amount, time,  strDate, strEnd, frequencyDB, medTypeDB, dosage;
    Date startdate, enddate;
    final int start = 1;
    final int end = 2;
    Calendar calendar = Calendar.getInstance();
    Calendar c;
    ImageView helpdosage;
    Calendar myAlarmDate;
    Button timeButtonEdit, dateFormat, delete, change, enddatebutton;
    FirebaseFirestore db;
    private DatePickerDialog datePickerDialog;
    int hour, minuteDB, typechoice, frequencychoide, choice, alarmIDdb, alarmID, alarmYear, alarmMonth, alarmDay, hourchange, minchange;
    Spinner mySpinnerfrequency, mySpinnertype;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private medication_info  medication_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_medications);
        medication_info = (medication_info) getIntent().getSerializableExtra("medication_info");
        db = FirebaseFirestore.getInstance();
        delete = findViewById(R.id.deleteBtn);
        change = findViewById(R.id.save_changes);
        dateFormat = findViewById(R.id.startButton_oneM);
        enddatebutton = findViewById(R.id.endButton_oneM);
        dosageBoxET = findViewById(R.id.DosageBox);
        medName = findViewById(R.id.medicine_Box);
        helpdosage = findViewById(R.id.helpDosageEdit);
        medInventory = findViewById(R.id.inventoryBox);
        final Calendar calendar = Calendar.getInstance();
        initDatePicker();
        getData();
        getHourandMin();

        helpdosage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder aBuilder = new AlertDialog.Builder(edit_delete_medications.this);
                aBuilder.setCancelable(true);
                aBuilder.setTitle("Dosage");
                aBuilder.setMessage("Enter the dosage you will take for your medication. " +
                        "Dosage depends on the type of medication. If you are taking a pill, capsule, " +
                        "or tablet put one if you only have to take one or put the specified amount. " +
                        "If you are taking liquid medication press tablespoon if you only have to take a tablespoon or press ml and put your specified amount");
                aBuilder.show();

            }
        });


        Log.d("K", "Hour" +hour+" " + minuteDB);


        //added spinner
         mySpinnertype = (Spinner) findViewById(R.id.type_spinner_two);
         mySpinnerfrequency = (Spinner) findViewById(R.id.frequency_spinner_two);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(edit_delete_medications.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinnertype.setAdapter(myAdapter);


        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(edit_delete_medications.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.frequency));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinnerfrequency.setAdapter(myAdapter2);

        mySpinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typechoice = position;
                if (position == 4)
                {
                    dosageBoxET.setText(String.valueOf(15));
                    dosageBoxET.setFocusable(false);
                    dosageBoxET.setEnabled(false);
                }
                else
                {
                    dosageBoxET.setFocusableInTouchMode(true);
                    dosageBoxET.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mySpinnerfrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                frequencychoide = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dateFormat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = 1;
                initDatePicker();
                openDatePicker();
            }
        });
        enddatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = 2;
                initDatePicker();
                openDatePicker();
            }
        });



        timeButtonEdit = findViewById(R.id.timeButton_edit);
        timeButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
                Log.d("K", "helo" +" " +alarmYear +" " + alarmMonth +" " + alarmDay +" " + hour+" " + minuteDB);

            }
        });

        setData();
        if(frequencyDB!=null)
        {
            int pos = myAdapter2.getPosition(frequencyDB);
            mySpinnerfrequency.setSelection(pos);
        }

        if(medTypeDB!=null)
        {
            int pos = myAdapter.getPosition(medTypeDB);
            mySpinnertype.setSelection(pos);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(edit_delete_medications.this);
                builder.setTitle("Are you sure about deleting this reminder");
                builder.setMessage("Deleting reminders are permanent");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMedication();
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

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarm(myAlarmDate);
            }
        });

        Log.d("K", "helo" +" " +alarmYear +" " + alarmMonth +" " + alarmDay +" " + hourchange+" " + minchange);
    }

    private void getData()
    {
        if(getIntent().hasExtra("description") && getIntent().hasExtra("time"))
        {
            title = getIntent().getStringExtra("description");
            amount = getIntent().getStringExtra("pill");
            time = getIntent().getStringExtra("time");
            strDate = getIntent().getStringExtra("startdate");
            strEnd = getIntent().getStringExtra("enddate");
            frequencyDB = getIntent().getStringExtra("FrequencyTitle");
            medTypeDB = getIntent().getStringExtra("MedicineTitle");
            dosage = getIntent().getStringExtra("dosage");
            alarmIDdb = getIntent().getIntExtra("AlarmID", 0);



        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    private void getHourandMin()
    {
        if(getIntent().hasExtra("Hour") && getIntent().hasExtra("Minute"))
        {

            hour = getIntent().getIntExtra("Hour", 0);
            minuteDB = getIntent().getIntExtra("Minute", 0);

        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                getData();
                month+=1;
                if (choice == start)
                {
                    getData();
                    alarmYear = year ;
                    alarmMonth = month;
                    alarmDay = day;

                    String startdate = makeDateString(day, month, year);
                    dateFormat.setText(startdate);
                }
                else if (choice == end)
                {
                    String enddate = makeDateString(day, month, year);
                    enddatebutton.setText(enddate);

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

    private void setData()
    {
        medName.setText(title);
        dateFormat.setText(strDate);
        enddatebutton.setText(strEnd);
        medInventory.setText(amount);
        timeButtonEdit.setText(time);
        dosageBoxET.setText(dosage);
    }
    private void deleteMedication() {
        db.collection("users").document(currentFirebaseUser.getUid()).collection("New Medications").document(medication_info.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(edit_delete_medications.this, "Deleted Alarm", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(edit_delete_medications.this, home_page.class));
                            finish();

                        }
                    }
                });
    }
    private void updateAlarm() {
       /* getData();
        title = medName.getText().toString().trim();
        amount = medInventory.getText().toString().trim();
        startdate = getDateFromString(dateFormat.getText().toString());
        time = timeButtonEdit.getText().toString().trim();
        enddate = getDateFromString(enddatebutton.getText().toString());
        String frequencyName = mySpinnerfrequency.getSelectedItem().toString();
        String medicationTypeName = mySpinnertype.getSelectedItem().toString();

        if (TextUtils.isEmpty(title)) {
            medName.setError("This field is required");
            return;
        }
        else if (TextUtils.isEmpty(dosage) ){
            dosageBoxET.setError("This field is required");
            return;
        }
        else if (TextUtils.isEmpty(amount)) {
            medInventory.setError("This field is required");
            return;
        }
        else if (Integer.parseInt(dosageBoxET.getText().toString()) > Integer.parseInt(medInventory.getText().toString())){
            dosageBoxET.setError("Dosage should be less than the Inventory");
            return;

        }
        else if (Integer.parseInt(dosageBoxET.getText().toString()) < 0){
            dosageBoxET.setError("Dosage should not be a negative number");
            return;

        }
        else if (Integer.parseInt(medInventory.getText().toString()) < 0){
            medInventory.setError("Dosage should not be a negative number");
            return;
        }
        else if(mySpinnerfrequency.getSelectedItemPosition()==0){
            Toast.makeText(getApplicationContext(), "Please select type", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(mySpinnertype.getSelectedItemPosition()==0){
            Toast.makeText(getApplicationContext(), "Please select frequency", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            startAlarm(myAlarmDate);
        }

        medication_info m = new medication_info(title, amount, startdate, time, enddate,
                medicationTypeName, frequencyName, hourchange, minchange, alarmID);
        db.collection("users").document(currentFirebaseUser.getUid()).collection("New Medications")
                .document(medication_info.getId()).update("Medication", m.getMedication(),
                "InventoryMeds", m.getInventoryMeds(), "StartDate", m.getStartDate(),
                "Time", m.getTime(), "EndDate", m.getEndDate(), "FrequencyName", m.getFrequencyName(),
                "MedicineTypeName", m.getMedicineTypeName(), "Hour", m.getHour(), "Minute", m.getMinute(), "AlarmID", m.getAlarmID()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                        Toast.makeText(edit_delete_medications.this, "Measurement Alarm Changed", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(edit_delete_medications.this, home_page.class));
                        finish();
                    }
                });*/
    }

    public Date getDateFromString(String dateToSave) {
        try {
            Date date = format.parse(dateToSave);
            return date ;
        } catch (ParseException e){
            return null ;
        }
    }
    public void edmed_To_home(View view) {
        Intent intent = new Intent(edit_delete_medications.this, home_page.class);
        startActivity(intent);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay , int minute) {
        c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        hourchange = hourOfDay;
        minchange = minute;
        updateTimeText(c);
    }

    private void startAlarm(Calendar c)
    {
        int dynYear, dynMonth, dynDay, dynHour , dynMin;
        myAlarmDate = Calendar.getInstance();
        getData();
        getHourandMin();
        startdate = getDateFromString(dateFormat.getText().toString());
        String month = (String) android.text.format.DateFormat.format("MM", startdate);
        String day = (String) android.text.format.DateFormat.format("dd", startdate);
        String year = (String) android.text.format.DateFormat.format("yyyy", startdate);
        if (hourchange == 0)
        {
            dynHour = hour;
        }
        else
        {
            dynHour = hourchange;
        }

        if (minchange == 0)
        {
            dynMin = minuteDB;
        }
        else
        {
            dynMin = minchange;
        }
        if (alarmYear == 0)
        {
            dynYear = Integer.parseInt(year);
        }
        else
        {
            dynYear = alarmYear;
        }

        if (alarmMonth == 0)
        {
            dynMonth = Integer.parseInt(month);
        }
        else
        {
            dynMonth = alarmMonth;
        }
        if(alarmDay == 0)
        {
            dynDay = Integer.parseInt(day);
        }
        else
        {
            dynDay = alarmDay;
        }
        Log.d("K", "dynamic" +" " +dynYear +" " + dynMonth +" " + dynDay +" " + dynHour+" " + dynMin);

        Intent intent = new Intent(this, alarmreceiver.class);
        myAlarmDate.setTimeInMillis(System.currentTimeMillis());
        myAlarmDate.set(dynYear, dynMonth-1, dynDay, dynHour, dynMin);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingDB = PendingIntent.getBroadcast(this, alarmIDdb, intent, 0);
        alarmManager.cancel(pendingDB);
        alarmID = new Random().nextInt(1000000);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmID, intent, 0);

        if (myAlarmDate.getTimeInMillis() < System.currentTimeMillis()) {
            Toast.makeText(edit_delete_medications.this, "Set the time and date to the future", Toast.LENGTH_LONG).show();
        }
        else
        {
            getData();
            title = medName.getText().toString().trim();
            amount = medInventory.getText().toString().trim();
            startdate = getDateFromString(dateFormat.getText().toString());
            time = timeButtonEdit.getText().toString().trim();
            dosage = dosageBoxET.getText().toString().trim();
            enddate = getDateFromString(enddatebutton.getText().toString());
            String frequencyName = mySpinnerfrequency.getSelectedItem().toString();
            String medicationTypeName = mySpinnertype.getSelectedItem().toString();

            if (TextUtils.isEmpty(title)) {
                medName.setError("This field is required");
                return;
            }
            else if (dosageBoxET.getText().toString().isEmpty() ){
                dosageBoxET.setError("This field is required");
                return;
            }
            else if (TextUtils.isEmpty(amount)) {
                medInventory.setError("This field is required");
                return;
            }
            else if (Integer.parseInt(dosage) > Integer.parseInt(medInventory.getText().toString()) && typechoice >3){
                dosageBoxET.setError("Dosage should be less than the Inventory");
                return;

            }
            else if (Integer.parseInt(dosage) < 0 ){
                dosageBoxET.setError("Dosage should not be a negative number");
                return;

            }
            else if (Integer.parseInt(medInventory.getText().toString()) < 0){
                medInventory.setError("Dosage should not be a negative number");
                return;
            }
            else if(mySpinnerfrequency.getSelectedItemPosition()==0){
                Toast.makeText(getApplicationContext(), "Please select type", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(mySpinnertype.getSelectedItemPosition()==0){
                Toast.makeText(getApplicationContext(), "Please select frequency", Toast.LENGTH_SHORT).show();
                return;
            }


            medication_info m = new medication_info(title, amount, startdate, time, enddate,
                    medicationTypeName, frequencyName, hourchange, minchange, alarmID, dosage);
            db.collection("users").document(currentFirebaseUser.getUid()).collection("New Medications")
                    .document(medication_info.getId()).update("Medication", m.getMedication(),
                    "InventoryMeds", m.getInventoryMeds(), "StartDate", m.getStartDate(),
                    "Time", m.getTime(), "EndDate", m.getEndDate(), "FrequencyName", m.getFrequencyName(),
                    "MedicineTypeName", m.getMedicineTypeName(), "Hour", m.getHour(), "Minute", m.getMinute(), "AlarmID", m.getAlarmID(), "Dosage", m.getDosage()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void avoid) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), pendingIntent);
                    Toast.makeText(edit_delete_medications.this, "Measurement Alarm Changed", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(edit_delete_medications.this, home_page.class));
                    finish();
                }
            });

        }
    }



    private void updateTimeText(Calendar c)
    {
        String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        timeButtonEdit.setText(timeText);
    }
}