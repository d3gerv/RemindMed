package com.example.capstone1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableReference;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class history_page extends AppCompatActivity {
    RecyclerView recyclerView1;

    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    TextView firstname, clear;
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
        clear = findViewById(R.id.clearAll_medications);
        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                firstname.setText(value.getString("firstname"));
            }
        });
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
       // progressDialog.setMessage("Fetching Data...");
       // progressDialog.show();

        recyclerView1 = findViewById(R.id.recycleViewHistoryMed);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        myArrayList = new ArrayList<medication_history_info>();
        myAdapter = new medication_history_adapter(history_page.this, myArrayList);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(history_page.this);
                alert.setTitle("Delete")
                        .setMessage("Are you sure you want to clear your history")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.document("users/" + currentFirebaseUser.getUid()).collection("Medication History")
                                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for (QueryDocumentSnapshot snapshot: task.getResult())
                                        {
                                            db.document("users/" + currentFirebaseUser.getUid()).collection("Medication History")
                                                    .document(snapshot.getId()).delete();
                                        }
                                    }
                                });
                                myAdapter.notifyDataSetChanged();
                                Intent intent = new Intent(history_page.this, history_page.class);
                                startActivity(intent);

                            }
                        });
                alert.show();
            }
        });
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