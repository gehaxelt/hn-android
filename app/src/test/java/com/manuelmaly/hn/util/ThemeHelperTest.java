package com.manuelmaly.hn.util;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 33)
public class ThemeHelperTest {

    private Context contextWithUiMode(int nightMode) {
        Context base = ApplicationProvider.getApplicationContext();
        Configuration config = new Configuration(base.getResources().getConfiguration());
        config.uiMode = (nightMode & Configuration.UI_MODE_NIGHT_MASK)
                | (config.uiMode & ~Configuration.UI_MODE_NIGHT_MASK);
        return base.createConfigurationContext(config);
    }

    @Test
    public void isNightModeDetectsUiMode() {
        assertFalse(ThemeHelper.isNightMode(
                contextWithUiMode(Configuration.UI_MODE_NIGHT_NO)));
        assertTrue(ThemeHelper.isNightMode(
                contextWithUiMode(Configuration.UI_MODE_NIGHT_YES)));
    }

    @Test
    public void adaptCommentColorIsNoOpInDayMode() {
        Context c = contextWithUiMode(Configuration.UI_MODE_NIGHT_NO);
        int black = Color.BLACK;
        assertEquals(black, ThemeHelper.adaptCommentColor(black, c));
        int gray = Color.rgb(0x5A, 0x5A, 0x5A);
        assertEquals(gray, ThemeHelper.adaptCommentColor(gray, c));
    }

    @Test
    public void adaptCommentColorInvertsDarkColorsInNightMode() {
        Context c = contextWithUiMode(Configuration.UI_MODE_NIGHT_YES);
        assertEquals(0xFFFFFFFF, ThemeHelper.adaptCommentColor(Color.BLACK, c));
        assertEquals(0xFFA5A5A5,
                ThemeHelper.adaptCommentColor(Color.rgb(0x5A, 0x5A, 0x5A), c));
    }

    @Test
    public void adaptCommentColorKeepsLightColorsInNightMode() {
        Context c = contextWithUiMode(Configuration.UI_MODE_NIGHT_YES);
        int lightGray = Color.rgb(0xCE, 0xCE, 0xCE);
        assertEquals(lightGray, ThemeHelper.adaptCommentColor(lightGray, c));
    }
}
