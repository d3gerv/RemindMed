package com.example.capstone1;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class intake_confirmation extends AppCompatActivity{
    TextView medName, medAmount;
    String title, amount, time, date, enddate;
    int medicationAmount;
    int inventoryInt;
    Button confirm;
    FirebaseFirestore db;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private medication_info  medication_info;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intake_confirmation);
        medication_info = (medication_info) getIntent().getSerializableExtra("medication_info");
        db  = FirebaseFirestore.getInstance();
        medName = findViewById(R.id.medNameConfirmTv);
        medAmount = findViewById(R.id.medInventoryTV);
        confirm = findViewById(R.id.confirm_btn);

        getData();
        setData();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementMedication();
            }
        });

    }

    private void getData()
    {
        if(getIntent().hasExtra("description") && getIntent().hasExtra("pill"))
        {
            title = getIntent().getStringExtra("description");
            amount = getIntent().getStringExtra("pill");
            time = getIntent().getStringExtra("time");
            date = getIntent().getStringExtra("startdate");

        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
    private void setData()
    {
        medName.setText(title);
        medAmount.setText(amount);
    }

    private void decrementMedication()
    {
        getData();
        int inv = Integer.parseInt(amount);
        inv -= 1;
        amount= Integer.toString(inv);

            medication_info m =     new medication_info(title, amount, date, time, enddate);
            db.collection("users").document(currentFirebaseUser.getUid()).collection("New Medications")
                    .document(medication_info.getId()).update("InventoryMeds", m.getInventoryMeds())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            Toast.makeText(intake_confirmation.this, "Confirmed Intake", Toast.LENGTH_LONG);
                        }
                    });
    }
}