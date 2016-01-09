package de.schilldroid.carfuelly;

import android.app.FragmentManager;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.HashMap;

import de.schilldroid.carfuelly.Fragments.*;
import de.schilldroid.carfuelly.Utils.Consts;
import de.schilldroid.carfuelly.Utils.Logger;

public class Carfuelly extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // MEMBER VARIABLES

    private static Carfuelly mMainActivity;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private Toolbar mToolbar;

    private String mTitle;

    private FloatingActionButton mFab;
    private boolean mIsFabOpen = false;

    private HashMap<Integer, BaseFragment> mContentFragments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carfuelly_activity_layout);

        // init static instance, to access main class from any point of code
        mMainActivity = this;

        mToolbar = (Toolbar) findViewById(R.id.car_details_toolbar);
        setSupportActionBar(mToolbar);

        // initialize FAB
        mFab = (FloatingActionButton) findViewById(R.id.fab_add);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFab.setSelected(!mFab.isSelected());

                View clayout = findViewById(R.id.coordinator_layout_main);

                // FAB animation. Not working correctly, when displaying a snack bar
//                // initialize animation appropriate to its state
//                if (!mIsFabOpen) {
//                    Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_add_rotate_fwd);
//                    mFab.startAnimation(rotate);
//                    mIsFabOpen = true;
//                } else {
//                    Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_add_rotate_reverse);
//                    mFab.startAnimation(rotate);
//                    mIsFabOpen = false;
//                }

                Snackbar.make(clayout, "Carfuelly", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        mTitle = getTitle().toString();

        initFragments();
        initNavigationDrawer();
        selectFragment(0);
    }

    private void initNavigationDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navi_drawer_open, R.string.navi_drawer_close);

        // register navigation drawer button as drawer listener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void initFragments() {
        mContentFragments = new HashMap<>();

        mContentFragments.put(R.id.nav_drawer_menu_home, new HomeFragment());
        mContentFragments.put(R.id.nav_drawer_menu_cars, new CarsFragment());
        mContentFragments.put(R.id.nav_drawer_menu_fuelings, new FuelingsFragment());
        mContentFragments.put(R.id.nav_drawer_menu_misc, new DummyFragment());
        mContentFragments.put(R.id.nav_drawer_menu_combined, new DummyFragment());
        mContentFragments.put(R.id.nav_drawer_menu_list, new DummyFragment());
        mContentFragments.put(R.id.nav_drawer_menu_Graphs, new DummyFragment());
        mContentFragments.put(R.id.nav_drawer_menu_stations, new DummyFragment());
        mContentFragments.put(R.id.nav_drawer_menu_types, new DummyFragment());

    }



    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void selectFragment(int fragmentId) {
        // shows the appropriate fragment in content container

        String fragmentName;
        BaseFragment fragment = mContentFragments.get(fragmentId);

        if(fragment == null) {
            Logger.log(Consts.Logger.LOG_ERROR, "[Carfuelly]", "No fragment found for id = " + fragmentId);
            return;
        }

        // update title of action bar
        fragmentName = fragment.getTitle();
        updateTitle(fragmentName);

        // update content view with selected fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame_layout, fragment).commit();

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Logger.log(Consts.Logger.LOG_ERROR, "[Carfuelly]", "navigation item with id " + id + " chosen");

        selectFragment(id);

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
