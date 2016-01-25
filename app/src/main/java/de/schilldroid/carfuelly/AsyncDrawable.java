package de.schilldroid.carfuelly;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Simon on 14.01.2016.
 */
public class AsyncDrawable extends BitmapDrawable {

    private final WeakReference<LoadBitmapWorkerTask> mBitmapTask;

    public AsyncDrawable(Resources res, Bitmap bitmap, LoadBitmapWorkerTask bitmapTask) {
        super(res, bitmap);
        mBitmapTask = new WeakReference<>(bitmapTask);
    }

    public LoadBitmapWorkerTask getBitmapTask() {
        return mBitmapTask.get();
    }

}
