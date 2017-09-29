package com.example.user.SDKDemo;

/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 * <p>
 * Created by Seyfullah Semen
 * </p>
 */


import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.user.SDKDemo.R;
import com.vmg.ConfigVMG.VMGConfig;
import com.vmg.LoggerPack.VMGLogs;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private FrameLayout frags_container;
    private HomeFragment fragment = new HomeFragment();
    private DrawerLayout.DrawerListener drawerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        frags_container = findViewById(R.id.frags_container);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.frags_container, fragment);
            fragmentTransaction.commit();
        }
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

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                if (drawerListener != null) {
                    drawerListener.onDrawerClosed(drawerLayout);
                }
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (drawerListener != null) {
                    drawerListener.onDrawerOpened(drawerLayout);
                }
                invalidateOptionsMenu();
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        changeView();
        VMGConfig.loadConfig(getApplicationContext(), 6194);


    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        actionBarDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState, persistentState);
    }


    private void changeView() {
        Button about_drawer = findViewById(R.id.about_vmg);
        Button home = findViewById(R.id.home);
        Button scroll_drawer = findViewById(R.id.scrollview_vmg);
        Button inReadTopListView = findViewById(R.id.in_read_top_listview);
        Button inReadToprecyclerview = findViewById(R.id.in_read_top_recyclerview);
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
            VMGLogs.fatal("Error opening fragment:  " + ex.getMessage());
        }

    }


}
