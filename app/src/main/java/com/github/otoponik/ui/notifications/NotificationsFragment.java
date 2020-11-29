package com.github.otoponik.ui.notifications;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.DatePickerDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.github.otoponik.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private DatePickerDialog datePickerDialog;
    private Button lihatBtn;
    private TextView tanggalDisplay, hariDisplay, bulanDisplay;
    private CardView cardView;
    private SimpleDateFormat monthFormat, dateFormat, dayFormat;
    private String hari, tanggal, bulan;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        hariDisplay = root.findViewById(R.id.text_hari_riwayat);
        tanggalDisplay = root.findViewById(R.id.text_tanggal_riwayat);
        bulanDisplay = root.findViewById(R.id.text_bulan_riwayat);
        cardView = root.findViewById(R.id.card_date);
        lihatBtn = root.findViewById(R.id.lihat);

        dateFormat = new SimpleDateFormat("dd");
        monthFormat = new SimpleDateFormat("MMMM yyyy");
        dayFormat = new SimpleDateFormat("EEEE");

        Calendar calendar = Calendar.getInstance();
        tanggal = dateFormat.format(calendar.getTime());
        bulan = monthFormat.format(calendar.getTime());
        hari = dayFormat.format(calendar.getTime());

        tanggalDisplay.setText(tanggal);
        bulanDisplay.setText(bulan);
        hariDisplay.setText(hari);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(requireContext(), dateSetListener, year, month, day);

                datePickerDialog.getWindow();
                datePickerDialog.show();

                dateSetListener = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        calendar.set(year, month, day);
                        tanggal = dateFormat.format(calendar.getTime());
                        bulan = monthFormat.format(calendar.getTime());
                        hari = dayFormat.format(calendar.getTime());

                        tanggalDisplay.setText(tanggal);
                        bulanDisplay.setText(bulan);
                        hariDisplay.setText(hari);
                    }
                };
            }
        });

        lihatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = true;
                Log.d("clicked", String.valueOf(status));
            }
        });

        return root;
    }
}