package com.github.otoponik.ui.home;

import java.util.HashMap;
import java.util.Map;

public class Status {
    int ph, suhu, kelembapan;

    public Status() {
        // empty default constructor, necessary for Firebase to be able to deserialize Status
    }

    public Status(int ph, int suhu, int kelembapan) {
        this.ph = ph;
        this.suhu = suhu;
        this.kelembapan = kelembapan;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ph", ph);
        result.put("suhu", suhu);
        result.put("kelembapan", kelembapan);

        return result;
    }

    public int getPh() {
        return ph;
    }

    public int getSuhu() {
        return suhu;
    }

    public int getKelembapan() {
        return kelembapan;
    }

}
