package com.au.cit.handbook.singleton;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsManager {

    private static SharedPrefsManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPrefsManager(Context context) {
        sharedPreferences = context.getSharedPreferences(
                "SharedPrefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedPrefsManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefsManager(context.getApplicationContext());
        }
        return instance;
    }

    public SharedPreferences sharedPreferences() {
        return sharedPreferences;
    }

    public SharedPreferences.Editor editor() {
        return editor;
    }
}
