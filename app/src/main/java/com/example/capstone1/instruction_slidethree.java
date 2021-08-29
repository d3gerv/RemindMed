package com.example.capstone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class instruction_slidethree extends AppCompatActivity {

    private ImageView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_slidethree);

        ImageView button = (ImageView) findViewById(R.id.instruction_three_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openinstruction_slidethree();
            }
        });
    }

    private void openinstruction_slidethree() {
        Intent intent = new Intent(this, home_page.class);
        startActivity(intent);
    }
}