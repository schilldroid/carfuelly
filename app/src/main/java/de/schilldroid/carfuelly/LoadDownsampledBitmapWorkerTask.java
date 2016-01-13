package de.schilldroid.carfuelly;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;

import de.schilldroid.carfuelly.Utils.Consts;
import de.schilldroid.carfuelly.Utils.Logger;
import de.schilldroid.carfuelly.Utils.Tools;

/**
 * Created by Simon on 13.01.2016.
 */
public class LoadDownsampledBitmapWorkerTask extends AsyncTask<Void, Void, Bitmap> {

    private final int MODE_SOURCE_URI = 0;
    private final int MODE_SOURCE_PATH = 1;

    private String mClassName = "LoadDownsampledBitmapWorkerTask";

    private WeakReference<ImageView> mImageViewReference;
    private Uri mUri;
    private String mFilePath;
    private BitmapReceiver mReceiver;
    private int mMode;
    private Context mContext;

    public LoadDownsampledBitmapWorkerTask(Context context, ImageView imageView, Uri uri, BitmapReceiver receiver) {
        mMode = MODE_SOURCE_URI;
        mImageViewReference = new WeakReference<ImageView>(imageView);
        mUri = uri;
        mReceiver = receiver;
        mContext = context;
    }

    public LoadDownsampledBitmapWorkerTask(Context context, ImageView imageView, String path, BitmapReceiver receiver) {
        mMode = MODE_SOURCE_PATH;
        mImageViewReference = new WeakReference<ImageView>(imageView);
        mFilePath = path;
        mReceiver = receiver;
        mContext = context;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "start loading sampled bitmap");


        BitmapFactory.Options options = new BitmapFactory.Options();
        // with this option set to true, the bitmap is not created nor allocated. Just read image size  and mime type
        options.inJustDecodeBounds = true;


        // retrieve image meta data
        switch (mMode) {
            case MODE_SOURCE_URI:
                InputStream inStream = null;
                try {
                    // open uri as inputstream
                    inStream = mContext.getContentResolver().openInputStream(mUri);
                    // decode input stream with given options
                    BitmapFactory.decodeStream(inStream, null, options);
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.log(Consts.Logger.LOG_ERROR, mClassName, "cannot load bitmap metadata from uri '"+ mUri.toString() +"'\n"+ e.getStackTrace());
                    return null;
                }
                try {
                    inStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.log(Consts.Logger.LOG_ERROR, mClassName, "cannot close input stream for uri '"+ mUri.toString() +"'\n"+ e.getStackTrace());
                }
                break;


            case MODE_SOURCE_PATH:
                try {
                    BitmapFactory.decodeFile(mFilePath, options);
                } catch (Exception e) {
                    Logger.log(Consts.Logger.LOG_ERROR, mClassName, "cannot load bitmap metadata from file '" + mFilePath + "'\n"+ e.getStackTrace());
                    e.printStackTrace();
                    return null;
                }
                break;


            default:
                Logger.log(Consts.Logger.LOG_ERROR, mClassName, "unknown mode '"+ mMode +"'");
                return null;
        }

        // get dimensions of the image
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;

        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "image params (w | h | t): ("+ imageWidth +" | "+ imageHeight +" | "+ imageType +")");

        // now that we know the size of the image to be loaded, we have to evaluate it against the target size
        // get target dimensions
        //int targetHeight = mToolbarImage.getHeight();
        //int targetWidth = mToolbarImage.getWidth();
        int targetHeight = Consts.CarDetails.TARGET_IMAGE_HEIGHT;
        int targetWidth = Consts.CarDetails.TARGET_IMAGE_WIDTH;

        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "target params (w | h): ("+ targetWidth +" | "+ targetHeight +")");

        int sampleSize = Tools.computeBitmapSampleSize(imageWidth, imageHeight, targetWidth, targetHeight);

        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "resulting sample size: "+ sampleSize);

        //options.inSampleSize = targetScale;
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;

        Bitmap bitmap = null;

        // receive downsampled bitmap
        switch (mMode) {
            case MODE_SOURCE_URI:
                InputStream inStream = null;
                try {
                    // open uri as inputstream
                    inStream = mContext.getContentResolver().openInputStream(mUri);
                    // decode input stream with given options
                    bitmap = BitmapFactory.decodeStream(inStream, null, options);
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.log(Consts.Logger.LOG_ERROR, mClassName, "cannot load bitmap from uri '"+ mUri.toString() +"'\n"+ e.getStackTrace());
                    return null;
                }
                try {
                    inStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.log(Consts.Logger.LOG_ERROR, mClassName, "cannot close input stream for uri '"+ mUri.toString() +"'\n"+ e.getStackTrace());
                }
                break;


            case MODE_SOURCE_PATH:
                try {
                    bitmap = BitmapFactory.decodeFile(mFilePath, options);
                } catch (Exception e) {
                    Logger.log(Consts.Logger.LOG_ERROR, mClassName, "cannot load bitmap from file '" + mFilePath + "'\n"+ e.getStackTrace());
                    e.printStackTrace();
                    return null;
                }
                break;


            default:
                Logger.log(Consts.Logger.LOG_ERROR, mClassName, "unknown mode '"+ mMode +"'");
                return null;
        }

        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "resulting image params (w | h | t): ("+ options.outWidth +" | "+ options.outHeight +" | "+ options.outMimeType +")");

        return bitmap;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (mImageViewReference != null && bitmap != null) {
            final ImageView imageView = mImageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
        if(mReceiver != null) {
            mReceiver.onReceiveBitmap(bitmap);
        }
    }
}
