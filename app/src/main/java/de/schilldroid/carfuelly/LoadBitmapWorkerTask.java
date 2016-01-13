package de.schilldroid.carfuelly;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Simon on 13.01.2016.
 */
public class LoadBitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {

    // TODO take care of concurrency!!

    private final WeakReference<ImageView> mImageViewReference;
    private String mSrcFilepath;

    public LoadBitmapWorkerTask(ImageView imageView, String srcFilepath) {
        mSrcFilepath = srcFilepath;
        mImageViewReference = new WeakReference<ImageView>(imageView);
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(Void... params) {
        return BitmapFactory.decodeFile(mSrcFilepath);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (mImageViewReference != null && bitmap != null) {
            final ImageView imageView = mImageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
