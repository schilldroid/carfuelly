package de.schilldroid.carfuelly.Drawer;

import de.schilldroid.carfuelly.Consts;

/**
 * Created by Simon on 22.12.2015.
 */
public class NavDrawerListEntry extends NavDrawerListItem {

    // identifies the fragment id, this entry belongs to
    private int mFragmentID;
    private String mName;
    private int mIconResourceId;

    public NavDrawerListEntry(int id, String name, int resourceId) {
        mFragmentID = id;
        mName = name;
        mIconResourceId = resourceId;
        mViewType = Consts.NavDrawer.ITEM_VIEW_TYPE_ENTRY;
    }

    public String getName() {
        return mName;
    }

    public int getIconResourceId() {
        return mIconResourceId;
    }

    public int getFragmentID() {
        return mFragmentID;
    }

}
