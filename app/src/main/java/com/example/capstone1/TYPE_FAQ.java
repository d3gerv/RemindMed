package com.example.capstone1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class TYPE_FAQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_faq);
    }
    public void FaqType_To_User(View view) {
        Intent intent = new Intent(TYPE_FAQ.this, Faq.class);
        startActivity(intent);
    }
}