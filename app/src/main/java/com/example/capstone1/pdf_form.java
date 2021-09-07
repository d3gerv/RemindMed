package com.example.capstone1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class pdf_form extends AppCompatActivity {
    private FirebaseFirestore fstore;
    private RecyclerView firestorerecyclerView;
    private FirestoreRecyclerAdapter adapter;
    //FirebaseAuth rootAuthen;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_form);

        firestorerecyclerView = findViewById(R.id.firestore_list);
        fstore = FirebaseFirestore.getInstance();
        //userId = rootAuthen.getCurrentUser().getUid();
        //rootAuthen = FirebaseAuth.getInstance();

        //query
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = fstore.collection("users/").document(currentFirebaseUser.getUid()).collection("New Medications");
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
                holder.list_dosage.setText(model.getDosage());
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
        private TextView list_dosage;
        private TextView list_inventory;

        public MedicationsViewHolder(@NonNull View itemView) {
            super(itemView);

            list_medications = itemView.findViewById(R.id.list_medication);
            list_dosage = itemView.findViewById(R.id.list_dosage);
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
}