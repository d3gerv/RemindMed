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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class myHomeAdapaterMeasurement extends RecyclerView.Adapter<myHomeAdapaterMeasurement.MyViewHolder>{
    Context context;
    ArrayList<measurement_info_today> userArrayList;

    public myHomeAdapaterMeasurement(Context context, ArrayList<measurement_info_today> userArrayList)
    {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.measurement_edit_delete_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        measurement_info_today measurement = userArrayList.get(position);
        DateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy");
        String strStart = dateFormat.format(measurement.StartDate);
        String strEnd = dateFormat.format(measurement.EndDate);
        holder.Time.setText(measurement.Time);
        holder.Startdate.setText(strStart);
        holder.Enddate.setText(strEnd);
        holder.Measurement.setText(measurement.HMName);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                measurement_info_today measurement = userArrayList.get(position);
                Intent intent;
                if(measurement.HMName.equals("Bloodpressure"))
                {
                    intent = new Intent(context, set_later_blood_pressure.class);
                    intent.putExtra("Time", measurement.Time);
                    intent.putExtra("Date", strStart);
                    intent.putExtra("EndDate", strEnd);
                    intent.putExtra("fromToday", 1);
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Measurement, Time, Startdate, Enddate;
        Button edit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Measurement = itemView.findViewById(R.id.HMname);
            Time = itemView.findViewById(R.id.timeCardMeasure);
            Startdate = itemView.findViewById(R.id.startdateCardMeasure);
            Enddate = itemView.findViewById(R.id.endDatecardMeasure);
            edit = itemView.findViewById(R.id.editHealthM);

        }
    }
}
