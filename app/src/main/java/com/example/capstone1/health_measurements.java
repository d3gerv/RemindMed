package com.example.capstone1;

import static com.example.capstone1.home_page.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class health_measurements extends AppCompatActivity {
    //EditText bloodpressure, cholesterol, sugar, temperature, heartrate, pulserate, sleep;
    //Button buttonsavehealth;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    String userId;
    long accounttype ;
    FloatingActionButton profileBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_measurements);
        rootAuthen = FirebaseAuth.getInstance();
        userId = rootAuthen.getCurrentUser().getUid();

        profileBtn = findViewById(R.id.Profile_page_button);
        DocumentReference documentReference = fstore.collection("users").document(userId);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentReference.addSnapshotListener(health_measurements.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "listen:error", error);
                            return;
                        }
                        try {
                            accounttype = value.getLong("accounttype");
                            Log.d("TAG","ID: "+ accounttype);

                            Log.d("TAG", "tag: " + accounttype);
                            if (accounttype == 1)
                            {
                                Intent intent = new Intent(health_measurements.this, user_information.class);
                                startActivity(intent);
                            }
                            else if (accounttype == 2)
                            {
                                Intent intent = new Intent(health_measurements.this, guestLogout.class);
                                startActivity(intent);
                            }
                        }catch (Exception e){
                            Intent intent = new Intent(health_measurements.this, user_information.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

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
        Intent intent = new Intent(health_measurements.this, set_now_cholesterol.class);
        startActivity(intent);
    }
    public void Health_To_Sugar (View view){
        Intent intent = new Intent(health_measurements.this, set_now_blood_sugar.class);
        startActivity(intent);
    }
    public void Health_To_Temperature (View view){
        Intent intent = new Intent(health_measurements.this, set_now_temperature.class);
        startActivity(intent);
    }

    public void Health_To_Sleep (View view){
        Intent intent = new Intent(health_measurements.this, set_now_hours_of_sleep.class);
        startActivity(intent);
    }
    public void Health_To_Pulserate (View view){
        Intent intent = new Intent(health_measurements.this, set_now_pulse_rate.class);
        startActivity(intent);
    }
    public void Health_To_Bloodpressure (View view){
        Intent intent = new Intent(health_measurements.this, set_now_blood_pressure.class);
        startActivity(intent);
    }
    public void Health_To_Home (View view){
        Intent intent = new Intent(health_measurements.this, home_page.class);
        startActivity(intent);
    }
    public void Health_To_User (View view){
        Intent intent = new Intent(health_measurements.this, user_information.class);
        startActivity(intent);
    }
    public void Health_To_Today (View view){
        Intent intent = new Intent(health_measurements.this, today_page_recycler.class);
        startActivity(intent);
    }
    public void Health_To_History (View view){
        Intent intent = new Intent(health_measurements.this, history_page.class);
        startActivity(intent);
    }
}