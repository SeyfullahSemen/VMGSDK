package com.example.user.newapp;

/**
 * dit is de main activity dit is de plek waar de programma draait wanneer het programma opstart
 * dit is dus de plek waar de gebruiker van de app het eerste mee te maken zal hebben
 */
// dit zijn de libarries die worden gebruikt


import android.content.Intent;
import android.graphics.Color;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.user.newapp.Interfaces.JavascriptInterface;


public class MainActivity extends AppCompatActivity {
    /**
     * hier worden de variabelen gecreeered voor de app
     * we hebben onder andere de webview, de text met lorem ipsum
     * en de button die de video laad wanneer er op wordt geklikt
     * de reden dat we ze private maken is zodat andere klassen de waarde van deze UI components niet kunnen aanpassen
     * het zorgt voor encapsulatie
     */

    private MenuItem home;
    //    private WebView webView;
    private TextView txtLorem;
    private Button showWebView;
    //private Button closeWebView;
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
         * Je klikt en sleept componenten in je app en die krijgen allemaal een unieke ID mee om te werken met deze componenten
         * hebben we dus de ID's nodig
         */
        fragment_container = (FrameLayout) findViewById(R.id.fragment_container);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        final BlankFragment fragment = new BlankFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();



        txtLorem = (TextView) findViewById(R.id.txtLorem); // lorem ipsum tekst

        toolbar = (Toolbar) findViewById(R.id.toolbar);// de toolbar die boven op komt

        scroll = (NestedScrollView) findViewById(R.id.scroll);// de scroll view waar de tekst in staat
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);

        scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                fragment.Scrollding(scrollY,scrollX );


            }
        });


        setSupportActionBar(toolbar);// dit zorgt ervoor dat de toolbar tevoorschijn komt

        makeDrawerMenuClickable();




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
        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (item.getItemId()) {

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

                switch (menuItem.getItemId()) {
                    case R.id.about:
                        Intent myIntent = new Intent(MainActivity.this, AboutVMG.class);
                        startActivity(myIntent);
                        break;
                    case R.id.listView:
                        Intent ListViewIntent = new Intent(MainActivity.this, ListView_page.class);
                        startActivity(ListViewIntent);
                        break;

                }
                return false;
            }
        });

    }



}
