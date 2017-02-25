package com.chailijun.mtime.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.chailijun.mtime.MtimeApp;

import java.util.Set;
import java.util.TreeSet;

/**
 * Helps with shared preference.
 */
public class SPUtil {

    private static Context context = MtimeApp.getContext();
    private static SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

    public static void save(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public static void save(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public static void saveInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public static void saveSet(String key, Set<String> set){
        sp.edit().putStringSet(key,set).apply();
    }

    public static Set<String> getSet(String key){
        return sp.getStringSet(key,new TreeSet<String>());
    }

//    public static void saveLong(String key,long value){
//        sp.edit().putLong(key,value);
//    }

    public static String get(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

//    public static Long getLong(String key) {
//        return sp.getLong(key, -1);
//    }
    public static String getString(String key) {
        return sp.getString(key, "");
    }
    public static int getInt(String key) {
        return sp.getInt(key, 0);
    }
    public static boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }
    public static boolean getBoolean(String key,boolean def) {
        return sp.getBoolean(key, def);
    }
}
