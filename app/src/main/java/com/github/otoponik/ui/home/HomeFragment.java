package com.github.otoponik.ui.home;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.otoponik.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView dateDisplay, dayDisplay, timeDisplay, phDisplay, suhuDisplay, kelembapanDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat, dayFormat, timeFormat;
    private String date, day, time, ph, suhu, kelembapan;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        dateDisplay = root.findViewById(R.id.text_tanggal);
        dayDisplay = root.findViewById(R.id.text_hari);
        timeDisplay = root.findViewById(R.id.text_waktu);
        phDisplay = root.findViewById(R.id.text_ph);
        suhuDisplay = root.findViewById(R.id.text_suhu);
        kelembapanDisplay = root.findViewById(R.id.text_kelembapan);

        // Display Date and Day
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        dayFormat = new SimpleDateFormat("EEEE");
        date = dateFormat.format(calendar.getTime());
        day = dayFormat.format(calendar.getTime());
        dateDisplay.setText(date);
        dayDisplay.setText(day);

        readFromFirebase();
        return root;
    }

    private void readFromFirebase() {
        // Get database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference statusRef = database.getReference("status");


        // Read from the database
        statusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Status status = dataSnapshot.getValue(Status.class);
                ph = String.valueOf(status.getPh());
                suhu = String.valueOf(status.getSuhu());
                kelembapan = String.valueOf(status.getKelembapan());

                Log.d(TAG, "pH is: " + ph);
                Log.d(TAG, "Suhu is: " + suhu);
                Log.d(TAG, "Kelembapan is: " + kelembapan);

                phDisplay.setText(ph);
                suhuDisplay.setText(suhu + "\u00B0");
                kelembapanDisplay.setText(kelembapan + "%");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}

