package com.example.capstone1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class instruction_slideone extends AppCompatActivity {
    private static final String TAG = "AnonymousAuth";
    FirebaseAuth rootAuthen;
    FirebaseUser currentUser;


    private ImageView button;
    private float x1, x2, y1, y2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_slideone);
        rootAuthen = FirebaseAuth.getInstance();
        currentUser = rootAuthen.getCurrentUser();

        //ImageView button = (ImageView) findViewById(R.id.instructione_one_btn);
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openinstruction_slidetwo();
            }
        });*/
    }

    private void openinstruction_slidetwo() {
        Intent intent = new Intent(this, instruction_slidetwo.class);
        startActivity(intent);
    }
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 >  x2){
                Intent i = new Intent(instruction_slideone.this, instruction_slidetwo.class);
                startActivity(i);
            }
            break;
        }
        return false;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = rootAuthen.getCurrentUser();
        updateUI(currentUser);
    }
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
                        Toast.makeText(instruction_slideone.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);

                    }
                }
            });
        }
    }
}