package com.github.otoponik.ui.dashboard;

import java.util.HashMap;
import java.util.Map;


public class WaktuPenyiraman {
    public int hour, minute;

    public WaktuPenyiraman() {
        // empty default constructor, necessary for Firebase to be able to deserialize WaktuPenyiraman
    }

    public WaktuPenyiraman (int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("jam", hour);
        result.put("menit", minute);

        return result;
    }
}
