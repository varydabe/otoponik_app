package com.github.otoponik.ui.notifications;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;  // jangan impor java.icu.utils.Calendar


public class DatePickerFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Gunakan waktu sekarang sebagai waktu default
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Activity perlu mengimplement listener ini
        DatePickerDialog.OnDateSetListener listener = (DatePickerDialog.OnDateSetListener) getActivity();
        // Buat TimePickerDialog dan panggil return
        return new DatePickerDialog(getActivity(), listener, year, month, day);
    }
}