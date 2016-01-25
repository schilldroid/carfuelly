package de.schilldroid.carfuelly.Utils;

import java.text.SimpleDateFormat;

/**
 * Created by Simon on 23.12.2015.
 */
public final class Consts {

    public static class General {
        public static final String EXTERNAL_STORAGE_DIRECTORY_NAME = "carfuelly";
        public static final String IMAGES_DIRECTORY_NAME = "images";
        public static final String CAR_IMAGES_NAME_PREFIX = "car_";
        public static final String IMAGES_SUFFIX = ".jpg";
        public static final String MEDIA_DIRECTORY_NAME = "media";
    }


    public final class NavDrawer {
        public static final int TOTAL_NUMBER_OF_ENTRIES = 15;

        public static final int ID_HOME_FRAGMENT = 0;
        public static final int ID_CARS_FRAGMENT = 1;
        public static final int ID_FUELINGS_FRAGMENT = 4;
        public static final int ID_MISC_FRAGMENT = 5;
        public static final int ID_COMBINED_FRAGMENT = 6;
        public static final int ID_LIST_FRAGMENT = 9;
        public static final int ID_GRAPHS_FRAGMENT = 10;
        public static final int ID_STATIONS_FRAGMENT = 13;
        public static final int ID_TYPES_FRAGMENT = 14;

        public static final int ID_SUBHEADER_EXPENSES = 3;
        public static final int ID_SUBHEADER_STATISTICS = 8;
        public static final int ID_SUBHEADER_DATA = 12;


        public static final int ITEM_VIEW_TYPE_ENTRY = 0;
        public static final int ITEM_VIEW_TYPE_SEPARATOR = 1;
        public static final int ITEM_VIEW_TYPE_SUBHEADER = 2;
    }

    public final class Logger {
        public static final int LOG_DEBUG = 0;
        public static final int LOG_INFO = 1;
        public static final int LOG_WARNING = 2;
        public static final int LOG_ERROR = 3;
        public static final int LOG_CRITICAL = 4;
    }

    public final class CarDetails {
        public static final String DATE_PICKER_CONTEXT = "context";
        public static final int DATE_PICKER_CONTEXT_FIRST_REGISTRATION = 0;
        public static final int DATE_PICKER_CONTEXT_PURCHASE_DATE = 1;

        public static final String PARAM_CAR_ID = "CAR_ID";
        public static final String CONTEXT_KEY = "CONTEXT";
        public static final int CONTEXT_CREATE = 0;
        public static final int CONTEXT_MODIFY = 1;

        public static final int REQUEST_CAR_DETAILS = 0;
        public static final int CAR_DETAILS_OK = 0;
        public static final int CAR_DETAILS_CANCELED = 1;

        public static final int REQUEST_PICK_IMAGE = 0;

        public static final int TARGET_IMAGE_WIDTH = 1024;
        public static final int TARGET_IMAGE_HEIGHT = 1024;
    }

}
