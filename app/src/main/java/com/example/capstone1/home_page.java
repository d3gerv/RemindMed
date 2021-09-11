package com.example.capstone1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class home_page extends AppCompatActivity {
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    FirebaseAuth rootAuthen;
    String userId;
    TextView firstname;
    myHomeAdpater myAdapter;
    RecyclerView recyclerView;
    ArrayList<medication_info> myArrayList;
    ProgressDialog progressDialog;
    Button addMed, addHM, changeLayout;
    int layout = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        addMed = (Button) findViewById(R.id.add_medications_btn);
        addHM = (Button) findViewById(R.id.add_measurements_btn);
        changeLayout = (Button) findViewById(R.id.changeLayout);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
      //  progressDialog.show();
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
        recyclerView = findViewById(R.id.recyclerViewHome);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myArrayList = new ArrayList<medication_info>();
        myAdapter = new myHomeAdpater(home_page.this, myArrayList);
        EventChangeListener();


        changeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout == 0) {
                    recyclerGone();
                }
                else if(layout == 1)
                {
                    recyclerVisible();
                    recyclerView.setAdapter(myAdapter);

                }
            }
        });

    }

    public void Home_To_Medication(View view) {
        Intent intent = new Intent(this, new_medications.class);
        startActivity(intent);
    }

    public void Home_To_Health(View view) {
        Intent intent = new Intent(this, health_measurements.class);
        startActivity(intent);
    }

    public void Home_To_User(View view) {
        Intent intent = new Intent(this, user_information.class);
        startActivity(intent);
    }

    public void Home_To_History(View view) {
        Intent intent = new Intent(home_page.this, history_page.class);
        startActivity(intent);
    }

    public void Home_To_Today(View view) {
        Intent intent = new Intent(home_page.this, today_page_recycler.class);
        startActivity(intent);
    }

    public void Home_To_Pdf(View view) {
        Intent intent = new Intent(home_page.this, pdf_form.class);
        startActivity(intent);
    }

    private void EventChangeListener() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fstore.document("users/"+currentFirebaseUser.getUid()).collection("New Medications")
                .orderBy("Medication", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                    if(dc.getType() == DocumentChange.Type.ADDED) {
                        medication_info m = dc.getDocument().toObject(medication_info.class);
                        m.setId(dc.getDocument().getId());
                        myArrayList.add(m);

                    }
                    myAdapter.notifyDataSetChanged();
                    if(progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }

    private void recyclerGone()
    {
        recyclerView.setVisibility(View.GONE);
        addMed.setVisibility(View.VISIBLE);
        addHM.setVisibility(View.VISIBLE);
        layout = 1 ;
    }
    private void recyclerVisible()
    {
        recyclerView.setVisibility(View.VISIBLE);
        addMed.setVisibility(View.GONE);
        addHM.setVisibility(View.GONE);
        layout = 0;
    }

}