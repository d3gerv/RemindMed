package com.example.capstone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class instruction_slideone extends AppCompatActivity {

    private ImageView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_slideone);

        ImageView button = (ImageView) findViewById(R.id.instructione_one_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openinstruction_slidetwo();
            }
        });
    }

    private void openinstruction_slidetwo() {
        Intent intent = new Intent(this, instruction_slidetwo.class);
        startActivity(intent);
    }
}