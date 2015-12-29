package de.schilldroid.carfuelly;

import android.util.Log;

/**
 * Created by Simon on 29.12.2015.
 */
public class Logger {

    public static void log(int logLevel, String msg) {
        log(logLevel, "general", msg);
    }

    public static void log(int logLevel, String tag, String msg) {
        Log.d(tag, msg);
    }

}
