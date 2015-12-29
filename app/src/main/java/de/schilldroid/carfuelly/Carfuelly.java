package de.schilldroid.carfuelly;

import android.app.FragmentManager;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import java.util.HashMap;

import de.schilldroid.carfuelly.Drawer.NavDrawerListAdapter;
import de.schilldroid.carfuelly.Fragments.*;

public class Carfuelly extends AppCompatActivity {

    // MEMBER VARIABLES

    private static Carfuelly mMainActivity;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    private String mTitle;
    private String mDrawerTitle;

    private FloatingActionButton mFab;
    private boolean mIsFabOpen = false;

    private HashMap<Integer, BaseFragment> mContentFragments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carfuelly);

        // init static instance, to access main class from any point of code
        mMainActivity = this;

        // initialize FAB
        mFab = (FloatingActionButton) findViewById(R.id.fab_add);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFab.setSelected(!mFab.isSelected());

                // initialize animation appropriate to its state
                if (!mIsFabOpen) {
                    Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_add_rotate_fwd);
                    mFab.startAnimation(rotate);
                    mIsFabOpen = true;
                } else {
                    Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_add_rotate_reverse);
                    mFab.startAnimation(rotate);
                    mIsFabOpen = false;
                }
            }
        });

        mTitle = getTitle().toString();
        mDrawerTitle = "Navigation";

        initFragments();
        initNavigationDrawer();
        selectFragment(0);


    }

    private void initNavigationDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerList = (ListView) findViewById(R.id.navi_drawer_list);

        // to show the navigation drawer icon on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // initialize navigation drawer button
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navi_drawer_open, R.string.navi_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                // update action bar title
                getSupportActionBar().setTitle(mTitle);

                // creates call to onPrepareOptionsMenu() to sync navigation drawer icon
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // update action bar title
                getSupportActionBar().setTitle(mDrawerTitle);
                // creates call to onPrepareOptionsMenu() to sync navigation drawer icon
                invalidateOptionsMenu();
            }
        };
        // register navigation drawer button as drawer listener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // initialize drawer list
        NavDrawerListAdapter navDrawerAdapter = new NavDrawerListAdapter(this);
        mDrawerList.setAdapter(navDrawerAdapter);
        mDrawerList.setOnItemClickListener(navDrawerAdapter);
    }


    private void initFragments() {
        mContentFragments = new HashMap<>();

        mContentFragments.put(Consts.NavDrawer.ID_HOME_FRAGMENT, new HomeFragment());
        mContentFragments.put(Consts.NavDrawer.ID_CARS_FRAGMENT, new CarsFragment());
        mContentFragments.put(Consts.NavDrawer.ID_FUELINGS_FRAGMENT, new FuelingsFragment());
        mContentFragments.put(Consts.NavDrawer.ID_MISC_FRAGMENT, new DummyFragment());
        mContentFragments.put(Consts.NavDrawer.ID_COMBINED_FRAGMENT, new DummyFragment());
        mContentFragments.put(Consts.NavDrawer.ID_LIST_FRAGMENT, new DummyFragment());
        mContentFragments.put(Consts.NavDrawer.ID_GRAPHS_FRAGMENT, new DummyFragment());
        mContentFragments.put(Consts.NavDrawer.ID_STATIONS_FRAGMENT, new DummyFragment());
        mContentFragments.put(Consts.NavDrawer.ID_TYPES_FRAGMENT, new DummyFragment());

    }




    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // to initiate animation of the navigation drawer icon
        mDrawerToggle.syncState();
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // ask to open navigation drawer, if the icon on the action bar is tapped
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }



    public void selectFragment(int id) {
        // shows the appropriate fragment in content container

        String fragmentName;
        BaseFragment fragment = mContentFragments.get(id);

        if(fragment == null) {
            Logger.log(Consts.Logger.LOG_ERROR, "[Carfuelly]", "No fragment found for id = " + id);
            return;
        }

        // update title of action bar
        fragmentName = fragment.getTitle();
        updateTitle(fragmentName);

        // update content view with selected fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame_layout, fragment).commit();


        //mDrawerList.setItemChecked(position, true);
        // update current title
        //mTitle = fragmentName;
        // apply title to action bar
        //etSupportActionBar().setTitle(mTitle);
        // close drawer
        mDrawerLayout.closeDrawer(mDrawerList);
    }







    public static Carfuelly getMainActivity() {
        return mMainActivity;
    }


    public FloatingActionButton getFab() {
        return mFab;
    }

    public void updateTitle(String title) {
        mTitle = title;
        setTitle(mTitle);
    }

}
