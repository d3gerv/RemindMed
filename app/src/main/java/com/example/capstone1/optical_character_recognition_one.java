package com.example.capstone1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class optical_character_recognition_one extends AppCompatActivity {
    Button captureImage, saveText;
    ImageView viewImage;
    Uri imageUri;
    TextToSpeech textToSpeech;
    String text;
    int ocrChoice;
    int REQUEST_IMAGE_CAPTURE = 1;
    int REQUEST_IMAGE_COUNT = 3;
    TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optical_character_recognition_one);

        captureImage = (Button) findViewById(R.id.captureCount);
        saveText = (Button) findViewById(R.id.pasteCount);


        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "photo";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try {
                    File image_file = File.createTempFile(fileName, ".jpg", storageDirectory);
                    //currentPhotoPath = image_file.getAbsolutePath();
                    ContentValues values = new ContentValues();
                    //imageUri = FileProvider.getUriForFile(MainActivity.this, "com.example.android.fileprovider", image_file );
                    imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ocrChoice = getIntent().getIntExtra("ocrchoice", 0);
        Log.d("Tag", "henlo"+ ocrChoice);

        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ocrChoice == 1)
                {
                    try {
                        new_medications.inventory.setText(displayText.getText().toString());
                        finish();
                    }catch (Exception e)
                    {
                        Toast.makeText(optical_character_recognition_one.this, "Scan your medicine inventory", Toast.LENGTH_SHORT).show();

                    }

                }
                else if (ocrChoice == 2)
                {
                    try{
                        edit_delete_medications.medName.setText(displayText.getText().toString());
                        finish();
                    }catch (Exception e)
                    {
                        Toast.makeText(optical_character_recognition_one.this, "Scan your medicine inventory", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int rotationDegree = 0;
        displayText = (TextView) findViewById(R.id.medCountTV);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).start(this);

            }
            if (requestCode == 2) {
                requestCode = REQUEST_IMAGE_COUNT;
            }

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                {
                    Uri resultUri = result.getUri();
                    ImageView imageView = findViewById(R.id.imageMedsCount);
                    imageView.setImageURI(resultUri);
                    BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    //create image object to be read by visionmlkit
                    InputImage image = InputImage.fromBitmap(bitmap, rotationDegree);
                    //instance of text recognizer
                    TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
                    //task that processes the image
                    Task<Text> resultText = recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
                        @Override
                        public void onSuccess(Text visionText) {
                            String a = visionText.getText();
                            displayText.setText(a);
                            String b = a.toLowerCase();
                        }
                    }).addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        }

    }


    public void ocr_To_med(View view) {
        Intent intent = new Intent(optical_character_recognition_one.this, new_medications.class);
        startActivity(intent);
    }

}