package com.example.capstone1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class OCR_FAQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_faq);

    }

    public void FacOcr_To_Faq(View view) {
        Intent intent = new Intent(OCR_FAQ.this, Faq.class);
        startActivity(intent);
    }
}