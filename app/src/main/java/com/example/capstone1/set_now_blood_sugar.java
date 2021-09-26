package com.example.capstone1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class set_now_blood_sugar extends AppCompatActivity {
    EditText bsVal;
    Button saveBSNow;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId, dateToday, timeToday, time, startdate, enddate;
    Date c = Calendar.getInstance().getTime();
    int choice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_now_blood_sugar);

        saveBSNow = findViewById(R.id.save_set_now_bs);
        bsVal = findViewById(R.id.bs_value);
        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = rootAuthen.getCurrentUser().getUid();

        SimpleDateFormat df = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault());
        dateToday = df.format(c);

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        timeToday = timeFormat.format(c);


        saveBSNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Record = bsVal.getText().toString().trim();
                Map<String,Object> user =new HashMap<>();
                getData();
                if (choice == 1)
                {
                    user.put("Name", "Bloodsugar");
                    user.put("Record",Record);
                    user.put("Date", startdate);
                    user.put("Time", time);

                }
                else
                {
                    user.put("Name", "Bloodsugar");
                    user.put("Record",Record);
                    user.put("Date", dateToday);
                    user.put("Time", timeToday);
                }


                Log.d("Calendar", "Selected day change " + timeToday );
                fstore.collection("users").document(userId).collection("New Health Measurements")
                        .document("Bloodsugar").collection("Bloodsugar")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(set_now_blood_sugar.this, "New Bloodsugar measurement added", Toast.LENGTH_SHORT).show();
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

    private void getData() {
        if (getIntent().hasExtra("Time") && getIntent().hasExtra("Date")) {
            time = getIntent().getStringExtra("Time");
            startdate = getIntent().getStringExtra("Date");
            enddate = getIntent().getStringExtra("EndDate");
            choice = getIntent().getIntExtra("fromToday", 1);



        }
    }
}