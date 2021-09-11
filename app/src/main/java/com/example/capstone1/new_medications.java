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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class new_medications extends AppCompatActivity {
    EditText medication, dosage, inventory;
    Button buttonsavemedication;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId;
    Spinner spinnertypeunit, spinnerfrequencymedication;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    FloatingActionButton timeSetBtn;

    //CollectionReference reference = fstore.collection("Users");

    Button timeButtonmedtst, dateformat;
    int hour, minute;
    int year, month, day;


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
        spinnertypeunit = findViewById(R.id.type_spinner_one);
        spinnerfrequencymedication = findViewById(R.id.frequency_spinner_ten);
        buttonsavemedication = findViewById(R.id.save_medication_button);
        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        timeButtonmedtst = findViewById(R.id.timeButtonmed);
        userId = rootAuthen.getCurrentUser().getUid();
        initDatePicker();
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(new_medications.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.frequency));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerfrequencymedication.setAdapter(myAdapter2);

        ArrayAdapter<String> myAdapterTwo = new ArrayAdapter<String>(new_medications.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type));
        myAdapterTwo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertypeunit.setAdapter(myAdapterTwo);

        buttonsavemedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Medication = medication.getText().toString().trim();
                String Dosage = dosage.getText().toString().trim();
                String Inventory = inventory.getText().toString().trim();
                String Time = timeButtonmedtst.getText().toString().trim();
                String StartDate =  dateButton.getText().toString().trim();
                Map<String, Object> user = new HashMap<>();
                user.put("Medication", Medication);
                user.put("Dosage", Dosage);
                user.put("InventoryMeds", Inventory);
                user.put("Time", Time);
                user.put("StartDate", StartDate);



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
                setAlarm();
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

    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, alarmreceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarm Set Succesfully", Toast.LENGTH_SHORT).show();

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

    public void popTimePicker (View view){
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

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return month + "/" + day + "/" + year;
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}


// }
//}