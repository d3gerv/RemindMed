package com.example.capstone1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class user_information extends AppCompatActivity {
    public static final String TAG = "TAG";
    //public static final String TAG1 = "TAG";
    //public static final String TAG = "TAG";
    EditText gender, birthyr, height, weight;
    Button buttonSave, buttonLogout;
    TextView email, firstname, lastname;
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    String userId;

    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);




        /*
        Intent data = getIntent();
        String Gender = data.getStringExtra("Gender");
        String Birthdate = data.getStringExtra("Birthdate");
        String Height = data.getStringExtra("Height");
        String Weight = data.getStringExtra("Weight");
*/
        email = findViewById(R.id.emailview);
        //firstname = findViewById(R.id.firstview);
        //lastname = findViewById(R.id.lastview);

        spinner = findViewById(R.id.gender_spinner);
        birthyr = findViewById(R.id.editTextbirth);
        height = findViewById(R.id.editTextheight);
        weight = findViewById(R.id.editTextweight);
        buttonSave = findViewById(R.id.btnSave);
        buttonLogout = findViewById(R.id.btnLogout);

        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userId = rootAuthen.getCurrentUser().getUid();

        //added spinner and
        //Spinner mySpinnerone = (Spinner) findViewById(R.id.gender_spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(user_information.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.gender));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Gender = spinner.getSelectedItem().toString().trim();
                String Birthyr = birthyr.getText().toString().trim();
                String Height = height.getText().toString().trim();
                String Weight = weight.getText().toString().trim();

                Map<String,Object> user = new HashMap<>();
                user.put("gender",Gender);
                user.put("birthyr",Birthyr);
                user.put("height",Height);
                user.put("weight",Weight);

                fstore.collection("users").document(userId).set(user, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(user_information.this, "User information added", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

/*
                DocumentReference documentReference = fstore.collection("users").document(userId);
                Map<String,Object> user = new HashMap<>();
                user.put("gender",Gender);
                user.put("birthyr",Birthyr);
                user.put("height",Height);
                user.put("weight",Weight);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG,"onscuess" + userId);
                    }
                });
*/

                /*
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("gender",Gender);
                hashMap.put("birthyr",Birthyr);
                hashMap.put("height",Height);
                hashMap.put("weight",Weight);
                FirebaseFirestore.getInstance().collection("users").document(userId).set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(user_information.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(user_information.this, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                 */

            }
        });


/*
        gender.setText(Gender);
        birthdate.setText(Birthdate);
        gender.setText(Height);
        gender.setText(Weight);
        Log.d(TAG, "onCreate: " + Gender + " " + Birthdate + " " + Height + " " + Weight);
*/
/*
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Gender = gender.getText().toString().trim();
                String Birthdate = birthdate.getText().toString().trim();
                String Height = height.getText().toString().trim();
                String Weight = height.getText().toString().trim();
                if (TextUtils.isEmpty(Gender)) {
                    gender.setError("Gender is required");
                    return;
                }
                if (TextUtils.isEmpty(Birthdate)) {
                    birthdate.setError("Birthdate is required");
                    return;
                }
                if (TextUtils.isEmpty(Height)) {
                    height.setError("Height is required");
                    return;
                }
                if (TextUtils.isEmpty(Weight)) {
                    weight.setError("Confirm Password is required");
                    return;
                }
                rootAuthen.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(user_information.this, "Saved", Toast.LENGTH_SHORT).show();
                            userId = rootAuthen.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userId);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Gender",Gender);
                            user.put("YearofBirth",Birthdate);
                            user.put("Weight",Weight);
                            user.put("Height",Height);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"onSucess: saved " + userId);
                                }
                            });
                    }
                });
            }
        });
*/
        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                email.setText(value.getString("email"));
                //firstname.setText(value.getString("firstname"));
                //lastname.setText(value.getString("lastname"));
                //gender.setText(value.getString("gender"));
                birthyr.setText(value.getString("birthyr"));
                height.setText(value.getString("height"));
                weight.setText(value.getString("weight"));
            }
        });
    }
    public void User_To_Account (View view){
        Intent intent = new Intent(user_information.this, change_name.class);
        startActivity(intent);
    }

    public void Logout (View view){
        Intent intent = new Intent(user_information.this, main_page.class);
        startActivity(intent);

    }
}