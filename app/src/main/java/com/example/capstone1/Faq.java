package com.example.capstone1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Faq extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

    }

    public void Faq_To_User(View view) {
        Intent intent = new Intent(Faq.this, user_information.class);
        startActivity(intent);
    }

    public void FAQOCR_Faq(View view) {
        Intent intent = new Intent(Faq.this, OCR_FAQ.class);
        startActivity(intent);
    }
    public void FAQTTS_Faq(View view) {
        Intent intent = new Intent(Faq.this, FAQTTS.class);
        startActivity(intent);
    }
    public void FAQUnit_Faq(View view) {
        Intent intent = new Intent(Faq.this, TYPE_FAQ.class);
        startActivity(intent);
    }
    public void GuestFAQ_Faq(View view){
        Intent intent = new Intent(Faq.this, Guest_FAQ.class);
        startActivity(intent);
    }
}