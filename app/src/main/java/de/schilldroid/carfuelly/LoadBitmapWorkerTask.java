package de.schilldroid.carfuelly;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import de.schilldroid.carfuelly.Utils.Consts;
import de.schilldroid.carfuelly.Utils.Logger;
import de.schilldroid.carfuelly.Utils.Tools;

/**
 * Created by Simon on 13.01.2016.
 */
public class LoadBitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {

    private final String mClassName = "LoadBitmapWorkerTask";

    private final WeakReference<ImageView> mImageViewReference;
    private String mSrcFilepath = null;
    private boolean mStarted = false;
    private BitmapCacheOwner mCacheOwner;

    public LoadBitmapWorkerTask(ImageView imageView, String srcFilepath) {
        this(imageView, srcFilepath, null);
    }

    public LoadBitmapWorkerTask(ImageView imageView, String srcFilepath, BitmapCacheOwner cacheOwner) {
        mSrcFilepath = srcFilepath;
        mImageViewReference = new WeakReference<>(imageView);
        mCacheOwner = cacheOwner;
    }

    public String getFilename() {
        return mSrcFilepath;
    }

    public boolean hasStarted() {
        return mStarted;
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Void... params) {
        mStarted = true;
        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "start decoding bitmap '" + mSrcFilepath + "' ...");
        final Bitmap bitmap = BitmapFactory.decodeFile(mSrcFilepath);
        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "finish decoding bitmap '" + mSrcFilepath + "'!!!");
        return bitmap;
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if(isCancelled()) {
            bitmap = null;
        }

        if (mImageViewReference != null && bitmap != null) {
            final ImageView imageView = mImageViewReference.get();
            final LoadBitmapWorkerTask bitmapTask = Tools.getBitmapTask(imageView);
            Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "checking if requirements are met");
            if (this == bitmapTask && imageView != null) {
                Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "applying image to imageview");
                imageView.setImageBitmap(bitmap);
                if(mCacheOwner != null) {
                    mCacheOwner.onAddBitmapToCache(bitmap, mSrcFilepath);
                }
            }
            else {
                boolean iv = (imageView == null);
                boolean bt = (this == bitmapTask);
                Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "imageview == null => "+ iv +"\nthis == bitmaptask => "+ bt);
            }
        }
        else {
            Logger.log(Consts.Logger.LOG_ERROR, mClassName, "imageViewReference or bitmap is null");
        }
    }
}
