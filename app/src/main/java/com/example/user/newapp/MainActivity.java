package com.example.user.newapp;

/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 * Created by Seyfullah Semen
 */


// dit zijn de libarries die worden gebruikt

import android.content.Intent;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.TextView;
import android.widget.Toast;
/////////////////////////////////////////////////////////
//TODO: we are getting to the rigth way but find ways to make the code more efficient


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
        // wannneer er een android app is aangemaakt staan deze twee methodes er standaart in
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// dit zorgt voor het inladen van de scherm


        // dit is de ID van onze textView die is gevuld met lorem ipsum tekst

////////////////////////////{FINDING THE ID OF THE COMPONENTS}/////////////////////////////////////////////////////////////////////////////////
        toolbar = (Toolbar) findViewById(R.id.toolbar);// de toolbar die boven op komt

//        //scroll = (NestedScrollView) findViewById(R.id.scroll);// de scroll view waar de tekst in staat
//        navigation_view = (NavigationView) findViewById(R.id.navigation_view);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        frags_container = (FrameLayout) findViewById(R.id.frags_container);
        setSupportActionBar(toolbar);
////////////////////////////{FINDING THE ID OF THE COMPONENTS}/////////////////////////////////////////////////////////////////////////////////
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.frags_container, fragment); // voeg deze toe aan de frameLayout
            fragmentTransaction.commit(); // en vergeet het niet te committen
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
     * deze methode zorgt ervoor dat de menu wordt gevuld met de items die wij erin hebben gezet
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
     * dit zorgt voor de event wanneer een menu item is geklikt hierdoor kunnen we naar de
     * volgende paginas gaan
     * wat wij hier meegeven is het volgende
     * - als parameter komt er een item in te staan
     * - daarna vragen we aan de computer om de id op te halen van de gekozen item
     * - hierna gaat de computer aan de hand van de id bepalen naar welke pagina hij moet gaan
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) { // pak de ID van de gekozen Item uit de menu

            case R.id.about:
                openNewFragment(new AboutVMGFragment());
                break;

            case R.id.home:
                openNewFragment(new HomeFragment());
                break;

            case R.id.scroller_id:
                openNewFragment(new BlankFragment());
                break;

            default:
                return super.onOptionsItemSelected(item);


        }

        return false;

    }

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
        Button ListView = (Button) findViewById(R.id.listview);

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

        ListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewFragment(new ListViewFragment());
            }
        });


    }


    private void openNewFragment(Fragment fragment) {
        String stateName = ((Object) fragment).getClass().getName();
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.frags_container, fragment, ((Object) fragment).getClass().getName());
            transaction.addToBackStack(stateName);


            transaction.commit();
        } catch (IllegalStateException ex) {
            System.err.println("An error occurred with the Fragment");
            Log.e("Error Fragment ", " " + ex.getMessage());
        }

    }


}
