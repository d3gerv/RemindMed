package com.example.capstone1;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

public class optical_character_recognition extends AppCompatActivity {
    Button captureImage, saveText;
    ImageView viewImage;
    Uri imageUri;
    String currentPhotoPath;
    int REQUEST_IMAGE_CAPTURE = 1;
    int REQUEST_IMAGE_COUNT = 3;
    TextView displayText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optical_character_recognition);

        captureImage = (Button) findViewById(R.id.captureButton);
        saveText = (Button) findViewById(R.id.buttonTxttoEditTxt);

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

        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_medications.medication.setText(displayText.getText().toString());
                finish();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int rotationDegree = 0;
        displayText = (TextView) findViewById(R.id.medNameTV);
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
                    ImageView imageView = findViewById(R.id.medImageView);
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
        Intent intent = new Intent(optical_character_recognition.this, new_medications.class);
        startActivity(intent);
    }

    }
