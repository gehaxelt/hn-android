package com.manuelmaly.hn;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import org.androidannotations.annotations.EApplication;

@EApplication
public class App extends Application {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        // Default is MODE_NIGHT_FOLLOW_SYSTEM, so the system light/dark
        // setting is used until the user picks something else
        AppCompatDelegate.setDefaultNightMode(Settings.getNightMode(this));
    }

    public static App getInstance() {
        return mInstance;
    }

}
