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

public class schedule_measurements extends AppCompatActivity {
    FloatingActionButton profileBtn;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    FirebaseAuth rootAuthen;
    String userId;
    long accounttype ;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_measurements);
        rootAuthen = FirebaseAuth.getInstance();
        userId = rootAuthen.getCurrentUser().getUid();
        profileBtn = findViewById(R.id.Profile_page_button);

        DocumentReference documentReference = fstore.collection("users").document(userId);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentReference.addSnapshotListener(schedule_measurements.this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "listen:error", error);
                            return;
                        }
                        try {
                            accounttype = value.getLong("accounttype");

                            Log.d("TAG", "tag: " + accounttype);
                            if (accounttype == 1)
                            {
                                Intent intent = new Intent(schedule_measurements.this, user_information.class);
                                startActivity(intent);
                            }
                            else if (accounttype == 2)
                            {
                                Intent intent = new Intent(schedule_measurements.this, guestLogout.class);
                                startActivity(intent);
                            }
                        }catch (Exception e){
                            Intent intent = new Intent(schedule_measurements.this, guestLogout.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    public void set_later(View view) {
        Intent intent = new Intent(this, setLaterHM.class);
        startActivity(intent);
    }

    public void set_now(View view) {
        Intent intent = new Intent(this, health_measurements.class);
        startActivity(intent);
    }

    public void sched_to_home(View view) {
        Intent intent = new Intent(schedule_measurements.this, home_page.class);
        startActivity(intent);
    }
    public void sched_to_today(View view) {
        Intent intent = new Intent(schedule_measurements.this, today_page_recycler.class);
        startActivity(intent);
    }
    public void sched_to_history(View view) {
        Intent intent = new Intent(schedule_measurements.this, history_page.class);
        startActivity(intent);
    }
    public void Health_To_Home(View view) {
        Intent intent = new Intent(schedule_measurements.this, home_page.class);
        startActivity(intent);
    }

}