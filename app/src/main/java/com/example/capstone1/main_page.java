package com.example.capstone1;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class main_page extends AppCompatActivity {
    private static final String TAG = "AnonymousAuth";

    Button create_account;
    Button login;
    Button guest;
    FirebaseAuth rootAuthen;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        create_account = findViewById(R.id.createacc_button);
        login = (Button) findViewById(R.id.login_button);
        guest = (Button) findViewById(R.id.guest_btn);
        rootAuthen = FirebaseAuth.getInstance();
        currentUser = rootAuthen.getCurrentUser();


        //guest

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Main_To_Create();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openlogin_page();
            }
        });

    }
    public void openlogin_page(){
        Intent intent = new Intent(this, login_page.class);
        startActivity(intent);
    }


    public void Main_To_Create() {
        Intent intent = new Intent(this, create_account.class);
        startActivity(intent);
    }

    public void Main_To_instruction(View view) {
        Intent intent = new Intent(this, instruction_slideone.class);
        startActivity(intent);
    }

/*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = rootAuthen.getCurrentUser();
        updateUI(currentUser);
        }

 */
/*
    private void updateUI(FirebaseUser user) {
        if(user==null){
            rootAuthen.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInAnonymously:success");
                        FirebaseUser user = rootAuthen.getCurrentUser();
                        updateUI(user);

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInAnonymously:failure", task.getException());
                        Toast.makeText(main_page.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);

                    }
                }
            });
        }
    }

 */
}

