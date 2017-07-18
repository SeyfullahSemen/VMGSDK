package com.example.user.newapp;

/**
 * dit is de main activity dit is de plek waar de programma draait wanneer het programma opstart
 * dit is dus de plek waar de gebruiker van de app het eerste mee te maken zal hebben
 */
// dit zijn de libarries die worden gebruikt


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import android.widget.Button;
import android.widget.TextView;
import android.webkit.WebViewClient;


public class MainActivity extends AppCompatActivity {
    /**
     * hier worden de variabelen gecreeered voor de app
     * we hebben onder andere de webview, de text met lorem ipsum
     * en de button die de video laad wanneer er op wordt geklikt
     * de reden dat we ze private maken is zodat andere klassen de waarde van deze UI components niet kunnen aanpassen
     * het zorgt voor encapsulatie
     */

    private MenuItem home;
    private WebView webView;
    private TextView txtLorem;
    private Button showWebView;
    private Button closeWebView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;

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


        txtLorem = (TextView) findViewById(R.id.txtLorem); // lorem ipsum tekst
        webView = (WebView) findViewById(R.id.webViewTest); // de webview

        closeWebView = (Button) findViewById(R.id.closeWebView);// de button om de webview af te sluiten
        toolbar = (Toolbar) findViewById(R.id.toolbar);// de toolbar die boven op komt

        scroll = (NestedScrollView) findViewById(R.id.scroll);// de scroll view waar de tekst in staat
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);


        setSupportActionBar(toolbar);// dit zorgt ervoor dat de toolbar tevoorschijn komt

        makeDrawerMenuClickable();
        seeWebView();
        setView();


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
        switch (item.getItemId()) {
            case R.id.about:
                Intent aboutIntent = new Intent(this, AboutVMG.class);
                startActivity(aboutIntent);
                break;
            case R.id.listView:
                Intent listViewIntent = new Intent(this, ListView_page.class);
                startActivity(listViewIntent);
            default:
                return super.onOptionsItemSelected(item);


        }
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

    /**
     * hier maken we een methode die ervoor zorgt dat er een webpagina ingeladen wordt
     * - we maken de methode weer private we hoeven de methode nergens aan te passen aan de methode
     * - we gebruiken de variable die wij webView hebben genoemd en daar laden we een URL in
     * - vervolgens willen we dat Javascript ook erin wordt geladen
     * - dit doen we door WebSettings aan te zetten en daarna met een methode die in de klasse WebSettings is gemaakt aan te roepen en de boolean waarde op true
     * te zetten
     */
    private void setView() {


        /**
         * alles wat hier beneden gebeurd heeft te maken met het inladen van de video in de webview
         * zoals de webview settings maken en de video laden nadat de pagina is geladen
         *
         */
        // maak een nieuwe instantie aan van de WebSettings klasse
        WebSettings webSettings = webView.getSettings();

        // gebruikt de methode SetJavaScriptEnabled() op true
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.loadUrl("file:///android_asset/www/index.html");
        // maak een nieuwe instantie van de webviewclient
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);



            }
        });


        // maak een nieuwe instantie van de chrome webview client
        webView.setWebChromeClient(new WebChromeClient() {

        });


    }

    /**
     * dit is een methode die ervoor zorgt dat de webview weer openklapt en weer dicht gaat
     * het zorgt er ook voor dat de video tussen de tekst door komt
     */
    private void seeWebView() {
        int dimensionInPixel = 233;// we maken hier een variable aan die de pixels aangeeft
        webView = (WebView) findViewById(R.id.webViewTest); // we pakken hier de ID van de webview zodat we die kunnen gebruiken
        scroll = (NestedScrollView) findViewById(R.id.scroll);// we pakken hier de ID van de scrollview waar de webview in zit

        /**
         * hier beneden hebben wij de event die ervoor zorgt dat de webview weer dicht gaat
         */
        closeWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * als de webview wordt gesloten dan zorgen we ervoor dat de hoogte en de breedte van de webview weer op 0 komen te staan
                 */
                webView.getLayoutParams().height = 0;
                webView.getLayoutParams().width = 0;
                webView.requestLayout();
                webView.setVisibility(View.INVISIBLE);
                webView.onPause();// zorgt ervoor dat de video gaat pauzeren
                // de webview is hierdoor niet meer actief dus het filmpje speelt zich niet meer af
                webView.setActivated(false);
                closeWebView.setVisibility(View.INVISIBLE);
                webView.setEnabled(false);
            }
        });


    }


/**
 * dit is een niet gebruikte methode, maar deze methode kun je gebruken om met een button
 * naar een ander pagina te gaan
 */
//    public void open_About_Page(View view) {
//        Intent intent = new Intent(this, AboutVMG.class);
//        startActivity(intent);
//    }


}
