
package com.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * It initialize all session variable.
 */
public class EwpSession {
    private static EwpSession sharedInstance;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private String userId = null;

    private EwpSession() {
        // Set all properties
    }

    public static EwpSession getSharedInstance(Context context) {
        if (sharedInstance == null) {
            sharedInstance = new EwpSession();
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
            editor = preferences.edit();
        }
        return sharedInstance;
    }

    public String getUserId() {
        this.userId = preferences.getString("userId", "");
        return this.userId;
    }

    public void setUserId(String userId) {
        editor.putString("userId", userId);
        editor.commit();
        this.userId = userId;
    }
    public void clearSession() {
        editor.putString("userId", "");
        editor.commit();
        this.userId = "";
    }
  }