package com.example.capstone1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class history_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);
    }
    public void History_To_Home (View view){
        Intent intent = new Intent(history_page.this, home_page.class);
        startActivity(intent);
    }
    public void History_To_Today (View view){
        Intent intent = new Intent(history_page.this, today_page_recycler.class);
        startActivity(intent);
    }
    public void History_To_User (View view){
        Intent intent = new Intent(history_page.this, user_information.class);
        startActivity(intent);
    }
}