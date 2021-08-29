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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class health_measurements extends AppCompatActivity {
    //EditText bloodpressure, cholesterol, sugar, temperature, heartrate, pulserate, sleep;
    //Button buttonsavehealth;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_measurements);
/*
        bloodpressure = findViewById(R.id.bloodpressureinput);
        cholesterol = findViewById(R.id.cholesterolinput);
        sugar = findViewById(R.id.sugarinput);
        temperature = findViewById(R.id.temperatureinput);
        heartrate = findViewById(R.id.heartinput);
        pulserate = findViewById(R.id.pulseinput);
        sleep = findViewById(R.id.sleepinput);
        buttonsavehealth = findViewById(R.id.btnsavehealth);

        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userId = rootAuthen.getCurrentUser().getUid();

        buttonsavehealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Bloodpressure = bloodpressure.getText().toString().trim();
                String Cholesterol = cholesterol.getText().toString().trim();
                String Sugar = sugar.getText().toString().trim();
                String Temperature = temperature.getText().toString().trim();
                String Heartrate = heartrate.getText().toString().trim();
                String Pulserate = pulserate.getText().toString().trim();
                String Sleep = sleep.getText().toString().trim();

                Map<String,Object> user = new HashMap<>();
                user.put("Bloodpressure",Bloodpressure);
                user.put("Cholesterol",Cholesterol);
                user.put("Sugar",Sugar);
                user.put("Temperature",Temperature);
                user.put("Heartrate",Heartrate);
                user.put("Pulserate",Pulserate);
                user.put("Sleep",Sleep);

                fstore.collection("users").document(userId).set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(health_measurements.this, "Health Measurements added", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                bloodpressure.setText(value.getString("Bloodpressure"));
                cholesterol.setText(value.getString("Cholesterol"));
                sugar.setText(value.getString("Sugar"));
                temperature.setText(value.getString("Temperature"));
                heartrate.setText(value.getString("Heartrate"));
                pulserate.setText(value.getString("Pulserate"));
                sleep.setText(value.getString("Sleep"));

            }
        });

 */
    }
    public void Health_To_Cholesterol (View view){
        Intent intent = new Intent(health_measurements.this, new_measurements_cholesterol.class);
        startActivity(intent);
    }
    public void Health_To_Sugar (View view){
        Intent intent = new Intent(health_measurements.this, new_measurements_bloodsugar.class);
        startActivity(intent);
    }
    public void Health_To_Temperature (View view){
        Intent intent = new Intent(health_measurements.this, new_measurements_temperature.class);
        startActivity(intent);
    }
    public void Health_To_Heartrate (View view){
        Intent intent = new Intent(health_measurements.this, new_measurements_heartrate.class);
        startActivity(intent);
    }
    public void Health_To_Sleep (View view){
        Intent intent = new Intent(health_measurements.this, new_measurements_hours_of_sleep.class);
        startActivity(intent);
    }
    public void Health_To_Pulserate (View view){
        Intent intent = new Intent(health_measurements.this, new_measurements_pulserate.class);
        startActivity(intent);
    }
    public void Health_To_Bloodpressure (View view){
        Intent intent = new Intent(health_measurements.this, new_measurements_bloodpressure.class);
        startActivity(intent);
    }
}