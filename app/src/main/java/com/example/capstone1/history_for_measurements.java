package com.example.capstone1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class history_for_measurements extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<measurment_info> myArrayList;
    measurementAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    String measurement[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_for_measurements);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        recyclerView =  findViewById(R.id.recyclerHistory);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        myArrayList = new ArrayList<measurment_info>();


        myAdapter = new measurementAdapter(history_for_measurements.this, myArrayList);
        showMeasurementHistory();
        recyclerView.setAdapter(myAdapter);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();






    }

    private void showMeasurementHistory()
    {
        measurement = getResources().getStringArray(R.array.measurements);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        for ( String i : measurement) {
            db.document("users/" + currentFirebaseUser.getUid()).collection("New Health Measurements").document(i).collection(i)
                    .orderBy("Time", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                            myArrayList.add(dc.getDocument().toObject(measurment_info.class));
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

    private void getTypeofMeasurement()
    {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        db.document("users/"+currentFirebaseUser.getUid()).collection("New Health Measurements")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                String data = " ";
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                {
                    types_of_measurements types_of_measurements = documentSnapshot.toObject(types_of_measurements.class);
                    String Bloodpressure = types_of_measurements.getBloodpressure();

                    data += Bloodpressure;
                }
                Log.d("Measurement", "This is your measurement" + data);


            }
        });
    }

}