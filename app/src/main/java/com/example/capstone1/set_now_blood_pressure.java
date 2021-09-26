package com.example.capstone1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class set_now_blood_pressure extends AppCompatActivity implements fragment_bp.BloodPressuretxt{

    EditText bloodpressure;
    Button saveBPNow, bpDialog;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId, dateToday, timeToday, time, startdate, enddate;
    Date c = Calendar.getInstance().getTime();
    int choice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_now_blood_pressure);

        bloodpressure = findViewById(R.id.blood_pressure_box);
        saveBPNow = findViewById(R.id.save_set_now_bp);
        bpDialog = findViewById(R.id.bp_set_now);
        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = rootAuthen.getCurrentUser().getUid();


        bpDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBPDialog();
            }
        });

        SimpleDateFormat df = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault());
        dateToday = df.format(c);

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        timeToday = timeFormat.format(c);


        saveBPNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> user =new HashMap<>();
                String Record = bpDialog.getText().toString().trim();
                getData();
                if (choice == 1)
                {
                    user.put("Name", "Bloodpressure");
                    user.put("Record",Record);
                    user.put("Date", startdate);
                    user.put("Time", time);

                }
                else
                {
                    user.put("Name", "Bloodpressure");
                    user.put("Record",Record);
                    user.put("Date", dateToday);
                    user.put("Time", timeToday);
                }

                fstore.collection("users").document(userId).collection("New Health Measurements")
                        .document("Bloodpressure").collection("Bloodpressure")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(set_now_blood_pressure.this, "New Bloodpressure measurement added", Toast.LENGTH_SHORT).show();
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