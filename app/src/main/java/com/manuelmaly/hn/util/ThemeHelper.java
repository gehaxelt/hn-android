package com.manuelmaly.hn.util;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;

public final class ThemeHelper {

    private ThemeHelper() {
    }

    /**
     * AppCompat applies a forced night mode (MODE_NIGHT_YES / MODE_NIGHT_NO)
     * by overriding the uiMode in the activity's resource configuration, so
     * this also covers the forced modes, not only the system setting.
     */
    public static boolean isNightMode(Context c) {
        return (c.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
    }

    /**
     * Comment colors are parsed from the HTML and are all dark (black to dark
     * gray). On a dark background those would be unreadable, so in night mode
     * any dark color is inverted (RGB flip) which keeps the relative shading.
     */
    public static int adaptCommentColor(int color, Context c) {
        if (!isNightMode(c)) {
            return color;
        }
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        float brightness = (0.299f * r + 0.587f * g + 0.114f * b) / 255f;
        if (brightness < 0.5f) {
            return (color & 0xFF000000) | ((color & 0x00FFFFFF) ^ 0x00FFFFFF);
        }
        return color;
    }
}
