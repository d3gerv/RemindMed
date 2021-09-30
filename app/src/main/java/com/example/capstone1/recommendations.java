package com.example.capstone1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class recommendations extends AppCompatActivity {
    String HmName;
    TextView hmTitle;
    RecyclerView recommendationRecyclerView;
    recommendation_Adapter adapter;
    String bptext[] = { "reduce excess stress" , "eat Low salt and low fat food", "Eat high fiber foods", "proper rest and exercise" };
    String chotext[] = { "eat healthy foods" , "Low fat and oily", "moderate exercise" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);
        recommendationRecyclerView = findViewById(R.id.recommendationRecycler);
        getData();
        openDisclaimer();
        if (HmName.equals("Bloodpressure"))
        {
            adapter= new recommendation_Adapter(recommendations.this, bptext);
        }
        else if (HmName.equals("Cholesterol"))
        {
            adapter = new recommendation_Adapter(recommendations.this, chotext);
        }

        recommendationRecyclerView.setLayoutManager(new LinearLayoutManager(recommendations.this));
        recommendationRecyclerView.setAdapter(adapter);
    }

    public void openDisclaimer() {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(recommendations.this);
        aBuilder.setCancelable(true);
        aBuilder.setTitle("Disclaimer");
        aBuilder.setMessage("If you are having health problems " +
                "please head straight to your physician " +
                "all information here is only a recommendation and should not be used to diagnose yourself." +
                "To get a clear diagnosis ask your doctor about what to do." +
                "If you want to continue press ok if not press cancel");

        aBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(recommendations.this, home_page.class);
                startActivity(intent);
            }
        });

        aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        aBuilder.show();
    }

    private void getData() {
        if (getIntent().hasExtra("description")) {
            HmName = getIntent().getStringExtra("description");

        }
    }
}