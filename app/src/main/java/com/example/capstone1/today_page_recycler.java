package com.example.capstone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class today_page_recycler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_page_recycler);
    }
    public void Today_To_Home(View view){
        Intent intent = new Intent(this, home_page.class);
        startActivity(intent);
    }
    public void Today_To_User(View view){
        Intent intent = new Intent(this, user_information.class);
        startActivity(intent);
    }
}