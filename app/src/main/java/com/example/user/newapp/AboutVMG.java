package com.example.user.newapp;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class AboutVMG extends AppCompatActivity {
    private Toolbar toolbar_aboutVMG;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_vmg);
        toolbar_aboutVMG = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar_aboutVMG );// dit zorgt ervoor dat de toolbar tevoorschijn komt
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// op de toolbar komt er een soort pijltje zodat je het menu kan open klappen
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);// dit is de drawer menu
        /**
         * dit is het knopje -> die ervoor zorgt dat de drawer tevoorschijn komt
         */
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar_aboutVMG, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);// hier voegen we de event aan het knopje toe
    }


    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        actionBarDrawerToggle.syncState();
        super.onPostCreate(savedInstanceState, persistentState);

    }

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

    }


}
