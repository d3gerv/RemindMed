package com.example.capstone1;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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

public class set_now_blood_pressure extends AppCompatActivity implements fragment_bp.BloodPressuretxt{

    EditText bloodpressure;
    Button saveBPNow, bpDialog, cancelBP;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId, dateToday, timeToday, time, startdate, enddate, sysString, diaString;
    int sysInt, diaInt, pulseInt;
    Date c = Calendar.getInstance().getTime();
    int choice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_now_blood_pressure);


        saveBPNow = findViewById(R.id.save_set_now_bp);
        bpDialog = findViewById(R.id.bp_set_now);
        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = rootAuthen.getCurrentUser().getUid();
        cancelBP = findViewById(R.id.cancel_set_now_bp);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel
                    ("abnormalbp", "abnormalbp", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }



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

                if(sysInt >= 140 && diaInt >= 90 || sysInt <= 90 && diaInt <= 60)
                {
                    NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)
                            new NotificationCompat.Builder(set_now_blood_pressure.this, "abnormalbp");
                    mBuilder.setSmallIcon(R.drawable.ic_launcher_background);
                    mBuilder.setContentTitle("Abnormal Measurement");
                    mBuilder.setContentText("You have recently recorded an abnormal measurement");
                    mBuilder.setAutoCancel(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(set_now_blood_pressure.this);
                    notificationManager.notify(7, mBuilder.build());

                    AlertDialog.Builder aBuilder = new AlertDialog.Builder(set_now_blood_pressure.this);
                    aBuilder.setCancelable(true);
                    aBuilder.setTitle("Abnormal Measurement");
                    aBuilder.setMessage("You have recently recorded an abnormal measurement for your blood pressure click ok to see some recommendations to normalize it");

                    aBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });

                    aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(set_now_blood_pressure.this, recommendations.class);
                            intent.putExtra("description", "Bloodpressure");
                            startActivity(intent);
                        }
                    });

                    aBuilder.show();


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

        cancelBP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test", "check vals" + sysInt +" " + diaInt);


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
    public void applyBPtext(String bp, int sys, int dia, int pulse) {
        bpDialog.setText(bp);
        sysInt = sys;
        diaInt = dia;
        pulseInt = pulse;

    }
    public void nowbp_To_hm(View view) {
        Intent intent = new Intent(set_now_blood_pressure.this, health_measurements.class);
        startActivity(intent);
    }
    public void nowbp_To_hm1(View view) {
        Intent intent = new Intent(set_now_blood_pressure.this, health_measurements.class);
        startActivity(intent);
    }

}