package com.example.capstone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class instruction_slidethree extends AppCompatActivity {
    private float x1, x2, y1, y2;
    private ImageView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_slidethree);

    }

    private void openhome_page() {
        Intent intent = new Intent(this, home_page.class);
        startActivity(intent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        switch(motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = motionEvent.getX();
                y1 = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = motionEvent.getX();
                y2 = motionEvent.getY();
                if (x1 < x2)
                {
                    Intent intent = new Intent(instruction_slidethree.this, instruction_slidetwo.class);
                    startActivity(intent);
                }
                else if (x1 > x2)
                {
                    Intent intent = new Intent(instruction_slidethree.this, home_page.class);
                    startActivity(intent);
                }
                break;
        }
        return false;
    }
}