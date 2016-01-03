package de.schilldroid.carfuelly.Utils;

/**
 * Created by Simon on 23.12.2015.
 */
public final class Consts {

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
    }
}
