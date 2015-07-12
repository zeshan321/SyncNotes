package com.aslam.zeshan.syncnotes.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsManager {

    Context con;
    SharedPreferences sharedPreferences;

    public SettingsManager(Context con) {
        this.con = con;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(con);
    }

    public void set(String key, Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (value.getClass().toString()) {
            case "class java.lang.String":
                editor.putString(key, String.valueOf(value));
                break;
            case "class java.lang.Integer":
                editor.putInt(key, (int) value);
                break;
            case "class java.lang.Boolean":
                editor.putBoolean(key, (boolean) value);
                break;
            default:
                System.out.println(key + ": " + value + " is not supported");
                break;
        }

        editor.apply();
    }

    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }
}