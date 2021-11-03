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

public class setLaterHM extends AppCompatActivity {
    long accounttype ;
    FloatingActionButton profileBtn;
    FirebaseAuth rootAuthen;
    String userId;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_measurement_later);

        rootAuthen = FirebaseAuth.getInstance();
        userId = rootAuthen.getCurrentUser().getUid();
        profileBtn = findViewById(R.id.Profile_page_button_later);
        DocumentReference documentReference = fstore.collection("users").document(userId);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentReference.addSnapshotListener(setLaterHM.this, new EventListener<DocumentSnapshot>() {
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
                                Intent intent = new Intent(setLaterHM.this, user_information.class);
                                startActivity(intent);
                            }
                            else if (accounttype == 2)
                            {
                                Intent intent = new Intent(setLaterHM.this, guestLogout.class);
                                startActivity(intent);
                            }
                        }catch (Exception e){
                            Intent intent = new Intent(setLaterHM.this, user_information.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

    }

    public void later_to_BP (View view){
        Intent intent = new Intent(setLaterHM.this, set_later_blood_pressure.class);
        startActivity(intent);
    }

    public void later_to_Cho (View view){
        Intent intent = new Intent(setLaterHM.this, set_later_cholesterol.class);
        startActivity(intent);
    }

    public void later_to_BS (View view){
        Intent intent = new Intent(setLaterHM.this, set_later_blood_sugar.class);
        startActivity(intent);
    }

    public void later_to_HOS (View view){
        Intent intent = new Intent(setLaterHM.this, set_later_hours_of_sleep.class);
        startActivity(intent);
    }
    public void later_to_pulse (View view){
        Intent intent = new Intent(setLaterHM.this, set_later_pulse_rate.class);
        startActivity(intent);
    }

    public void later_to_Temp (View view){
        Intent intent = new Intent(setLaterHM.this, set_later_temperature.class);
        startActivity(intent);
    }
    public void laterhm_To_home(View view) {
        Intent intent = new Intent(setLaterHM.this, home_page.class);
        startActivity(intent);
    }
    public void laterhm_To_today(View view) {
        Intent intent = new Intent(setLaterHM.this, today_page_recycler.class);
        startActivity(intent);
    }
    public void laterhm_To_history(View view) {
        Intent intent = new Intent(setLaterHM.this, history_page.class);
        startActivity(intent);
    }
    public void laterhm_To_user(View view) {
        Intent intent = new Intent(setLaterHM.this, user_information.class);
        startActivity(intent);
    }

}
