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
import com.google.firebase.auth.FirebaseAuth;

public class change_password extends AppCompatActivity {
    private Button submitpw;
    private EditText changepass;
    private FirebaseAuth rootAuthen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        submitpw = findViewById(R.id.change_passwordBtn);
        changepass = findViewById(R.id.resetpw);
        rootAuthen = FirebaseAuth.getInstance();

        submitpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ChangePassword = changepass.getText().toString();

                if (TextUtils.isEmpty(ChangePassword))
                {
                    Toast.makeText(change_password.this, "Please write your valid email address first", Toast.LENGTH_SHORT).show();
                }
                else{
                    rootAuthen.sendPasswordResetEmail(ChangePassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(change_password.this, "Please check your Email Account if you want to reset your password", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(change_password.this, home_page.class));
                            }
                            else{
                                String message = task.getException().getMessage();
                                Toast.makeText(change_password.this, "Error occured" + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}