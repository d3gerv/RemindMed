package com.example.capstone1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Guest_FAQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_faq);
    }
    public void GuestFAQ_Faq(View view) {
        Intent intent = new Intent(Guest_FAQ.this, Faq.class);
        startActivity(intent);
    }
}