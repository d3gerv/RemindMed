package com.example.capstone1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyMedicationAdapter extends RecyclerView.Adapter<MyMedicationAdapter.MyViewHolder> {
    Context context;
    String dateToday, dateselectedString;
    Date c = Calendar.getInstance().getTime();
    ArrayList<medication_info> userArrayList;
    private  static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO = 1;

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
        DateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy");
        String strDate = dateFormat.format(medication_info.StartDate);
        String strEnd = dateFormat.format(medication_info.EndDate);
        holder.InventoryMeds.setText(medication_info.InventoryMeds);
        holder.Medication.setText(medication_info.Medication);
        holder.Dosage.setText(medication_info.Dosage);
        holder.Time.setText(medication_info.Time);


        Integer inventory = medication_info.getPillStatic();
        Log.d("text", "test" + inventory);

        int intInv = inventory;
        if (Integer.parseInt(medication_info.getInventoryMeds())  > intInv/2 )
        {
            holder.constraintLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }
        else if(Integer.parseInt(medication_info.getInventoryMeds()) <= 5 && Integer.parseInt(medication_info.getInventoryMeds()) > 1 )
        {
            holder.constraintLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.orange));
        }
        else if(Integer.parseInt(medication_info.getInventoryMeds())  == 1)
        {
            holder.constraintLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.red1));
        }

        SimpleDateFormat df = new SimpleDateFormat("M/dd/yyyy", Locale.getDefault());
        dateToday = df.format(c);
        dateselectedString = df.format(today_page_recycler.dateSelected);

        if(dateselectedString.equals(dateToday))
        {

        }
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, intake_confirmation.class);

                medication_info medication_info = userArrayList.get(position);
                intent.putExtra("description", medication_info.Medication);
                intent.putExtra("pill", medication_info.InventoryMeds);
                intent.putExtra("startdate", strDate);
                intent.putExtra("time", medication_info.Time);
                intent.putExtra("enddate", strEnd);
                intent.putExtra("Dosage", medication_info.Dosage);
                intent.putExtra("medication_info", medication_info);
                intent.putExtra("frequency", medication_info.Frequency);
                intent.putExtra("Hour", medication_info.Hour);
                intent.putExtra("Minute", medication_info.Minute);


                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Medication, Time, InventoryMeds, StartDate, Dosage ;
        ConstraintLayout constraintLayout, mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Medication =itemView.findViewById(R.id.medNameView);
            Dosage = itemView.findViewById(R.id.dosageTV);
            InventoryMeds = itemView.findViewById(R.id.inventoryView);
            Time = itemView.findViewById(R.id.timeView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);

        }
    }

}
