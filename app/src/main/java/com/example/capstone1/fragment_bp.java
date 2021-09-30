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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class fragment_bp extends AppCompatDialogFragment {
    private NumberPicker sys ;
    private NumberPicker dia ;
    private NumberPicker pulse ;
    private int sysInt, diaInt, pulseInt;
    private Button bpText;
    private BloodPressuretxt listener;



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
                listener.applyBPtext(bpValues, sysInt, diaInt, pulseInt);
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
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bptxt");
        }
    }

    public interface BloodPressuretxt
    {
        void applyBPtext(String bp, int sys, int dia, int pulse);

    }
}
