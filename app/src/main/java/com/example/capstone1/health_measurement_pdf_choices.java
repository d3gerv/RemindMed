package com.example.capstone1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class health_measurement_pdf_choices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_measurement_pdf_choices);


    }

    public void pdfchoice_tobp(View view) {
        Intent intent = new Intent(health_measurement_pdf_choices.this, pdf_form.class);
        startActivity(intent);
    }
    public void pdfchoice_tocho(View view) {
        Intent intent = new Intent(health_measurement_pdf_choices.this, pdf_cholesterol.class);
        startActivity(intent);
    }
    public void pdfchoice_tobs(View view) {
        Intent intent = new Intent(health_measurement_pdf_choices.this, pdf_BloodSugar.class);
        startActivity(intent);
    }
    public void pdfchoice_tosleep(View view) {
        Intent intent = new Intent(health_measurement_pdf_choices.this, pdf_hours_of_sleep.class);
        startActivity(intent);
    }
    public void pdfchoice_topulse(View view) {
        Intent intent = new Intent(health_measurement_pdf_choices.this, pdf_pulserate.class);
        startActivity(intent);
    }
    public void pdfchoice_toyemp(View view) {
        Intent intent = new Intent(health_measurement_pdf_choices.this, pdf_bodyTemp.class);
        startActivity(intent);
    }

}