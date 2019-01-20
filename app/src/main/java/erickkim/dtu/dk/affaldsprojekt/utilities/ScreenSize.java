package erickkim.dtu.dk.affaldsprojekt.utilities;

import android.app.Activity;
import android.util.DisplayMetrics;

import java.util.HashMap;
import java.util.Map;
//Dette er taget fra
//https://stackoverflow.com/questions/11252067/how-do-i-get-the-screensize-programmatically-in-android
//Til at bestemme skærmstørrelse

public class ScreenSize {

    public Map<String, Integer> deriveMetrics(Activity activity) {
        try {
            DisplayMetrics metrics = new DisplayMetrics();

            if (activity != null) {
                activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            }

            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("screenWidth", Integer.valueOf(metrics.widthPixels));
            map.put("screenHeight", Integer.valueOf(metrics.heightPixels));
            map.put("screenDensity", Integer.valueOf(metrics.densityDpi));

            return map;
        } catch (Exception err) {
            ; // just use zero values
            return null;
        }
    }
}

