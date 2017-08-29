package com.example.user.newapp;

/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 * Created by Seyfullah Semen
 */


// dit zijn de libarries die worden gebruikt

import android.content.Context;
import android.content.Intent;

import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {


    /**
     * here we decalre our components that we use in our activities
     * we make them private because we dont need to change them in other classes
     */
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

    private FrameLayout frags_container;
    private HomeFragment fragment = new HomeFragment();

    private NavigationView navigation_view;
    private DrawerLayout.DrawerListener drawerListener;


    /**
     * De onCreate() methode AKA function is het punt wat er gebeurd wanneer de app is opgestart
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


////////////////////////////{FINDING THE ID OF THE COMPONENTS}/////////////////////////////////////////////////////////////////////////////////
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        frags_container = (FrameLayout) findViewById(R.id.frags_container);
        setSupportActionBar(toolbar);
////////////////////////////{FINDING THE ID OF THE COMPONENTS}/////////////////////////////////////////////////////////////////////////////////
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.frags_container, fragment);
            fragmentTransaction.commit();
        }// end of the if statement

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                if (drawerListener != null) {
                    drawerListener.onDrawerClosed(drawerLayout);
                }

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (drawerListener != null) {
                    drawerListener.onDrawerOpened(drawerLayout);
                }

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();


        change(); // make a call to the method to go to the next page


    }// end onCreate()

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        actionBarDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState, persistentState);

    }//end of onPostCreate()

    /**
     * this method takes care of getting the menu filled with the chooseable options
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }//end of onCreateOptionsMenu



    /**
     * this is the method for going to the next page with the help of our navigation drawer
     * We get the ids of the buttons and we place it in one place.
     * I found it more convenient to do it like this
     * But maybe I can use an Interface
     */

    private void change() {
        Button about_drawer = (Button) findViewById(R.id.about_drawer);
        Button home = (Button) findViewById(R.id.home);
        Button scroll_drawer = (Button) findViewById(R.id.scroll_drawer);
        Button inReadTopListView = (Button) findViewById(R.id.in_read_top_listview);
        Button inReadToprecyclerview = (Button) findViewById(R.id.in_read_top_recyclerview);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewFragment(new HomeFragment());
            }
        });

        about_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewFragment(new AboutVMGFragment());
            }
        });

        scroll_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewFragment(new BlankFragment());
            }
        });

        inReadTopListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewFragment(new ListViewFragment());
            }
        });
        inReadToprecyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewFragment(new RecyclerFragment());

            }
        });


    }

    /**
     * this is the method for changing from fragment to fragment
     *
     * @param fragment
     */

    private void openNewFragment(Fragment fragment) {
        String stateName = ((Object) fragment).getClass().getName();
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.frags_container, fragment, ((Object) fragment).getClass().getName());
            transaction.addToBackStack(stateName);
            drawerLayout.closeDrawer(GravityCompat.START);

            transaction.commit();
        } catch (IllegalStateException ex) {
            System.err.println("An error occurred with the Fragment");
            Log.e("Error Fragment ", " " + ex.getMessage());
        }

    }




}
