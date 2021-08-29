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
public class new_measurements_temperature extends AppCompatActivity {
    EditText temperature;
    Button buttonsavetemp;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId;
    Button timeButton;
    int hour, minute;
    Spinner spinnertemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_measurements_temperature);

        temperature= findViewById(R.id.body_tempt_box);
        buttonsavetemp = findViewById(R.id.btnsavetemp);
        spinnertemp = findViewById(R.id.frequency_spinner_nine);

        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userId = rootAuthen.getCurrentUser().getUid();

        //Spinner my_spinner = (Spinner) findViewById(R.id.frequency_spinner_nine);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(new_measurements_temperature.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.frequency));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertemp.setAdapter(myAdapter2);

        buttonsavetemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Temperature = temperature.getText().toString().trim();
                String Frequency = spinnertemp.getSelectedItem().toString().trim();

                Map<String,Object> user =new HashMap<>();
                user.put("Temperature",Temperature);
                user.put("FrequencyTemp",Frequency);

                fstore.collection("users").document(userId).set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(new_measurements_temperature.this, "New temperature measurement added", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                temperature.setText(value.getString("Temperature"));

            }
        });
    }
    //added spinner and timePicker
    public void Temperature_To_Health (View view){
        Intent intent = new Intent(new_measurements_temperature.this, health_measurements.class);
        startActivity(intent);

        timeButton = findViewById(R.id.timeButton);
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Set Time");
        timePickerDialog.show();
    }
}