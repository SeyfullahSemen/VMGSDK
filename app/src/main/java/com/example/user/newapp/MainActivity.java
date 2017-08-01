package com.example.user.newapp;

/**
 * Copyright © 2017 Video Media Group, Seyfullah Semen All rights reserved
 */


// dit zijn de libarries die worden gebruikt

import android.content.Intent;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;


import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
/////////////////////////////////////////////////////////

public class MainActivity extends AppCompatActivity {
    /**
     * hier worden de variabelen gecreeered voor de app
     * we hebben onder andere de webview, de text met lorem ipsum
     * en de button die de video laad wanneer er op wordt geklikt
     * de reden dat we ze private maken is zodat andere klassen de waarde van deze UI components niet kunnen aanpassen
     * het zorgt voor encapsulatie
     */

    private MenuItem home;

    private TextView txtLorem;
    private Button showWebView;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private FrameLayout fragment_container;

    private NestedScrollView scroll;
    private NavigationView navigation_view;


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


        /**
         * in de onCreate methode roepen wij de framelayout aan die is gemaakt in activity_main.xml
         * wij gaan hier onze fragment in laden.
         */
        fragment_container = (FrameLayout) findViewById(R.id.fragment_container);

        /**
         * hier roepen wij de fragment manager aan die ervoor gaat zorgen
         * dat we nieuwe fragmenten aan de framelayout kunnen toevoegen
         * dit is niet alleen onze BlankFragment maar ook toekomstige Fragments die gaan komen
         */
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        final BlankFragment fragment = new BlankFragment(); // maak een nieuwe instantie aan van BlankFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment); // voeg deze toe aan de frameLayout
        fragmentTransaction.commit(); // en vergeet het niet te committen

        // dit is de ID van onze textView die is gevuld met lorem ipsum tekst
        txtLorem = (TextView) findViewById(R.id.txtLorem); // lorem ipsum tekst

        toolbar = (Toolbar) findViewById(R.id.toolbar);// de toolbar die boven op komt

        scroll = (NestedScrollView) findViewById(R.id.scroll);// de scroll view waar de tekst in staat
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        // er is een scrollview gemaakt in de activity_main.xml en deze roepen we aan om een event uit te voeren wanneer de gebruiker over de
        // advertentie scrolled willen we natuurlijk weten waar de gebrukiker zich bevind en aan de hand van die informatie kunnen we bepalenm
        // of de advertentie moet stoppen of door moet gaan
        scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                fragment.ScrollEventVMG(scrollY, scrollX); // we roepen hier de methode aan die we hebben gemaakt in onze BlankFragment


            }
        });

        // dit zorgt ervoor dat we een toolbar hebben aan de bovenkant van onze app dit is de toolbar die is gemaakt in  toolbar_layout.xml
        setSupportActionBar(toolbar);// dit zorgt ervoor dat de toolbar tevoorschijn komt

        makeDrawerMenuClickable(); // dit is een methode die ervoor zorgt dat er acties optreden wanneer erop op de knopjes worden gedrukt van de hamburger menu


    }// end onCreate()

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
        Fragment fragment = null; // maak een nieuwe instantie van de fragment aan
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) { // pak de ID van de gekozen Item uit de menu

            case R.id.about:
                Intent aboutIntent = new Intent(this, AboutVMG.class);
                startActivity(aboutIntent);
                break;
            case R.id.listView:
                Intent listViewIntent = new Intent(this, ListView_page.class);
                startActivity(listViewIntent);
                break;
            case R.id.home:
                fragment = new BlankFragment();
                break;
            case R.id.testOff:
                fragment = new testOffset();
                break;
            default:
                return super.onOptionsItemSelected(item);


        }
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        return false;

    }


    /**
     * deze methode zorgt ervoor dat de navigation drawer oftewel onze hamburger menu klikbaar wordt
     * zodat we ook kunnen navigeren met deze menu
     */
    private void makeDrawerMenuClickable() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        /**
         * in grote lijnen zorgt deze methode er voor dat deze klik event kan uitgevoerd worden
         */
        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Fragment fragment = null; // maak een nieuwe instantie van de fragment aan
                FragmentManager fragmentManager = getSupportFragmentManager();

                switch (menuItem.getItemId()) {
                    case R.id.about:
                        Intent myIntent = new Intent(MainActivity.this, AboutVMG.class);
                        startActivity(myIntent);
                        break;
                    case R.id.listView:
                        Intent ListViewIntent = new Intent(MainActivity.this, ListView_page.class);
                        startActivity(ListViewIntent);
                        break;
                 case R.id.home:
                        fragment = new BlankFragment();
                        break;
                    case R.id.testOff:
                        fragment = new testOffset();
                        break;

                }
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return false;
            }
        });

    }


}
