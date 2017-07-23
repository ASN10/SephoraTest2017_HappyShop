package com.sephora.Utils;

import android.util.Log;

/**
 * Created by nivedhitha.a on 7/21/17.
 */

public class LogUtils {

    private static String LOG_TAG="HappyShop";

    private static boolean enabled;

    public static void i(String tag,String message){
        if(enabled) {
            Log.i(LOG_TAG, tag + " " + message);
        }
    }

    public static void i(String message){
        if(enabled) {
            Log.i(LOG_TAG, message);
        }
    }



    public static void setEnabled(boolean enabled) {
        LogUtils.enabled = enabled;
    }
}
