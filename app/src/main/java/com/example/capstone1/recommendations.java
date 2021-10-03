package com.example.capstone1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    String bstext[] = { "Reduce carb" , "Remove fiber", "Drink advisable amount of water", "Maintain body weight",
            "Metformin when blood sugar level is high", "Chemlin for low", "exercise regularly", "get enough sleep"};
    String tempText[] = { "Drink medicine e.g. paracetamol and ibuprofen" , "Have some rest", "Keep hydrated", "eat healthy foods like vegetables and fruits" };
    String sleepText[] = { "eat healthy foods" , "Low fat and oily", "moderate exercise" };
    String pulseText[] = { "Keep hydrated" , "Avoid caffeine", "monitored yung cholesterol levels" };




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
        else if (HmName.equals("Bloodsugar"))
        {
            adapter = new recommendation_Adapter(recommendations.this, bstext);
        }
        else if (HmName.equals("Sleep"))
        {
            adapter = new recommendation_Adapter(recommendations.this, sleepText);
        }
        else if (HmName.equals("Temperature"))
        {
            adapter = new recommendation_Adapter(recommendations.this, tempText);

        }
        else if (HmName.equals("Pulserate"))
        {
            adapter = new recommendation_Adapter(recommendations.this, pulseText);
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
    public void Recommendations_To_Home(View view) {
        Intent intent = new Intent(recommendations.this, home_page.class);
        startActivity(intent);
    }
}