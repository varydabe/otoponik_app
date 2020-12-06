package com.github.otoponik.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.github.otoponik.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import static android.content.ContentValues.TAG;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView dateDisplay, dayDisplay, phDisplay, suhuDisplay, kelembapanDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat, dayFormat, timeFormat;
    private String date, day, time, ph, suhu, kelembapan;
    private TextClock textClock;
    private FirebaseDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        dateDisplay = root.findViewById(R.id.text_tanggal);
        dayDisplay = root.findViewById(R.id.text_hari);
        phDisplay = root.findViewById(R.id.text_ph);
        suhuDisplay = root.findViewById(R.id.text_suhu);
        kelembapanDisplay = root.findViewById(R.id.text_kelembapan);
        textClock = root.findViewById(R.id.text_waktu);

        // Firebase
        database = FirebaseDatabase.getInstance();

        // Display Today Date and Day
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        dayFormat = new SimpleDateFormat("EEEE");
        timeFormat = new SimpleDateFormat("k:mm");
        date = dateFormat.format(calendar.getTime());
        day = dayFormat.format(calendar.getTime());
        time = timeFormat.format(calendar.getTime());
        dateDisplay.setText(date);
        dayDisplay.setText(day);

        // Get current status data from database
        readFromFirebase();

        // Testing purpose
        textClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postDataFirebase();
            }
        });
        return root;
    }

    private void readFromFirebase() {
        // Get database reference
        DatabaseReference statusRef = database.getReference("status");

        // Read from the database when data change
        // Thinking about microcontroller send data every hour.
        statusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Status status = dataSnapshot.getValue(Status.class);
                ph = status.getPh();
                suhu = status.getSuhu();
                kelembapan = status.getKelembapan();

                // Log for testing
                Log.d(TAG, "pH is: " + ph);
                Log.d(TAG, "Suhu is: " + suhu);
                Log.d(TAG, "Kelembapan is: " + kelembapan);

                phDisplay.setText(ph);
                suhuDisplay.setText(suhu + "\u00B0");
                kelembapanDisplay.setText(kelembapan + "%");

                // Every data change, post to firebase (not used)
                //postDataFirebase();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    // Post to database with different reference
    private void postDataFirebase() {
        DatabaseReference riwayatRef = database.getReference(String.valueOf(date) + "/" + String.valueOf(time));

        Status status = new Status(ph, suhu, kelembapan);
        Map<String, Object> status_tumbuhan = status.toMap();

        riwayatRef.setValue(status_tumbuhan);
    }
}

