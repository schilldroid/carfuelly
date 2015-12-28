package de.schilldroid.carfuelly.Drawer;

import de.schilldroid.carfuelly.Consts;

/**
 * Created by Simon on 23.12.2015.
 */
public class NavDrawerListSubheader extends NavDrawerListItem {

    private String mName;

    public NavDrawerListSubheader(String name) {
        mViewType = Consts.NavDrawer.ITEM_VIEW_TYPE_SUBHEADER;
        mName = name;
    }

    public String getName() {
        return mName;
    }
}
