package com.example.a2048;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenInfo {

    public int widthPixels;
    public int heighPixel;

    public ScreenInfo(int widthPixels, int heighrPixels) {
        this.widthPixels = widthPixels;
        this.heighPixel = heighrPixels;
    }

    public static ScreenInfo getSreenInfo(Context context) {
        DisplayMetrics dm = new DisplayMetrics();

        WindowManager window = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        window.getDefaultDisplay().getMetrics(dm);

        return new ScreenInfo(dm.widthPixels, dm.heightPixels);
    }
}