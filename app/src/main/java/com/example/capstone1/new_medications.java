package com.example.capstone1;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.capstone1.databinding.ActivityNewMedicationsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class new_medications extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    EditText medication, dosage, inventory;
    Button buttonsavemedication;
    FloatingActionButton timeSetBtn, captureImage;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId;
    Uri imageUri;
    String currentPhotoPath;
    int REQUEST_IMAGE_CAPTURE = 1;

    private static final String TAG = "new_medications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medications);
        captureImage = (FloatingActionButton) findViewById(R.id.ocr_btn);
        medication = findViewById(R.id.medicine_Box);
        dosage = findViewById(R.id.DosageBox);
        inventory = findViewById(R.id.inventoryBox);
        timeSetBtn = findViewById(R.id.add_btnTime);
        buttonsavemedication = findViewById(R.id.save_medication_button);
        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = rootAuthen.getCurrentUser().getUid();


        timeSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new TimePickerFragment();
                timepicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        buttonsavemedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Medication = medication.getText().toString().trim();
                String Dosage = dosage.getText().toString().trim();
                String Inventory = inventory.getText().toString().trim();
                Map<String, Object> user = new HashMap<>();
                user.put("Medication", Medication);
                user.put("Dosage", Dosage);
                user.put("InventoryMeds", Inventory);

                fstore.collection("users").document(userId).collection("New Medications")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(new_medications.this, "New Medication added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onSuccess: failed");
                            }
                        });
            }
        });

    }

    public void Medication_To_Home(View view) {
        Intent intent = new Intent(new_medications.this, home_page.class);
        startActivity(intent);
    }
    public void Medication_To_OCR(View view) {
        Intent intent = new Intent(new_medications.this, optical_character_recognition.class);
        startActivity(intent);
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView timeTV = (TextView) findViewById(R.id.timeTV);
        timeTV.setText(hourOfDay + ":" + minute);
    }

 }
