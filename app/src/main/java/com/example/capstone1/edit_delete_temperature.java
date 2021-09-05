package com.example.capstone1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class edit_delete_temperature extends AppCompatActivity {

    Button timeButtonEditTemp, dateFormatTemp;
    int hour, minute;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_temperature);

        //added calendar for startButton
        dateFormatTemp = findViewById(R.id.startButtonTemp);
        final Calendar calendar = Calendar.getInstance();
        dateFormatTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get((Calendar.YEAR));
                month = calendar.get((Calendar.MONTH));
                day = calendar.get((Calendar.DAY_OF_MONTH));
                DatePickerDialog datePickerDialog = new DatePickerDialog(edit_delete_temperature.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dateFormatTemp.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        Spinner my_spinner = (Spinner) findViewById(R.id.frequency_spinner_editTemp);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(edit_delete_temperature.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.frequency));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        my_spinner.setAdapter(myAdapter2);

        timeButtonEditTemp = findViewById(R.id.time_btn_editTemp);
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
                timeButtonEditTemp.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };
    }
}
