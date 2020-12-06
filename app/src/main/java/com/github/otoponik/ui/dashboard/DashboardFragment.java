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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.github.otoponik.R;
import com.github.otoponik.ui.home.Status;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Map;


public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private NumberPicker numpicker_hours, numpicker_minutes, numpicker_seconds;
    private Button button_atur;
    private TextView waktu_set;
    public int hour, minute, second;
    private String jam, menit, detik;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        waktu_set = root.findViewById(R.id.text_waktu_set);
        numpicker_hours = root.findViewById(R.id.numpicker_hours);
        numpicker_minutes = root.findViewById(R.id.numpicker_minutes);
        numpicker_seconds = root.findViewById(R.id.numpicker_seconds);
        button_atur = root.findViewById(R.id.button_atur);

        initializePicker();

        button_atur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = numpicker_hours.getValue();
                minute = numpicker_minutes.getValue();
                second = numpicker_seconds.getValue();

                jam = String.format("%02d", hour);
                menit = String.format("%02d", minute);
                detik =  String.format("%02d", second);

                // Testing purpose
                Log.d("Jam : ", jam);
                Log.d("Menit : ", menit);
                Log.d("Detik : ", detik);

                waktu_set.setText("Tanaman akan disiram pada: " + jam + ":" + menit + ":" + detik);

                // Write to database WaktuPenyiraman
                writeToFirebase();
            }
        });

        return root;
    }
    private void initializePicker() {
        numpicker_hours.setMinValue(00);
        numpicker_hours.setMaxValue(24);
        numpicker_hours.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
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

        numpicker_seconds.setMinValue(00);
        numpicker_seconds.setMaxValue(60);
        numpicker_seconds.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });
    }

    private void writeToFirebase() {
        // Write waktu penyiraman data to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference timeRef = database.getReference("waktuPenyiraman");

        WaktuPenyiraman waktu_penyiraman = new WaktuPenyiraman(hour, minute, second);
        Map<String, Object> waktuPenyiraman = waktu_penyiraman.toMap();

        timeRef.setValue(waktuPenyiraman);

        // Write status data for testing
        // DatabaseReference statusRef = database.getReference("status");
        //Status status = new Status(String.valueOf(hour), String.valueOf(minute), String.valueOf(second));
        //Map<String, Object> status_tumbuhan = status.toMap();

        //statusRef.setValue(status_tumbuhan);
    }
}