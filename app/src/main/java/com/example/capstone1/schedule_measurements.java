package com.example.capstone1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class schedule_measurements extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_measurements);
    }

    public void set_later(View view) {
        Intent intent = new Intent(this, setLaterHM.class);
        startActivity(intent);
    }

    public void set_now(View view) {
        Intent intent = new Intent(this, health_measurements.class);
        startActivity(intent);
    }
}