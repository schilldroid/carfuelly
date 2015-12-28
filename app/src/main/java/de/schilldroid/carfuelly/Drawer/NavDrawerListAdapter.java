package de.schilldroid.carfuelly.Drawer;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.schilldroid.carfuelly.Carfuelly;
import de.schilldroid.carfuelly.Consts;
import de.schilldroid.carfuelly.R;

/**
 * Created by Simon on 22.12.2015.
 */
public class NavDrawerListAdapter extends BaseAdapter implements ListView.OnItemClickListener {

    private Carfuelly mAppContext;
    private LayoutInflater mInflater;

    private ArrayList<NavDrawerListItem> mDrawerItems;

    public NavDrawerListAdapter(AppCompatActivity a) {
        mAppContext = (Carfuelly) a;
        initDrawerListEntries();

        // obtain layout inflater instance
        mInflater = (LayoutInflater) mAppContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    private void initDrawerListEntries() {
        mDrawerItems = new ArrayList<>();

        // initialize the list of navigation items
        mDrawerItems.add(Consts.NavDrawer.ID_HOME_FRAGMENT, new NavDrawerListEntry(Consts.NavDrawer.ID_HOME_FRAGMENT, mAppContext.getResources().getString(R.string.nav_drawer_list_entry_home), R.drawable.ic_home_black_24dp));
        mDrawerItems.add(Consts.NavDrawer.ID_CARS_FRAGMENT, new NavDrawerListEntry(Consts.NavDrawer.ID_CARS_FRAGMENT, mAppContext.getResources().getString(R.string.nav_drawer_list_entry_cars), R.drawable.ic_home_black_24dp));

        mDrawerItems.add(new NavDrawerListSeparator());
        mDrawerItems.add(Consts.NavDrawer.ID_SUBHEADER_EXPENSES, new NavDrawerListSubheader(mAppContext.getResources().getString(R.string.nav_drawer_list_subheader_expenses)));

        mDrawerItems.add(Consts.NavDrawer.ID_FUELINGS_FRAGMENT, new NavDrawerListEntry(Consts.NavDrawer.ID_FUELINGS_FRAGMENT, mAppContext.getResources().getString(R.string.nav_drawer_list_entry_fuelings), R.drawable.ic_home_black_24dp));
        mDrawerItems.add(Consts.NavDrawer.ID_MISC_FRAGMENT, new NavDrawerListEntry(Consts.NavDrawer.ID_MISC_FRAGMENT, mAppContext.getResources().getString(R.string.nav_drawer_list_entry_misc), R.drawable.ic_home_black_24dp));
        mDrawerItems.add(Consts.NavDrawer.ID_COMBINED_FRAGMENT, new NavDrawerListEntry(Consts.NavDrawer.ID_COMBINED_FRAGMENT, mAppContext.getResources().getString(R.string.nav_drawer_list_entry_combined), R.drawable.ic_home_black_24dp));

        mDrawerItems.add(new NavDrawerListSeparator());
        mDrawerItems.add(Consts.NavDrawer.ID_SUBHEADER_STATISTICS, new NavDrawerListSubheader(mAppContext.getResources().getString(R.string.nav_drawer_list_subheader_statistics)));

        mDrawerItems.add(Consts.NavDrawer.ID_LIST_FRAGMENT, new NavDrawerListEntry(Consts.NavDrawer.ID_LIST_FRAGMENT, mAppContext.getResources().getString(R.string.nav_drawer_list_entry_list), R.drawable.ic_home_black_24dp));
        mDrawerItems.add(Consts.NavDrawer.ID_GRAPHS_FRAGMENT, new NavDrawerListEntry(Consts.NavDrawer.ID_GRAPHS_FRAGMENT, mAppContext.getResources().getString(R.string.nav_drawer_list_entry_graphs), R.drawable.ic_home_black_24dp));

        mDrawerItems.add(new NavDrawerListSeparator());
        mDrawerItems.add(Consts.NavDrawer.ID_SUBHEADER_DATA, new NavDrawerListSubheader(mAppContext.getResources().getString(R.string.nav_drawer_list_subheader_data)));

        mDrawerItems.add(Consts.NavDrawer.ID_STATIONS_FRAGMENT, new NavDrawerListEntry(Consts.NavDrawer.ID_STATIONS_FRAGMENT, mAppContext.getResources().getString(R.string.nav_drawer_list_entry_stations), R.drawable.ic_home_black_24dp));
        mDrawerItems.add(Consts.NavDrawer.ID_TYPES_FRAGMENT, new NavDrawerListEntry(Consts.NavDrawer.ID_TYPES_FRAGMENT, mAppContext.getResources().getString(R.string.nav_drawer_list_entry_types), R.drawable.ic_home_black_24dp));

    }

    @Override
    public int getCount() {
        return mDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        // take care of different list item types (entry, seperator, subheader)
        switch (mDrawerItems.get(position).getViewType()) {
            case Consts.NavDrawer.ITEM_VIEW_TYPE_ENTRY:
                view = initializeEntry(view, position);
                break;
            case Consts.NavDrawer.ITEM_VIEW_TYPE_SEPARATOR:
                view = initializeSeparator(view, position);
                break;
            case Consts.NavDrawer.ITEM_VIEW_TYPE_SUBHEADER:
                view = initializeSubheader(view, position);
                break;
        }

        return view;
    }

    public View initializeEntry(View v, int position) {

        // if no view is present, create a new
        if(v == null) {
            v = mInflater.inflate(R.layout.nav_drawer_list_item, null);
        }

        // initialize components in the view
        ImageView icon = (ImageView) v.findViewById(R.id.nav_drawer_list_item_icon);
        TextView dest = (TextView) v.findViewById(R.id.nav_drawer_list_item_dest);

        NavDrawerListItem item = mDrawerItems.get(position);
        icon.setImageResource(((NavDrawerListEntry) item).getIconResourceId());
        dest.setText(((NavDrawerListEntry) item).getName());
        return v;
    }


    public View initializeSubheader(View v, int position) {

        // if no view is present, create a new
        if(v == null) {
            v = mInflater.inflate(R.layout.nav_drawer_list_subheader, null);
        }

        // initialize components in the view
        TextView textView = (TextView) v.findViewById(R.id.nav_drawer_list_subheader_title);
        NavDrawerListSubheader subheader = (NavDrawerListSubheader)mDrawerItems.get(position);
        textView.setText(subheader.getName());

        return v;
    }


    public View initializeSeparator(View v, int position) {

        // if no view is present, create a new
        if(v == null) {
            v = mInflater.inflate(R.layout.nav_drawer_list_separator, null);
        }
        return v;
    }



    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        NavDrawerListItem item = mDrawerItems.get(position);

        // if the clicked item is not a separator or a subheader, then show the fragment the entry belongs to
        if(item.getViewType() == Consts.NavDrawer.ITEM_VIEW_TYPE_ENTRY) {
            mAppContext.selectFragment(((NavDrawerListEntry)item).getFragmentID());
        }

    }

}
