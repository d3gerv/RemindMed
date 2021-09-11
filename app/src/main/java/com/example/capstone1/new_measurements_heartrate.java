package com.example.capstone1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class new_measurements_heartrate extends AppCompatActivity {
    EditText heartrate;
    Button buttonsaveheart;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId;
    Spinner spinnerhr;

    Button timeButtonheart;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_measurements_heartrate);

        heartrate= findViewById(R.id.heart_rate_box);
        buttonsaveheart = findViewById(R.id.btnsaveHeartrate);
        spinnerhr = findViewById(R.id.frequency_spinner_four);

        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userId = rootAuthen.getCurrentUser().getUid();

        //Spinner my_spinner = (Spinner) findViewById(R.id.frequency_spinner_six);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(new_measurements_heartrate.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.frequency));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerhr.setAdapter(myAdapter2);

        timeButtonheart = findViewById(R.id.time_btn_four);

        timeButtonheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(new_measurements_heartrate.this,
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
                                    timeButtonheart.setText(f12Hours.format(date));
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

        buttonsaveheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Heartrate = heartrate.getText().toString().trim();
                //String Frequency = spinnerhr.getSelectedItem().toString().trim();
                String TimeHeartRate = timeButtonheart.getText().toString().trim();


                Map<String,Object> user =new HashMap<>();
                user.put("TimeHeartRate",TimeHeartRate);
                user.put("Heartrate",Heartrate);
                //user.put("FrequencyHeartR",Frequency);

                fstore.collection("users").document(userId).collection("New Health Measurements").document("Heartrate").collection("Heartrate")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(new_measurements_heartrate.this, "New Heartrate measurement added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Log.d(TAG,"onSuccess: failed");
                            }
                        });
/*
                fstore.collection("users").document(userId).collection("New Health Measurements").document("Heartrate").set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(new_measurements_heartrate.this, "New heart rate measurement added", Toast.LENGTH_SHORT).show();
                    }
                });

 */
            }
        });
/*
        DocumentReference documentReference = fstore.collection("users").document(userId).collection("New Health Measurements").document("Heartrate");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                heartrate.setText(value.getString("Heartrate"));

            }
        });

 */
    }

    //added spinner and timePicker
    public void Heartrate_To_Health (View view){
        Intent intent = new Intent(new_measurements_heartrate.this, health_measurements.class);
        startActivity(intent);

    }
}