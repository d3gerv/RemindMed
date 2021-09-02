package com.example.capstone1;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class new_medications extends AppCompatActivity {
    EditText medication, dosage, inventory;
    Button buttonsavemedication;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId;
    Spinner spinnertypeunit, spinnerfrequencymedication;

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

        medication = findViewById(R.id.medicine_Box);
        dosage = findViewById(R.id.DosageBox);
        inventory = findViewById(R.id.inventoryBox);
        spinnertypeunit = findViewById(R.id.type_spinner_one);
        spinnerfrequencymedication = findViewById(R.id.frequency_spinner_ten);
        buttonsavemedication = findViewById(R.id.save_medication_button);

        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userId = rootAuthen.getCurrentUser().getUid();

        //not sure here
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
                mDisplayDate.setText(date);


                //Spinner mySpinner = (Spinner) findViewById(R.id.type_spinner_one);
                //Spinner mySpinnertwo = (Spinner) findViewById(R.id.frequency_spinner_ten);

                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(new_medications.this,
                        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type));
                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnertypeunit.setAdapter(myAdapter);

                ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(new_medications.this,
                        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.frequency));
                myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerfrequencymedication.setAdapter(myAdapter2);

                timeButton = findViewById(R.id.timeButton);

                buttonsavemedication.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Medication = medication.getText().toString().trim();
                        String Dosage = dosage.getText().toString().trim();
                        String Inventory = inventory.getText().toString().trim();
                        String FrequencyMedication = spinnerfrequencymedication.getSelectedItem().toString().trim();
                        String FrequencyMedicationType = spinnertypeunit.getSelectedItem().toString().trim();
                        String TimeMedication = timeButton.getText().toString().trim();

                        Map<String, Object> user = new HashMap<>();
                        user.put("TimeMedication", TimeMedication);
                        user.put("Medication", Medication);
                        user.put("Dosage", Dosage);
                        user.put("InventoryMeds", Inventory);
                        user.put("FrequencyMedication", FrequencyMedication);
                        user.put("FrequencyMedicationType", FrequencyMedicationType);

                        fstore.collection("users").document(userId).set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(new_medications.this, "New Medication added", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
/*
        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                medication.setText(value.getString("Medication"));
                dosage.setText(value.getString("Dosage"));
                inventory.setText(value.getString("InventoryMeds"));
            }
        });
 */
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

            public void Medication_To_Home(View view) {
                Intent intent = new Intent(new_medications.this, home_page.class);
                startActivity(intent);
            }
        };
    }
}