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
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


import android.os.Bundle;

public class forgot_password extends AppCompatActivity {

    private Button submitbtn;
    private EditText txtbox_email;
    private FirebaseAuth rootAuthen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        rootAuthen = FirebaseAuth.getInstance();

        submitbtn = (Button) findViewById(R.id.resetbtn);
        txtbox_email = (EditText) findViewById(R.id.resetemail);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = txtbox_email.getText().toString();

                if (TextUtils.isEmpty(userEmail))
                {
                    Toast.makeText(forgot_password.this, "Please write your valid email address first", Toast.LENGTH_SHORT).show();
                }
                else{
                    rootAuthen.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(forgot_password.this, "Please check your Email Account if you want to reset your password", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(forgot_password.this, login_page.class));
                            }
                            else{
                                String message = task.getException().getMessage();
                                Toast.makeText(forgot_password.this, "Error occured" + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

        });

    }
}