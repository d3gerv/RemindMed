package com.example.capstone1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class pdf_form extends AppCompatActivity {
    TextView firstname, lastname, medication,list, listtime;
    Button buttonpdf;
    private FirebaseFirestore fstore;
    private RecyclerView firestorerecyclerView;
    private FirestoreRecyclerAdapter adapter;
    FirebaseAuth rootAuthen;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_form);

        buttonpdf = findViewById(R.id.btnSavePDF);
        list = findViewById(R.id.list_medication);
        listtime = findViewById(R.id.list_time);

        firstname = findViewById(R.id.firstview);
        lastname = findViewById(R.id.lastview);
        medication = findViewById(R.id.medicationview);

        firestorerecyclerView = findViewById(R.id.firestore_list);
        fstore = FirebaseFirestore.getInstance();

        //para sa pdf
        buttonpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = findViewById(R.id.list_medication);
                listtime = findViewById(R.id.list_time);
                /*
                for (int i = 0; i< adapter.getItemCount();i++){
                    View view=firestorerecyclerView.getChildAt(i);
                    if(view!=null)
                    {
                        list = findViewById(R.id.list_medication);
                        list.getText().toString().trim();
                    }
                }

                 */


                String Firstname = firstname.getText().toString().trim();
                String Lastname = lastname.getText().toString().trim();
                String Medication = list.getText().toString().trim();
                String Time = listtime.getText().toString().trim();

                //String Mema = firestorerecyclerView.getText.toString.trim();
                /*
                String Gender = spinner.getSelectedItem().toString().trim();
                String Birthyr = birthyr.getText().toString().trim();
                String Height = height.getText().toString().trim();
                String Weight = weight.getText().toString().trim();

                 */

                Map<String,Object> user = new HashMap<>();
                user.put("firstname", Firstname);
                user.put("lastname",Lastname);
                user.put("Medication",Medication);
                user.put("MedicationTime",Time);
                //user.put("Medication", Mema);
                //user.put("gender",Gender);
                //user.put("birthyr",Birthyr);
                //user.put("height",Height);
                //user.put("weight",Weight);

                printPDF();
            }
        //hanggang dito pdf

    });
//end ng method ng pdf


        //displayname
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference documentReference = fstore.collection("users").document(currentFirebaseUser.getUid());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                firstname.setText(value.getString("firstname"));
                lastname.setText(value.getString("lastname"));
                //list.setText(value.getString("Medication"));
            }
        });

        //query
        FirebaseUser currentFirebaseUser1 = FirebaseAuth.getInstance().getCurrentUser();
        Query query = fstore.collection("users/").document(currentFirebaseUser1.getUid()).collection("New Medications");
        //recycler options
        FirestoreRecyclerOptions<MedicationsModel> options = new FirestoreRecyclerOptions.Builder<MedicationsModel>()
                .setQuery(query, MedicationsModel.class)
                .build();

         adapter = new FirestoreRecyclerAdapter<MedicationsModel, MedicationsViewHolder>(options) {
            @NonNull
            @Override
            public MedicationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new MedicationsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MedicationsViewHolder holder, int position, @NonNull MedicationsModel model) {
                holder.list_medications.setText(model.getMedication());
                holder.list_time.setText(model.getTimeMedication());
                holder.list_inventory.setText(model.getInventoryMeds());

            }
        };
         firestorerecyclerView.setHasFixedSize(true);
         firestorerecyclerView.setLayoutManager(new LinearLayoutManager(this));
         firestorerecyclerView.setAdapter(adapter);

        //view holder

    }

    private class MedicationsViewHolder extends RecyclerView.ViewHolder {
        private TextView list_medications;
        private TextView list_time;
        private TextView list_inventory;

        public MedicationsViewHolder(@NonNull View itemView) {
            super(itemView);

            list_medications = itemView.findViewById(R.id.list_medication);
            list_time = itemView.findViewById(R.id.list_time);
            list_inventory = itemView.findViewById(R.id.list_inventory);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    //start ng method ng pdf
    private void printPDF() {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint forLinePaint = new Paint();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(250,350,1).create();
        PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = myPage.getCanvas();

        paint.setTextSize(15.5f);
        paint.setColor(Color.rgb(0,50,250));

        canvas.drawText("RemindMed",20,20,paint);
        paint.setTextSize(8.5f);
        canvas.drawText("Testing lang kung gagana",20,40,paint);
        forLinePaint.setStyle(Paint.Style.STROKE);
        forLinePaint.setPathEffect(new DashPathEffect(new float[]{5,5},0));
        forLinePaint.setStrokeWidth(2);
        canvas.drawLine(20,65,230,65,forLinePaint);

        canvas.drawText("Name: " + firstname.getText(),20,80,paint);
        canvas.drawText("" + lastname.getText(),85,80,paint);
        canvas.drawText("Medication: " + list.getText(),20,90,paint);
        canvas.drawText("Time: " + listtime.getText(),20,100,paint);
            /*
            canvas.drawText("Gender: " + spinner.getSelectedItem(),20,90,paint);
            canvas.drawText("Year of birth: " + birthyr.getText(),20,100,paint);
            canvas.drawText("Height: " + height.getText(),20,110,paint);
            canvas.drawText("Weight: " + weight.getText(),20,120,paint);

             */


        myPdfDocument.finishPage(myPage);
        File file = new File(this.getExternalFilesDir("/"), "Remindmed.pdf");
        //File file = new File(this.getExternalFilesDir("/"), "Remindmed.pdf");


        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        myPdfDocument.close();

    }
}