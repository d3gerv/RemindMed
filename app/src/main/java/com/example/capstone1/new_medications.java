package com.example.capstone1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class new_medications extends AppCompatActivity {

    Button timeButton;
    int hour, minute;

    private static final String TAG = "new_medications";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    //added spinner and timePicker

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medications);

        /*not sure here
        mDisplayDate = (TextView) findViewById(R.id.startButton_date);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get((Calendar.YEAR));
                int month = cal.get((Calendar.MONTH));
                int day = cal.get((Calendar.DAY_OF_MONTH));

                DatePickerDialog dialog = new DatePickerDialog(
                        new_medications.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + year + "/" + month + "/" + day);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);*/
        

                Spinner mySpinner = (Spinner) findViewById(R.id.type_spinner_one);
                Spinner mySpinnertwo = (Spinner) findViewById(R.id.frequency_spinner_ten);

                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(new_medications.this,
                        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type));
                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mySpinner.setAdapter(myAdapter);

                ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(new_medications.this,
                        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.frequency));
                myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mySpinnertwo.setAdapter(myAdapter2);

                timeButton = findViewById(R.id.timeButton);
            }


            public void popTimePicker(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        hour = i;
                        minute = i1;
                        timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                    }
                };

                int style = AlertDialog.THEME_HOLO_DARK;

                TimePickerDialog timePickerDialog = new TimePickerDialog(new_medications.this, style, onTimeSetListener, hour, minute, true);

                timePickerDialog.setTitle("Set Time");
                timePickerDialog.show();
            }
        };
