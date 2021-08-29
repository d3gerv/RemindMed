package com.example.capstone1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_page extends AppCompatActivity {
    EditText password, emailInput;
    Button buttonlogin;
    FirebaseAuth rootAuthen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        emailInput = findViewById(R.id.emailinput);
        password = findViewById(R.id.passwordinput);
        buttonlogin = findViewById(R.id.loginBtn);
        rootAuthen = FirebaseAuth.getInstance();

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = emailInput.getText().toString().trim();
                String Password = password.getText().toString().trim();


                if (TextUtils.isEmpty(Email)) {
                    emailInput.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    password.setError("Password is required");
                    return;
                }
                if (Password.length() < 6) {
                    password.setError("Password must be at least 6 characters");
                    return;
                }

                rootAuthen.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(login_page.this, "Logged in Succesfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), instruction_slideone.class));
                        }else{
                            Toast.makeText(login_page.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
    }
    public void Log_To_Forgot (View view){
        Intent intent = new Intent(login_page.this, forgot_password.class);
        startActivity(intent);
    }
}