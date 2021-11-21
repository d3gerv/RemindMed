package com.example.capstone1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Faq extends AppCompatActivity {

   // public CardView card1, card2, card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

     /*   card1 = (CardView) findViewById(R.id.FAQ_OCR);
        card2 = (CardView) findViewById(R.id.FAQ_TTS);
        card3 = (CardView) findViewById(R.id.FAQ_type);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Faq.this, OCR_FAQ.class);
                startActivity(intent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Faq.this, TTS_FAQ.class);
                startActivity(intent);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Faq.this, TypeUnit_FAQ.class);
                startActivity(intent);
            }
        });*/

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
}