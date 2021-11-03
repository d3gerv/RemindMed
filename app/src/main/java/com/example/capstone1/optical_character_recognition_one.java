package com.example.capstone1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.capstone1.environment.Environment;
import com.example.capstone1.utils.FileUtil;
import com.google.mlkit.vision.common.InputImage;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

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
                File storageDirectory = getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES);
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

    @SuppressLint("LongLogTag")
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

                Uri resultUri = result.getUri();
                ImageView imageView = findViewById(R.id.imageMedsCount);
                imageView.setImageURI(resultUri);
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                //create image object to be read by visionmlkit
                InputImage image = InputImage.fromBitmap(bitmap, rotationDegree);


                // TODO make async
                AWSS3ImageUploaderTask awss3ImageUploaderTask = new AWSS3ImageUploaderTask(getApplicationContext(), bitmap);
                awss3ImageUploaderTask.execute();


                //instance of text recognizer
//                    TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
                //task that processes the image
//                Task<Text> resultText = recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
//                    @Override
//                    public void onSuccess(Text visionText) {
//                        String a = visionText.getText();
//                        displayText.setText(a);
//                        String b = a.toLowerCase();
//                    }
//                }).addOnFailureListener(
//                        new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            }
//                        });
            }
        }

    }

    public class AWSS3ImageUploaderTask extends AsyncTask<String, Void, Void> {

        private Context context;

        private final Bitmap bitmap;

        private AmazonS3Client s3Client;

        public AWSS3ImageUploaderTask(Context context, final Bitmap bitmap) {
            this.context = context;
            this.bitmap = bitmap;
        } //·

        @Override
        protected Void doInBackground(String... strings) {
            uploadToAWSAndSendToAPI(bitmap);
            return null;
        }


        private void uploadToAWSAndSendToAPI(final Bitmap bitmap) {
            s3Client = new AmazonS3Client(new BasicAWSCredentials(getString(R.string.aws_access_key), getString(R.string.aws_secret_key)));
            s3Client.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_1));
            TransferUtility transferUtility = new TransferUtility(s3Client, context);
            final String filename = "pills_".concat( UUID.randomUUID().toString() ).concat(".jpg");
            try {
                Log.i("uploadToAWSAndSendToAPI", "uploading...");
                TransferObserver transferObserver = transferUtility.upload(getString(R.string.s3_bucket_name), "pills/" + filename,
                        FileUtil.toFile(bitmap, getApplicationContext().getCacheDir(), filename) );

                Log.i("uploadToAWSAndSendToAPI", "transfer id: " + transferObserver.getId());
                Log.i("uploadToAWSAndSendToAPI", "transfer bytes transferred: " + transferObserver.getBytesTransferred());
                transferObserver.setTransferListener(new TransferListener() {


                    @SuppressLint("LongLogTag")
                    @Override
                    public void onStateChanged(int id, TransferState state) {
                        //Implement the code for handle the file status changed.


                        if (TransferState.COMPLETED == state) {
                            // Handle a completed upload.
                            Log.i("uploadToAWSAndSendToAPI", filename + " uploaded to S3");

                            // Call another rest call

                            SendImageToApiTask sendImageToApiTask = new SendImageToApiTask(context, filename);
                            sendImageToApiTask.execute();
                        }else {
                            Log.i("uploadToAWSAndSendToAPI state", state.name());
                        }
                    }

                    @Override
                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                        float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                        int percentDone = (int)percentDonef;

                        Log.i("onProgressChanged", "ID:" + id + " bytesCurrent: " + bytesCurrent
                                + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
                    }

                    @Override
                    public void onError(int id, Exception exception) {
                        //Implement the code to handle the file upload error.
                        exception.printStackTrace();;
                    }

                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class SendImageToApiTask extends AsyncTask<String, Void, Void> {

        private Context context;

        private final String filename;

        private AmazonS3Client s3Client;

        private static final String countString = "Medicine Count: ";

        public SendImageToApiTask(Context context, final String filename) {
            this.context = context;
            this.filename = filename;
        } //·

        @Override
        protected Void doInBackground(String... strings) {
            try {
                sendImageToApi();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void sendImageToApi() throws JSONException {
            String postUrl = Environment.ROOT_PATH + "v1/count_pills";
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("filename", filename);

            // TODO
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, jsonObject,
                    (Response.Listener<JSONObject>) response -> {
                        Log.i("sendImageToApi", response.toString());
                        try {
                            final int count = response.getInt("count");
                            final String fileUrl = response.getString("file_url");
                            Log.i("fileUrl", fileUrl);

                            displayText.setText( countString.concat(String.valueOf(count)) );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, Throwable::printStackTrace);

            requestQueue.add(jsonObjectRequest);
        }


    }

    public void ocr_To_med(View view) {
        Intent intent = new Intent(optical_character_recognition_one.this, new_medications.class);
        startActivity(intent);
    }

}