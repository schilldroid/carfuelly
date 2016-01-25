package de.schilldroid.carfuelly.Utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.schilldroid.carfuelly.AsyncDrawable;
import de.schilldroid.carfuelly.BitmapCacheOwner;
import de.schilldroid.carfuelly.Car;
import de.schilldroid.carfuelly.Carfuelly;
import de.schilldroid.carfuelly.LoadBitmapWorkerTask;
import de.schilldroid.carfuelly.R;

/**
 * Created by Simon on 09.01.2016.
 */
public class Tools {

    private static final String mClassName = "[Tools]";

    public static String getAbsolutePathFromUri(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            String[] projection = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(uri, projection, null, null, null);
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(columnIndex);
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }
    }

    public static Bitmap getDownSampledBitmapFromUri(Context context, Uri uri) {

        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "getDownSampledBitmapFromUri is called!");

        if(uri == null) {
            Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "no car image uri set");
            return null;
        }

        InputStream inStream = null;
        try {
            // open uri asa inputstream
            inStream = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        BitmapFactory.Options options = new BitmapFactory.Options();
        // with this option set, the bitmap is not created nor allocated. Just read image size  and mime type
        options.inJustDecodeBounds = true;
        // decode input stream with given options
        BitmapFactory.decodeStream(inStream, null, options);
        try {
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;

        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "image params (w | h | t): ("+ imageWidth +" | "+ imageHeight +" | "+ imageType +")");

        // now that we know the size of the image to be loaded, we have to evaluate it against the target size
        // get target dimensions
        //int targetHeight = mToolbarImage.getHeight();
        //int targetWidth = mToolbarImage.getWidth();
        int targetHeight = 1000;
        int targetWidth = 1000;

        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "target params (w | h): ("+ targetWidth +" | "+ targetHeight +")");

        int targetScale = computeBitmapSampleSize(imageWidth, imageHeight, targetWidth, targetHeight);

        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "resulting scale: "+ targetScale);

        //options.inSampleSize = targetScale;
        options.inJustDecodeBounds = false;

        try {
            inStream = context.getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap b = BitmapFactory.decodeStream(inStream, null, options);
        return b;
    }


    public static Bitmap getDownSampledBitmapFromFile(File file) {

        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "getDownSampledBitmapFromFile is called!");

        BitmapFactory.Options options = new BitmapFactory.Options();
        // with this option set, the bitmap is not created nor allocated. Just read image size  and mime type
        options.inJustDecodeBounds = true;
        // decode input stream with given options
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;

        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "image params (w | h | t): ("+ imageWidth +" | "+ imageHeight +" | "+ imageType +")");

        // now that we know the size of the image to be loaded, we have to evaluate it against the target size
        // get target dimensions
        //int targetHeight = mToolbarImage.getHeight();
        //int targetWidth = mToolbarImage.getWidth();
        int targetHeight = 1000;
        int targetWidth = 1000;

        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "target params (w | h): (" + targetWidth + " | " + targetHeight + ")");

        int targetScale = computeBitmapSampleSize(imageWidth, imageHeight, targetWidth, targetHeight);

        Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "resulting scale: "+ targetScale);

        options.inSampleSize = targetScale;
        options.inJustDecodeBounds = false;

        Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        return b;
    }


    public static int computeBitmapSampleSize(int width, int height, int targetWidth, int targetHeight) {

        int sampleSize = 1;
        if (height > targetHeight || width > targetWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / sampleSize) > targetHeight && (halfWidth / sampleSize) > targetWidth) {
                sampleSize *= 2;
            }
        }
        return sampleSize;
    }

    public static void copyFileFromUri(Context context, Uri sourceUri, File destFile) throws IOException {
        InputStream inStream = context.getContentResolver().openInputStream(sourceUri);
        OutputStream outStream = new FileOutputStream(destFile);
        writeFile(inStream, outStream);
    }

    public static void writeBitmapToFile(Bitmap b, File destFile) throws IOException {
        OutputStream outStream = new FileOutputStream(destFile);
        b.compress(Bitmap.CompressFormat.PNG, 100, outStream);
    }

    public static void writeFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public static LoadBitmapWorkerTask getBitmapTask(ImageView imageView) {
        if(imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if(drawable instanceof AsyncDrawable) {
                AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapTask();
            }
        }
        return null;
    }


    public static void loadCarImageFromFile(ImageView imageView, Car c, BitmapCacheOwner cacheOwner) {
        String imageFilename = c.getImageFilename();
        if(imageFilename != null) {
            File src = null;
            try {
                // build file path
                src = new File(Tools.getExternalStorageImagesDirectory(), c.getImageFilename());

                // if the cancelation of the current bitmaploader of this particular view was successfull ...
                if(Tools.cancelTaskSuccess(src.getAbsolutePath(), imageView)) {
                    // create new bitmaploader
                    final LoadBitmapWorkerTask bitmapTask = new LoadBitmapWorkerTask(imageView, src.getAbsolutePath(), cacheOwner);
                    // create Drawable, that contains default bitmap and bitmaploader
                    final AsyncDrawable asyncDrawable = new AsyncDrawable(Carfuelly.getMainActivity().getResources(), BitmapFactory.decodeResource(Carfuelly.getMainActivity().getResources(), R.drawable.cockpit_def), bitmapTask);
                    // apply drawable to the destination
                    imageView.setImageDrawable(asyncDrawable);
                    // execute bitmaploader
                    bitmapTask.execute();
                }
            } catch (Exception e) {
                Logger.log(Consts.Logger.LOG_ERROR, mClassName, "exception while trying to open car image file '"+ src.getAbsolutePath() +"'! displaying default image instead");
                imageView.setImageResource(R.drawable.cockpit_def);
            }
        }
        else {
            Logger.log(Consts.Logger.LOG_INFO, mClassName, "no image filename set for car "+ c.toString() +". displaying default image instead");
            imageView.setImageResource(R.drawable.cockpit_def);
        }
    }

    public static boolean cancelTaskSuccess(String filename, ImageView imageView) {

        // obrain bitmaploader from the given imageview
        final LoadBitmapWorkerTask bitmapTask = Tools.getBitmapTask(imageView);

        // if there is a bitmaploader present ...
        if(bitmapTask != null) {
            // query the image name, that the currently running bitmaploader works on
            final String bitmapFilename = bitmapTask.getFilename();
            // if the current bitmaploader has not started yet OR the image loaded by this bitmaploader not equals the image, that the new loader want to process ...
            if(!bitmapTask.hasStarted() || !bitmapFilename.equals(filename)) {
                // cancel the current bitmaploader
                bitmapTask.cancel(true);
                Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "cancelled bitmap task to render new bitmap in location '"+ filename +"'");
            }
            else {
                return false;
            }
        }

        return true;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getExternalStorageDirectory() {
        File f = new File(Environment.getExternalStorageDirectory(), Consts.General.EXTERNAL_STORAGE_DIRECTORY_NAME);
        if(! f.exists()) {
            Logger.log(Consts.Logger.LOG_INFO, mClassName, "Directory '"+ f.toString() +"' does not exist, so I try to create it");
            try {
                f.mkdir();
                Logger.log(Consts.Logger.LOG_INFO, mClassName, "Directory '" + f.toString() + "' successfully created!");
            } catch (Exception e) {
                Logger.log(Consts.Logger.LOG_ERROR, mClassName, "could not create directory '"+ f.toString() +"'!\n"+ e.toString());
            }
        }
        return f;
    }


    public static File getExternalStorageMediaDirectory() {
        File f = new File(getExternalStorageDirectory(), Consts.General.MEDIA_DIRECTORY_NAME);
        if(! f.exists()) {
            Logger.log(Consts.Logger.LOG_INFO, mClassName, "Directory '"+ f.toString() +"' does not exist, so I try to create it");
            try {
                f.mkdir();
                Logger.log(Consts.Logger.LOG_INFO, mClassName, "Directory '" + f.toString() + "' successfully created!");
            } catch (Exception e) {
                Logger.log(Consts.Logger.LOG_ERROR, mClassName, "could not create directory '"+ f.toString() +"'!\n"+ e.toString());
            }
        }
        return f;
    }

    public static File getExternalStorageImagesDirectory() {
        File f = new File(getExternalStorageMediaDirectory(), Consts.General.IMAGES_DIRECTORY_NAME);
        if(! f.exists()) {
            Logger.log(Consts.Logger.LOG_INFO, mClassName, "Directory '" + f.toString() + "' does not exist, so I try to create it");
            try {
                f.mkdir();
                Logger.log(Consts.Logger.LOG_INFO, mClassName, "Directory '" + f.toString() + "' successfully created!");
            } catch (Exception e) {
                Logger.log(Consts.Logger.LOG_ERROR, mClassName, "could not create directory '"+ f.toString() +"'!\n"+ e.toString());
            }
        }
        return f;
    }


}






