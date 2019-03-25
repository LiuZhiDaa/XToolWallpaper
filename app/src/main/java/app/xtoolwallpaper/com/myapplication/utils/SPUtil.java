package app.xtoolwallpaper.com.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.SharedPreferencesCompat;

import app.xtoolwallpaper.com.myapplication.XApplication;

public class SPUtil {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "data";
    public static final String IS_FIRST = "is_first";
    public static final String CURRENT_TIME = "current_time";
    public static final String IS_SEND_NOTIFICATION = "is_first";

    public static int getId() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(XApplication.APP);
        int pos = sp.getInt(FILE_NAME, 0);
        return pos;
    }

    public static void setId(int time) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(XApplication.APP);
        sp.edit().putInt(FILE_NAME, time).apply();
    }

    public static void setCurrentTime(String datetime) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(XApplication.APP);
        sp.edit().putString(CURRENT_TIME, datetime).apply();
    }

    public static String getCurrentTime() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(XApplication.APP);
        return sp.getString(CURRENT_TIME, "0");
    }

    public static void setOpen(boolean is) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(XApplication.APP);
        sp.edit().putBoolean(IS_FIRST, is).apply();
    }

    public static boolean getOpen() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(XApplication.APP);
        return sp.getBoolean(IS_FIRST, true);
    }

    public static void setIsSendNotification(boolean is) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(XApplication.APP);
        sp.edit().putBoolean(IS_SEND_NOTIFICATION, is).apply();
    }

    public static boolean getIsSendNotification() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(XApplication.APP);
        return sp.getBoolean(IS_SEND_NOTIFICATION, false);
    }
}
