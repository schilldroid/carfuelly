package de.schilldroid.carfuelly;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

import de.schilldroid.carfuelly.Utils.Consts;
import de.schilldroid.carfuelly.Utils.Logger;

/**
 * Created by Simon on 13.01.2016.
 */
public class SaveBitmapWorkerTask extends AsyncTask<Void, Void, Void> {


    private final String mClassName = "SaveBitmapWorkerTask";

    private String mFilePath;
    private Bitmap mBitmap;

    private Context mContext;

    public SaveBitmapWorkerTask(Context context, Bitmap bitmap, String destPath) {
        mFilePath = destPath;
        mBitmap = bitmap;
        mContext = context;
    }


    @Override
    protected Void doInBackground(Void... params) {

        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(mFilePath);
            Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "start writing bitmap to file '" + mFilePath + "' ...");
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outStream);
            Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "finish writing bitmap to file '" + mFilePath + "'!!!");
        } catch (Exception e) {
            Logger.log(Consts.Logger.LOG_ERROR, mClassName, "cannot write bitmap to file\n"+ e.getStackTrace());
        }

        try {
            if(outStream != null) {
                outStream.close();
            }
        } catch (Exception e) {
            Logger.log(Consts.Logger.LOG_ERROR, mClassName, "cannot close output stream\n"+ e.getStackTrace());
            e.printStackTrace();
        }

        return null;
    }
}
