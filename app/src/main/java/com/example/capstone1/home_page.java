package com.example.capstone1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class home_page extends AppCompatActivity {
    FirebaseFirestore fstore;
    FirebaseAuth rootAuthen;
    String userId;
    TextView firstname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        firstname = findViewById(R.id.firstnameview);

        fstore = FirebaseFirestore.getInstance();
        rootAuthen = FirebaseAuth.getInstance();

        userId = rootAuthen.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                firstname.setText(value.getString("firstname"));

            }
        });
    }
    public void Home_To_Health(View view){
        Intent intent = new Intent(this, health_measurements.class);
        startActivity(intent);
    }
    public void Home_To_User(View view){
        Intent intent = new Intent(this, user_information.class);
        startActivity(intent);
    }
}