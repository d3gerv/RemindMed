package com.example.capstone1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class today_page_recycler extends AppCompatActivity {
    RecyclerView recyclerView1;
    ArrayList<medication_info> myArrayList;
    MyMedicationAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_page_recycler);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView1 = findViewById(R.id.recyclerViewToday);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));


        db = FirebaseFirestore.getInstance();
        myArrayList = new ArrayList<medication_info>();
        myAdapter = new MyMedicationAdapter(today_page_recycler.this, myArrayList);
        recyclerView1.setAdapter(myAdapter);
        EventChangeListener();


        AlertDialog dialog = new AlertDialog.Builder(today_page_recycler.this)
                .setTitle("You have recently recorded a *insert health measurement*")
                .setMessage("Do you want to proceed with the recommendation?")
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(today_page_recycler.this,"Recommendation", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), recommendations.class));
                dialog.dismiss();
            }
        });
    }

    public void Today_To_Home(View view){
        Intent intent = new Intent(today_page_recycler.this, home_page.class);
        startActivity(intent);
    }
    public void Today_To_User(View view){
        Intent intent = new Intent(today_page_recycler.this, user_information.class);
        startActivity(intent);
    }
    public void Today_To_History(View view){
        Intent intent = new Intent(today_page_recycler.this, history_page.class);
        startActivity(intent);
    }

    private void EventChangeListener() {

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        db.document("users/"+currentFirebaseUser.getUid()).collection("New Medications").orderBy("Medication", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(e != null)
                {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Firestore error", e.getMessage());
                    return;
                }

                for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges())
                {
                    if(dc.getType() == DocumentChange.Type.ADDED)
                    {
                        myArrayList.add(dc.getDocument().toObject(medication_info.class));
                    }

                    myAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

            }
        });

    }
}