package de.schilldroid.carfuelly;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

import de.schilldroid.carfuelly.Activities.CarDetailsActivity;
import de.schilldroid.carfuelly.Fragments.CarsFragment;
import de.schilldroid.carfuelly.Utils.Consts;
import de.schilldroid.carfuelly.Utils.Logger;
import de.schilldroid.carfuelly.Utils.Tools;

/**
 * Created by Simon on 29.12.2015.
 */
public class CarCardListAdapter extends BaseAdapter implements BitmapCacheOwner {

    private LruCache<String, Bitmap> mBitmapMemoryCache;

    private CarsFragment mContainingFragment;
    private Carfuelly mAppContext;
    private LayoutInflater mInflater;
    private ArrayList<Car> mCars;
    private String mClassName = "[CarCardListAdapter]";


    public CarCardListAdapter(CarsFragment fragment) {
        mAppContext = Carfuelly.getMainActivity();
        mContainingFragment = fragment;
        initCarList();

        // obtain layout inflater instance
        mInflater = (LayoutInflater) mAppContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 4;
        mBitmapMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mBitmapMemoryCache.put(key, bitmap);
        }
    }

    public void deleteBitmapFromMemoryCache(String key) {
        mBitmapMemoryCache.remove(key);
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mBitmapMemoryCache.get(key);
    }

    private void initCarList() {
        mCars = DataManager.getInstance().getCars();
    }



    @Override
    public int getCount() {
        return mCars.size();
    }

    @Override
    public Object getItem(int position) {

        if(position >= 0 && position < mCars.size()) {
            Logger.log(Consts.Logger.LOG_INFO, "[CarCardListAdapter]", "requested car at position " + position);
            return mCars.get(position);
        }
        else {
            Logger.log(Consts.Logger.LOG_ERROR, "[CarCardListAdapter]", "requested car at position "+ position +" not found (size="+ mCars.size() +")");
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(position >= 0 && position < mCars.size()) {
            int id = mCars.get(position).getID();
            Logger.log(Consts.Logger.LOG_INFO, "[CarCardListAdapter]", "requested car id at position "+ position +" is "+ id);
            return id;
        }
        else {
            Logger.log(Consts.Logger.LOG_ERROR, "[CarCardListAdapter]", "requested car id at position "+ position +" not found (size="+ mCars.size() +")");
            return -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        // if no view is present, create a new
        if(v == null) {
            v = mInflater.inflate(R.layout.car_card_list_item, null);
        }



        CardView cardView = (CardView) v.findViewById(R.id.car_card);
        // saving the car ID in the cardview
        cardView.setTag(mCars.get(position).getID());
        // applying on click listener
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int carID = (int) v.getTag();
                Intent intent = new Intent(mAppContext, CarDetailsActivity.class);
                // tell the activity to start in MODIFY mode and modify the car identified by carID
                intent.putExtra(Consts.CarDetails.CONTEXT_KEY, Consts.CarDetails.CONTEXT_MODIFY);
                intent.putExtra(Consts.CarDetails.PARAM_CAR_ID, carID);
                // to get result, onActivityResult ist called on finishing CarDetailsActivity
                mContainingFragment.startActivityForResult(intent, Consts.CarDetails.REQUEST_CAR_DETAILS);
            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Logger.log(Consts.Logger.LOG_DEBUG, mClassName, "long cliked card");
                CardView cv = (CardView) v;

                cv.setCardBackgroundColor(R.color.colorPrimary);
                return true;
            }
        });

        Car c = mCars.get(position);

        // initialize components of view
        TextView name = (TextView) v.findViewById(R.id.car_card_name);
        name.setText(c.getName());

        TextView manufacturer = (TextView) v.findViewById(R.id.car_card_manufacturer);
        manufacturer.setText(c.getManufacturer());

        TextView model = (TextView) v.findViewById(R.id.car_card_model);
        model.setText(c.getModel());

        TextView dateOfFirstReg = (TextView) v.findViewById(R.id.car_card_date_of_first_registration);
        dateOfFirstReg.setText(c.getStrFirstRegistration());

        TextView performance = (TextView) v.findViewById(R.id.car_card_performance);
        performance.setText(c.getStrPower());

        TextView engine = (TextView) v.findViewById(R.id.car_card_engine);
        engine.setText(c.getStrEngine());

        TextView registration = (TextView) v.findViewById(R.id.car_card_registration);
        registration.setText(c.getLicensePlate());

        ImageView imageView = (ImageView) v.findViewById(R.id.car_card_image);

        if(c.getImageFilename() != null) {
            loadBitmap(imageView, c);
        }
        else {
            Logger.log(Consts.Logger.LOG_INFO, mClassName, "no image filename set for car "+ c.toString() +". displaying default image instead");
            imageView.setImageResource(R.drawable.cockpit_def);
        }




        return v;
    }

    private void loadBitmap(ImageView imageView, Car c) {

        final String imageKey = new File(Tools.getExternalStorageImagesDirectory(), c.getImageFilename()).getAbsolutePath();
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);

        if(bitmap != null) {
            Logger.log(Consts.Logger.LOG_INFO, mClassName, "found image "+ imageKey +" in cache");
            imageView.setImageBitmap(bitmap);
        }
        else {
            Logger.log(Consts.Logger.LOG_INFO, mClassName, "cannot find "+ imageKey +" in cache");
            Tools.loadCarImageFromFile(imageView, c, this);
        }
    }

    /*
    private void loadCarImage(ImageView imageView, Car c) {
        String imageFilename = c.getImageFilename();
        if(imageFilename != null) {
            File src = null;
            try {
                // build file path
                src = new File(Tools.getExternalStorageImagesDirectory(), c.getImageFilename());

                // if the cancelation of the current bitmaploader of this particular view was successfull ...
                if(cancelTaskSuccess(src.getAbsolutePath(), imageView)) {
                    // create new bitmaploader
                    final LoadBitmapWorkerTask bitmapTask = new LoadBitmapWorkerTask(imageView, src.getAbsolutePath());
                    // create Drawable, that contains default bitmap and bitmaploader
                    final AsyncDrawable asyncDrawable = new AsyncDrawable(mAppContext.getResources(), BitmapFactory.decodeResource(mAppContext.getResources(), R.drawable.cockpit_def), bitmapTask);
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


    public boolean cancelTaskSuccess(String filename, ImageView imageView) {

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
    */

    @Override
    public void notifyDataSetChanged() {
        initCarList();
        super.notifyDataSetChanged();
    }

    @Override
    public void onAddBitmapToCache(Bitmap bitmap, String filepath) {
        addBitmapToMemoryCache(filepath, bitmap);
    }
}

