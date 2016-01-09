package de.schilldroid.carfuelly.Utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    public static void copyFileFromUri(Context context, Uri sourceUri, File destFile) throws IOException {
        InputStream inStream = context.getContentResolver().openInputStream(sourceUri);
        OutputStream outStream = new FileOutputStream(destFile);
        writeFile(inStream, outStream);
    }

    public static void writeFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
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

    public static File getExternalStorageImagesDirectory() {
        File f = new File(getExternalStorageDirectory(), Consts.General.IMAGES_DIRECTORY_NAME);
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

    public static void updateImage(ImageView imageView, File src) {
        Bitmap image = null;
        try {
            // create bitmap from file
            image = BitmapFactory.decodeFile(src.getAbsolutePath());
        } catch (NullPointerException e) {
            Logger.log(Consts.Logger.LOG_ERROR, mClassName, "nullpointer exception while trying to open car image file");
        }

        if(image != null) {
            // display file
            imageView.setImageBitmap(image);
        }
        else {
            // show default image
            imageView.setImageResource(R.drawable.cockpit_def);
        }
    }

}






