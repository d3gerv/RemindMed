package com.example.capstone1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class edit_delete_pulserate extends AppCompatActivity {

    Button timeButtonEditPR, dateFormatPR;
    int hour, minute;
    int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_pulserate);

        //added calendar for startButton
        dateFormatPR = findViewById(R.id.startButtonPR);
        final Calendar calendar = Calendar.getInstance();
        dateFormatPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get((Calendar.YEAR));
                month = calendar.get((Calendar.MONTH));
                day = calendar.get((Calendar.DAY_OF_MONTH));
                DatePickerDialog datePickerDialog = new DatePickerDialog(edit_delete_pulserate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dateFormatPR.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        Spinner my_spinner = (Spinner) findViewById(R.id.frequency_spinner_editPR);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(edit_delete_pulserate.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.frequency));
        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        my_spinner.setAdapter(myAdapter2);

        timeButtonEditPR = findViewById(R.id.time_btn_editPR);
        timeButtonEditPR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(edit_delete_pulserate.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                hour = i;
                                minute = i1;
                                String time = hour + ":" + minute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa"
                                    );
                                    timeButtonEditPR.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hour, minute);
                timePickerDialog.show();
            }
        });
    }

}