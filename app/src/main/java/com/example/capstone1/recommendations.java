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
    String bptext[] = { "Reduce excess stress" , "Eat Low salt and low fat food", "Eat high fiber foods",
            "Proper rest and exercise", "If you recorded an SBP of 130 to 150 take Losartan", "If you recorded an SBP of 160 or above take Clonidine" };
    String chotext[] = { "Eat healthy foods" , "Consume high fiber", "Do moderate exercises"};
    String bstext[] = { "Eat recommended carbohydrate levels", "Drink advisable amount of water", "Maintain body weight",
            "Take Metformin when blood sugar level is high", "Exercise regularly", "Get enough sleep"};
    String tempText[] = { "Drink medicine e.g. paracetamol and ibuprofen" , "Have some rest", "Keep hydrated",
            "Eat healthy foods like vegetables and fruits" };
    String sleepText[] = { "Eat healthy foods like vegetables and fruits" , "Consume low fat and oily food", "Moderate exercise",  "Avoid screen time one hour before bed"};
    String pulseText[] = { "Keep hydrated" , "Avoid caffeine", "Have moderate rest"  };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);
        recommendationRecyclerView = findViewById(R.id.recommendationRecycler);
        hmTitle = findViewById(R.id.textViewRecommendations);
        getData();
        openDisclaimer();
        if (HmName.equals("Bloodpressure"))
        {
            adapter= new recommendation_Adapter(recommendations.this, bptext);
            hmTitle.setText("Recommendations for Abnormal Blood Pressure");
        }
        else if (HmName.equals("Bloodsugar"))
        {
            adapter = new recommendation_Adapter(recommendations.this, bstext);
            hmTitle.setText("Recommendations for Abnormal Blood Sugar Levels");

        }
        else if (HmName.equals("Cholesterol"))
        {
            adapter = new recommendation_Adapter(recommendations.this, chotext);
            hmTitle.setText("Recommendations for Abnormal Cholesterol Levels");

        }

        else if (HmName.equals("Sleep"))
        {
            adapter = new recommendation_Adapter(recommendations.this, sleepText);
            hmTitle.setText("Recommendations for Abnormal Sleeping Hours");

        }
        else if (HmName.equals("Temperature"))
        {
            adapter = new recommendation_Adapter(recommendations.this, tempText);
            hmTitle.setText("Recommendations for Abnormal Temperature");


        }
        else if (HmName.equals("Pulserate"))
        {
            adapter = new recommendation_Adapter(recommendations.this, pulseText);
            hmTitle.setText("Recommendations for Abnormal Pulse Rate");

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
                "all information here is only a recommendation and should not be used as a solution for your problems." +
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