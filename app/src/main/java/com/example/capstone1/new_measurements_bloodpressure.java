package com.example.capstone1;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class new_measurements_bloodpressure extends AppCompatActivity implements fragment_bp.BloodPressuretxt {
    EditText bloodpressure;
    Button buttonsavesbloodpressure, bpDialog;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId, dateToday;
    private Calendar calendar;
    Date c = Calendar.getInstance().getTime();
    Spinner spinnerbp;

    Button timeButtonbp;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_measurements_bloodpressure);


        buttonsavesbloodpressure = findViewById(R.id.btnsavebloodpressure);
        spinnerbp = findViewById(R.id.frequency_spinner_four);
        bpDialog = findViewById(R.id.buttonBPDialog);

        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userId = rootAuthen.getCurrentUser().getUid();

        //Spinner my_spinner = (Spinner) findViewById(R.id.frequency_spinner_four);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(new_measurements_bloodpressure.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.frequency));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerbp.setAdapter(myAdapter2);

        timeButtonbp = findViewById(R.id.time_btn_one);
        bpDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBPDialog();
            }
        });

        SimpleDateFormat df = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault());
         dateToday = df.format(c);


        timeButtonbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(new_measurements_bloodpressure.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                hour = i;
                                minute = i1;
                                String time = hour + ":" + minute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa"
                                    );
                                    timeButtonbp.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour, minute);
                timePickerDialog.show();
            }
        });

        buttonsavesbloodpressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Record = bpDialog.getText().toString().trim();
                //String Frequency = spinnerbp.getSelectedItem().toString().trim();
                String Time = timeButtonbp.getText().toString().trim();

                Map<String,Object> user =new HashMap<>();
                user.put("Time", Time);
                user.put("Record",Record);
                user.put("Name", "Bloodpressure");
                user.put("Date", dateToday);
                //user.put("FrequencyBloodPrs",Frequency);

               if (TextUtils.isEmpty(Record)) {
                    bloodpressure.setError("This field is required");
                    return;
                }
                if(timeButtonbp.getText().toString().equals("Set Time")){
                    Toast.makeText(getApplicationContext(), "Please select Time", Toast.LENGTH_SHORT).show();
                    return;
                }

                fstore.collection("users").document(userId).collection("New Health Measurements")
                        .document("Bloodpressure").collection("Bloodpressure")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(new_measurements_bloodpressure.this, "New Bloodpressure measurement added", Toast.LENGTH_SHORT).show();
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

    //added spinner and timePicker
    public void Bloodpressure_To_Health (View view){
        Intent intent = new Intent(new_measurements_bloodpressure.this, health_measurements.class);
        startActivity(intent);
    }

    public void openBPDialog()
    {
        fragment_bp fragment_bp = new fragment_bp();
        fragment_bp.show(getSupportFragmentManager(), "BP Dialog");
    }

    @Override
    public void applyBPtext(String bp) {
        bpDialog.setText(bp);
    }
}