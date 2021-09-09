package com.example.capstone1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class today_page_recycler extends AppCompatActivity {
    // private com.applandeo.materialcalendarview.CalendarView calendarView;
    //  private List<EventDay> mEventDays = new ArrayList<>();
    // FirebaseFirestore dbroot;
    TextView test ;

    RecyclerView recyclerView1;
    ArrayList<medication_info> myArrayList;
    MyMedicationAdapter myAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    CalendarView calendarView;
    String selectedDate, test1;

    String date = " ";
    public static final String RESULT = "result";
    public static final String EVENT = "event";


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

        calendarView = findViewById(R.id.calendarViewTD);
        test = findViewById(R.id.textViewTest);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month += 1;
                date = dayOfMonth + "/" + month + "/" + year;
                Log.d("Calendar", "Selected day change " + date);
                selectedDate = Integer.toString(dayOfMonth) + "/" + month + "/" + year;
                recyclerView1.setAdapter(myAdapter);


            }


        });
        EventChangeListener();

        /*dbroot = FirebaseFirestore.getInstance();        test = findViewById(R.id.textViewTest);

        calendarView =  findViewById(R.id.calendarViewNew);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
            }
        });*/
    }


    public void Today_To_Home(View view) {
        Intent intent = new Intent(today_page_recycler.this, home_page.class);
        startActivity(intent);
    }

    public void Today_To_User(View view) {
        Intent intent = new Intent(today_page_recycler.this, user_information.class);
        startActivity(intent);
    }

    public void Today_To_History(View view) {
        Intent intent = new Intent(today_page_recycler.this, history_page.class);
        startActivity(intent);
    }

    private void EventChangeListener() {

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference documentReference = db.document("users/"+currentFirebaseUser.getUid()).collection("New Medications").document(currentFirebaseUser.getUid());

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                } else {
                    Toast.makeText(getApplicationContext(), "Can't find Data", Toast.LENGTH_LONG);

                }

            }
        });


        db.document("users/" + currentFirebaseUser.getUid()).collection("New Medications").orderBy("Medication", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                        myArrayList.add(dc.getDocument().toObject(medication_info.class));
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
    /*public void fetchData()
    {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DocumentReference documentReference = dbroot.document("users/"+currentFirebaseUser.getUid()).collection("New Medications").document(currentFirebaseUser.getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    test.setText(documentSnapshot.getString("StartDate"));
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Can't find Data", Toast.LENGTH_LONG);

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to Fetch Data", Toast.LENGTH_LONG);
            }
        });*/

