package com.example.capstone1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class change_name extends AppCompatActivity {
    FirebaseAuth rootAuthen;
    FirebaseFirestore fstore;
    FirebaseUser user;
    Button savebtn;
    EditText editfirstname, editlastname, editemail, mem;
    public static final String TAG = "TAG";
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        Intent data = getIntent();
        String firstname = data.getStringExtra("firstname");
        String lastname = data.getStringExtra("lastname");
        String email = data.getStringExtra("email");

        rootAuthen = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        user = rootAuthen.getCurrentUser();


        editfirstname = findViewById(R.id.editfirstname);
        editlastname = findViewById(R.id.editlastname);
        editemail = findViewById(R.id.editemail);
        savebtn = findViewById(R.id.buttonsave);



        userId = rootAuthen.getCurrentUser().getUid();

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editfirstname.getText().toString().isEmpty() || editlastname.getText().toString().isEmpty() || editemail.getText().toString().isEmpty())
                {
                    Toast.makeText(change_name.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = editemail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DocumentReference docRef = fstore.collection("users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email",email);
                        edited.put("firstname",editfirstname.getText().toString());
                        edited.put("lastname",editlastname.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(change_name.this, "Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),home_page.class));
                                finish();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(change_name.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        Log.d(TAG, "onCreate: " + firstname + " " + lastname);

        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                editemail.setText(value.getString("email"));
                editfirstname.setText(value.getString("firstname"));
                editlastname.setText(value.getString("lastname"));

            }
        });

    }
    public void Change_To_User (View view){
        Intent intent = new Intent(change_name.this, user_information.class);
        startActivity(intent);
    }
}