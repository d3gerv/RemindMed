package com.example.capstone1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class setLaterHM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_measurement_later);

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
