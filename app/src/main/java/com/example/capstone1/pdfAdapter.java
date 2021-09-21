package com.example.capstone1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class pdfAdapter extends RecyclerView.Adapter<pdfAdapter.MyViewHolder> {
    Context context;
    ArrayList<measurment_info> userArrayList;

    public pdfAdapter(Context context, ArrayList<measurment_info> userArrayList)
    {
        this.context =context;
        this.userArrayList = userArrayList;
    }
    @NonNull
    @Override
    public pdfAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.table_layout_history, parent, false);
        return new pdfAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull pdfAdapter.MyViewHolder holder, int position) {
        measurment_info measurment_info = userArrayList.get(position);
        holder.Record.setText(measurment_info.Record);
        holder.Time.setText(measurment_info.Time);
        holder.Date.setText(measurment_info.Date);

    }

    @Override
    public int getItemCount() {
        return  userArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Record, Time,  Date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Record = itemView.findViewById(R.id.measurementhistoryPDF);
            Time = itemView.findViewById(R.id.timehisoryPDF);
            Date = itemView.findViewById(R.id.datehistoryPDF);
        }
    }
}
