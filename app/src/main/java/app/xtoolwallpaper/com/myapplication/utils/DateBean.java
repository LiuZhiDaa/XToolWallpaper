package app.xtoolwallpaper.com.myapplication.utils;

import android.util.Log;

public class DateBean {
    private int years;
    private int day;
    private int month;
    private int hours;
    private int minute;

    DateBean(int years, int month, int day, int hours, int minute) {
        this.years = years;
        this.month = month;
        this.day = day;
        this.hours = hours;
        this.minute = minute;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


}
