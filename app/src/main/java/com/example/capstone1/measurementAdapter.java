package com.example.capstone1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class measurementAdapter extends RecyclerView.Adapter<measurementAdapter.MyViewHolder> {
    Context context;
    ArrayList<measurment_info> userArraylist;

    public measurementAdapter(Context context, ArrayList<measurment_info> userArrayList)
    {
        this.context =context;
        this.userArraylist = userArrayList;
    }


    @NonNull
    @Override
    public measurementAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.history_measurement, parent, false);
        return new measurementAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull measurementAdapter.MyViewHolder holder, int position) {
        measurment_info measurment_info = userArraylist.get(position);
        holder.Record.setText(measurment_info.Record);
        holder.Time.setText(measurment_info.Time);
        holder.Name.setText(measurment_info.Name);
        holder.Date.setText(measurment_info.Date);
    }

    @Override
    public int getItemCount() {
        return userArraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Record, Time, Name, Date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Record = itemView.findViewById(R.id.healthRecord);
            Time = itemView.findViewById(R.id.measurementTime);
            Name = itemView.findViewById(R.id.healthType);
            Date = itemView.findViewById(R.id.dateHistoryMeasurement);

        }


    }
}
