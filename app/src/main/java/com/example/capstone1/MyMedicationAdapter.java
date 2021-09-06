package com.example.capstone1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyMedicationAdapter extends RecyclerView.Adapter<MyMedicationAdapter.MyViewHolder> {
    Context context;
    ArrayList<medication_info> userArrayList;

    public MyMedicationAdapter(Context context, ArrayList<medication_info> userArrayList)
    {
        this.context =context;
        this.userArrayList = userArrayList;
    }
    @NonNull
    @Override
    public MyMedicationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.items_today_medication, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyMedicationAdapter.MyViewHolder holder, int position) {
        medication_info medication_info = userArrayList.get(position);
        holder.InventoryMeds.setText(medication_info.InventoryMeds);
        holder.Medication.setText(medication_info.Medication);
        holder.Dosage.setText(medication_info.Dosage);

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Medication, Dosage, InventoryMeds;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Medication =itemView.findViewById(R.id.medNameView);
            Dosage = itemView.findViewById(R.id.freqView);
            InventoryMeds = itemView.findViewById(R.id.inventoryView);

        }
    }
}
