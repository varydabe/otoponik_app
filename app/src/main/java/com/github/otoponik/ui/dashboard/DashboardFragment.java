package com.github.otoponik.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.otoponik.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private NumberPicker numpicker_hours, numpicker_minutes, numpicker_seconds;
    private Button button_atur;
    private int hour, minute, second;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        numpicker_hours = root.findViewById(R.id.numpicker_hours);
        numpicker_minutes = root.findViewById(R.id.numpicker_minutes);
        numpicker_seconds = root.findViewById(R.id.numpicker_seconds);
        button_atur = root.findViewById(R.id.button_atur);

        initializePicker();

        return root;
    }

    private void initializePicker() {
        numpicker_hours.setMinValue(01);
        numpicker_hours.setMaxValue(24);
        numpicker_hours.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        numpicker_hours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hour = numpicker_hours.getValue();
            }
        });

        numpicker_minutes.setMinValue(00);
        numpicker_minutes.setMaxValue(60);
        numpicker_minutes.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        numpicker_hours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minute = numpicker_minutes.getValue();
            }
        });

        numpicker_seconds.setMinValue(00);
        numpicker_seconds.setMaxValue(60);
        numpicker_seconds.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
        numpicker_seconds.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                second = numpicker_seconds.getValue();
            }
        });
    }
}