package com.github.otoponik.ui.home;

import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView dateDisplay, dayDisplay, timeDisplay;
    private Calendar calendar;
    private SimpleDateFormat dateFormat, dayFormat, timeFormat;
    private String date, day, time;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        
        // Display Date and Day
        TextView dateDisplay = root.findViewById(R.id.text_tanggal);
        TextView dayDisplay = root.findViewById(R.id.text_hari);
        TextView timeDisplay = root.findViewById(R.id.text_waktu);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        dayFormat = new SimpleDateFormat("EEEE");
        date = dateFormat.format(calendar.getTime());
        day = dayFormat.format(calendar.getTime());
        dateDisplay.setText(date);
        dayDisplay.setText(day);


        
        return root;
    }
}
