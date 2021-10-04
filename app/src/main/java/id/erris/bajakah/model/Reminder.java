package id.erris.bajakah.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;

public class Reminder {
    public int id;
    public String keteranganId;
    public String keteranganEn;
    public Time waktu;
    public boolean sunday;
    public boolean monday;
    public boolean tuesday;
    public boolean wednesday;
    public boolean thursday;
    public boolean friday;
    public boolean saturday;

    public Reminder(int id, String keteranganId, String keteranganEn, Time waktu, boolean sunday, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday) {
        this.id = id;
        this.keteranganId = keteranganId;
        this.keteranganEn = keteranganEn;
        this.waktu = waktu;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeteranganId() {
        return keteranganId;
    }

    public void setKeteranganId(String keteranganId) {
        this.keteranganId = keteranganId;
    }

    public String getKeteranganEn() {
        return keteranganEn;
    }

    public void setKeteranganEn(String keteranganEn) {
        this.keteranganEn = keteranganEn;
    }

    public Time getWaktu() {
        return waktu;
    }

    public void setWaktu(Time waktu) {
        this.waktu = waktu;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }
}
