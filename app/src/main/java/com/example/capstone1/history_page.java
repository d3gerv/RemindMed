package com.example.capstone1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class history_page extends AppCompatActivity {
    RecyclerView recyclerView1;

    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    TextView firstname;
    FirebaseAuth rootAuthen;
    String userId;

    ArrayList<medication_history_info> myArrayList;
    medication_history_adapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);
        firstname = findViewById(R.id.firstnameview);
        rootAuthen = FirebaseAuth.getInstance();
        userId = rootAuthen.getCurrentUser().getUid();
        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                firstname.setText(value.getString("firstname"));
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView1 = findViewById(R.id.recycleViewHistoryMed);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        myArrayList = new ArrayList<medication_history_info>();
        myAdapter = new medication_history_adapter(history_page.this, myArrayList);

        recyclerView1.setAdapter(myAdapter);
        EventChangeListener();

    }

    public void History_To_Home(View view) {
        Intent intent = new Intent(history_page.this, home_page.class);
        startActivity(intent);
    }

    public void History_To_Today(View view) {
        Intent intent = new Intent(history_page.this, today_page_recycler.class);
        startActivity(intent);
    }

    public void History_To_User(View view) {
        Intent intent = new Intent(history_page.this, user_information.class);
        startActivity(intent);
    }
    public void medHistory_to_measurement(View view){
        Intent intent = new Intent(history_page.this, history_for_measurements.class);
        startActivity(intent);
    }

    private void EventChangeListener() {

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        db.document("users/" + currentFirebaseUser.getUid()).collection("Medication History").orderBy("StartDate", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Firestore error", e.getMessage());
                    return;
                }

                for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        myArrayList.add(dc.getDocument().toObject(medication_history_info.class));
                    }

                    myAdapter.notifyDataSetChanged();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

            }
        });

    }
}