package com.example.capstone1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.constraintlayout.widget.ConstraintLayout;

public class fragment_bp extends AppCompatDialogFragment {
    private NumberPicker sys ;
    private NumberPicker dia ;
    private NumberPicker pulse ;
    private int sysInt, diaInt, pulseInt;
    private int pulseID = 0;
    private Button change, bpText;
    private TextView pulsetxt, diatxt, diatxt2;
    private BloodPressuretxt listener;
    private BloodPressuretxtNoPulse listenernoP;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Bloodpressure Values");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.blood_pressure_dialog, null);

        sys = view.findViewById(R.id.sysNP);
        dia = view.findViewById(R.id.diaNP);
        pulse = view.findViewById(R.id.pulseNP);
        pulsetxt = view.findViewById(R.id.pulseTV);
        diatxt = view.findViewById(R.id.diaTV);
        diatxt2 = view.findViewById(R.id.diaTV2);
        change = view.findViewById(R.id.changePulse);


        sys.setMinValue(35);
        sys.setMaxValue(230);
        sys.setValue(120);
        dia.setMinValue(30);
        dia.setMaxValue(135);
        dia.setValue(80);

        pulse.setMinValue(25);
        pulse.setMaxValue(220);
        pulse.setValue(60);

        sysInt = 120;
        diaInt = 80;
        pulseInt = 60;


        sys.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                sysInt = oldVal;
                sysInt = newVal;

            }
        });

        dia.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                diaInt = oldVal;
                diaInt = newVal;
            }
        });

        pulse.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                pulseInt = oldVal;
                pulseInt = newVal;
            }
        });

        builder.setView(view);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String bpValues = sysInt + "/" + diaInt + "/" + pulseInt;
                String bpValuesnoP = sysInt + "/" + diaInt ;
                if (pulseID == 0)
                {
                    listener.applyBPtext(bpValues, sysInt, diaInt, pulseInt);

                }
                else
                {
                    listenernoP.applyBPtextnoP(bpValuesnoP, sysInt, diaInt);
                }
            }
        });
      change.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (pulseID == 0)
              {
                  pulse.setVisibility(View.GONE);
                  pulsetxt.setVisibility(View.GONE);
                  diatxt2.setVisibility(View.VISIBLE);
                  diatxt.setVisibility(View.INVISIBLE);
                  change.setText("With Pulse");
                  pulseID = 1;
              }
              else if(pulseID == 1)
              {
                  pulse.setVisibility(View.VISIBLE);
                  pulsetxt.setVisibility(View.VISIBLE);
                  diatxt2.setVisibility(View.INVISIBLE);
                  diatxt.setVisibility(View.VISIBLE);
                  change.setText("No Pulse");
                  pulseID = 0;
              }

          }
      });
        bpText = view.findViewById(R.id.buttonBPDialog);
        return builder.create();

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (BloodPressuretxt)context;
            listenernoP = (BloodPressuretxtNoPulse)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bptxt");
        }
    }

    public interface BloodPressuretxt
    {
        void applyBPtext(String bp, int sys, int dia, int pulse);

    }

    public  interface  BloodPressuretxtNoPulse
    {
        void applyBPtextnoP(String bp, int sys, int dia);

    }
}
