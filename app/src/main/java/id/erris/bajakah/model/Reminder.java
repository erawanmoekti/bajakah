package id.erris.bajakah.model;

import java.util.UUID;

public class Reminder {
    public UUID id;
    public String keterangan_id;
    public String keterangan_en;
    public String jam;
    public boolean sunday;
    public boolean monday;
    public boolean tuesday;
    public boolean wednesday;
    public boolean thursday;
    public boolean friday;
    public boolean saturday;

    public Reminder(UUID id, String keterangan_id, String keterangan_en, String jam, boolean sunday, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday) {
        this.id = id;
        this.keterangan_id = keterangan_id;
        this.keterangan_en = keterangan_en;
        this.jam = jam;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getKeteranganId() {
        return keterangan_id;
    }

    public void setKeteranganId(String keterangan_id) {
        this.keterangan_id = keterangan_id;
    }

    public String getKeteranganEn() {
        return keterangan_en;
    }

    public void setKeteranganEn(String keterangan_en) {
        this.keterangan_en = keterangan_en;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
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
