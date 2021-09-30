package com.example.capstone1;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recommendation_Adapter extends RecyclerView.Adapter<recommendation_Adapter.MyViewHolder> {

    String text[];
    Activity activity;

    public recommendation_Adapter(Activity activity,
                     String text[])
    {
        this.activity = activity;
        this.text = text;
    }
    @NonNull
    @Override
    public recommendation_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = activity.getLayoutInflater()
                .inflate(
                        R.layout.recommendation_layout,
                        parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recommendation_Adapter.MyViewHolder holder, int position) {
        holder.textView.setText(text[position]);

    }

    @Override
    public int getItemCount() {
        return text.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);


        }
    }
}
