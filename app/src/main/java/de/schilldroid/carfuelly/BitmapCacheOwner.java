package de.schilldroid.carfuelly;

import android.graphics.Bitmap;

/**
 * Created by Simon on 19.01.2016.
 */
public interface BitmapCacheOwner {
    public void onAddBitmapToCache(Bitmap bitmap, String filepath);
}
