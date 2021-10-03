package com.example.capstone1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

public class set_now_pulse_rate extends AppCompatActivity {
    EditText tempVal;
    Button saveTempNow;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId, dateToday, timeToday, time, startdate, enddate;
    Date c = Calendar.getInstance().getTime();
    int choice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_now_pulse_rate);
        saveTempNow = findViewById(R.id.save_set_now_pr);
        tempVal = findViewById(R.id.pr_value_set_now);
        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = rootAuthen.getCurrentUser().getUid();

        SimpleDateFormat df = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault());
        dateToday = df.format(c);

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        timeToday = timeFormat.format(c);


        saveTempNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Record = tempVal.getText().toString().trim();
                Map<String,Object> user =new HashMap<>();
                int recordInt = Integer.parseInt(Record);

                getData();
                if (choice == 1)
                {
                    user.put("Name", "Pulserate");
                    user.put("Record",Record);
                    user.put("Date", startdate);
                    user.put("Time", time);

                }
                else
                {
                    user.put("Name", "Pulserate");
                    user.put("Record",Record);
                    user.put("Date", dateToday);
                    user.put("Time", timeToday);
                }

                if(recordInt > 37)
                {
                    NotificationCompat.Builder mBuilder = (NotificationCompat.Builder)
                            new NotificationCompat.Builder(set_now_pulse_rate.this, "abnormalbp");
                    mBuilder.setSmallIcon(R.drawable.ic_launcher_background);
                    mBuilder.setContentTitle("Abnormal Measurement");
                    mBuilder.setContentText("You have recently recorded an abnormal measurement");
                    mBuilder.setAutoCancel(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(set_now_pulse_rate.this);
                    notificationManager.notify(7, mBuilder.build());

                    AlertDialog.Builder aBuilder = new AlertDialog.Builder(set_now_pulse_rate.this);
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
                            Intent intent = new Intent(set_now_pulse_rate.this, recommendations.class);
                            intent.putExtra("description", "Pulserate");
                            startActivity(intent);
                        }
                    });

                    aBuilder.show();
                }

                Log.d("Calendar", "Selected day change " + timeToday );
                fstore.collection("users").document(userId).collection("New Health Measurements")
                        .document("Pulserate").collection("Pulserate")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(set_now_pulse_rate.this, "New Temperature measurement added", Toast.LENGTH_SHORT).show();
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
    public void nowpulse_To_hm(View view) {
        Intent intent = new Intent(set_now_pulse_rate.this, health_measurements.class);
        startActivity(intent);
    }
    public void nowpulse_To_hm1(View view) {
        Intent intent = new Intent(set_now_pulse_rate.this, health_measurements.class);
        startActivity(intent);
    }
}