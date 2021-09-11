package com.example.capstone1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class today_page_recycler extends AppCompatActivity {
    RecyclerView recyclerView1;
    ArrayList<medication_info> myArrayList;
    MyMedicationAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    String date;
    medication_info medication_info;
    int i;
    private CalendarView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_page_recycler);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
       // progressDialog.show();
        calendarView = findViewById(R.id.calendarViewTD);

        recyclerView1 = findViewById(R.id.recyclerViewToday);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        myArrayList = new ArrayList<medication_info>();
        myAdapter = new MyMedicationAdapter(today_page_recycler.this, myArrayList);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month +=1;
                date = month + "/" + dayOfMonth +"/"+year;
                Log.d("Calendar", "Selected day change" + date );
                Log.d("Calendar", "Selected day change" + myArrayList.size() );

                EventChangeListener();
                myArrayList.clear();
                recyclerView1.setAdapter(myAdapter);

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
        db.document("users/"+currentFirebaseUser.getUid()).collection("New Medications")
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
                        if(myArrayList != null)
                        {
                            if(date.equals(m.getStartDate()))
                            {
                                myArrayList.add(m);
                            }
                        }
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