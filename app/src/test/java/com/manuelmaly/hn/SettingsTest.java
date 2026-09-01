package com.manuelmaly.hn;

import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 33)
public class SettingsTest {

    private Context context() {
        return ApplicationProvider.getApplicationContext();
    }

    @Test
    public void defaultNightModeFollowsSystem() {
        assertEquals(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
                Settings.getNightMode(context()));
    }

    @Test
    public void nightModeIsPersisted() {
        Context c = context();
        Settings.setNightMode(c, AppCompatDelegate.MODE_NIGHT_YES);
        assertEquals(AppCompatDelegate.MODE_NIGHT_YES, Settings.getNightMode(c));

        Settings.setNightMode(c, AppCompatDelegate.MODE_NIGHT_NO);
        assertEquals(AppCompatDelegate.MODE_NIGHT_NO, Settings.getNightMode(c));

        Settings.setNightMode(c, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        assertEquals(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
                Settings.getNightMode(c));
    }
}
