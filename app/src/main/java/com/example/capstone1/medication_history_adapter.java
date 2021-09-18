package com.example.capstone1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class medication_history_adapter extends RecyclerView.Adapter<medication_history_adapter.MyViewHolder> {
    Context context;
    ArrayList<medication_history_info> userArrayList;

    public medication_history_adapter(Context context, ArrayList<medication_history_info> userArrayList)
    {
        this.context =context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public medication_history_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.medication_history, parent, false);
        return new medication_history_adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull medication_history_adapter.MyViewHolder holder, int position) {
        medication_history_info medication_history_info = userArrayList.get(position);
        holder.Medication.setText(medication_history_info.Medication);
        holder.Time.setText(medication_history_info.Time);
        holder.Date.setText(medication_history_info.StartDate);


    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Medication, Time, Date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Medication = itemView.findViewById(R.id.medicationName);
            Time = itemView.findViewById(R.id.medicationTimeTaken);
            Date = itemView.findViewById(R.id.medicationDateTake);
        }
    }
}
