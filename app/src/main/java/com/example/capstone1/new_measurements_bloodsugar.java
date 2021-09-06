package com.example.capstone1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

public class new_measurements_bloodsugar extends AppCompatActivity {
    EditText sugar;
    Button buttonsavesugar;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId;
    Spinner spinnerbs;

    Button timeButtonbs;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_measurements_bloodsugar);

        sugar = findViewById(R.id.blood_sugar_level);
        buttonsavesugar = findViewById(R.id.btnsavesugaredit);
        spinnerbs = findViewById(R.id.frequency_spinner_three);

        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userId = rootAuthen.getCurrentUser().getUid();

        //Spinner my_spinner = (Spinner) findViewById(R.id.frequency_spinner_three);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(new_measurements_bloodsugar.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.frequency));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerbs.setAdapter(myAdapter2);

        timeButtonbs = findViewById(R.id.time_btn_one);


        buttonsavesugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Sugar = sugar.getText().toString().trim();
                String Frequency = spinnerbs.getSelectedItem().toString().trim();
                //String TimeBloodSugar = timeButtonbs.getText().toString().trim();

                Map<String, Object> user = new HashMap<>();
                //user.put("TimeBloodSugar",TimeBloodSugar);
                user.put("Sugar", Sugar);
                user.put("FrequencyBloodSgr",Frequency);

                fstore.collection("users").document(userId).collection("New Health Measurements")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(new_measurements_bloodsugar.this, "New BloodSugar measurement added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Log.d(TAG,"onSuccess: failed");
                            }
                        });

/*
                fstore.collection("users").document(userId).collection("New Health Measurements").document("Bloodsugar").set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(new_measurements_bloodsugar.this, "New blood sugar measurement added", Toast.LENGTH_SHORT).show();
                    }
                });

 */
            }
        });


/*
        DocumentReference documentReference = fstore.collection("users").document(userId).collection("New Health Measurements").document("Bloodsugar");
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                sugar.setText(value.getString("Sugar"));

            }
        });

 */
    }

    //added spinner and timePicker

    public void BloodSugar_To_Health (View view){
        Intent intent = new Intent(new_measurements_bloodsugar.this, health_measurements.class);
        startActivity(intent);

    }

    public void popTimePicker (View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
                timeButtonbs.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Set Time");
        timePickerDialog.show();
    }
}