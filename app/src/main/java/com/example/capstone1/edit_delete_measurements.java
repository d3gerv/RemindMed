package com.example.capstone1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class edit_delete_measurements extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_measurements);

        //added spinner

        Spinner my_spinner = (Spinner) findViewById(R.id.frequency_spinner_one);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(edit_delete_measurements.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.frequency));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        my_spinner.setAdapter(myAdapter2);
    }
}