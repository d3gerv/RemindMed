package com.example.capstone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class instruction_slidetwo extends AppCompatActivity {

    private ImageView button;
    private float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_slidetwo);


    }

    private void openinstruction_slidethree() {
        Intent intent = new Intent(this, instruction_slidethree.class);
        startActivity(intent);
    }
    public boolean onTouchEvent(MotionEvent touchEvent)
    {
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2){
                    Intent i = new Intent(instruction_slidetwo.this, instruction_slideone.class);
                    startActivity(i);
                }else if(x1 >  x2){
                    Intent i = new Intent(instruction_slidetwo.this, instruction_slidethree.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
}