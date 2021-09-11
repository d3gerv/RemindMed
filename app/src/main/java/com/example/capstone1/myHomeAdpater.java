package com.example.capstone1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myHomeAdpater extends RecyclerView.Adapter<myHomeAdpater.MyViewHolder> {
    Context context;
    ArrayList<medication_info> userArrayList;
    public myHomeAdpater(Context context, ArrayList<medication_info> userArrayList)
    {
        this.context = context;
        this.userArrayList = userArrayList;
    }
    @NonNull
    @Override
    public myHomeAdpater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.alarms_set, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myHomeAdpater.MyViewHolder holder, int position) {
        medication_info Medication = userArrayList.get(position);
        holder.Medication.setText(Medication.Medication);
        holder.Time.setText(Medication.Time);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, edit_delete_medications.class);
                medication_info medication_info = userArrayList.get(position);
                intent.putExtra("description", medication_info.Medication);
                intent.putExtra("pill", medication_info.InventoryMeds);
                intent.putExtra("startdate", medication_info.StartDate);
                intent.putExtra("time", medication_info.Time);
                intent.putExtra("medication_info", medication_info);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Medication, Time;
        Button edit, delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Medication = itemView.findViewById(R.id.medNameHome);
            Time = itemView.findViewById(R.id.medTimeHome);
            edit = itemView.findViewById(R.id.buttonEdit);
            delete = itemView.findViewById(R.id.buttonDelete);
        }


    }

}
