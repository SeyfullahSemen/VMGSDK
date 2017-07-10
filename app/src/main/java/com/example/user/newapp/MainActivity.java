package com.example.user.newapp;

/**
 * dit is de main activity dit is de plek waar de programma draait wanneer het programma opstart
 * dit is dus de plek waar de gebruiker van de app het eerste mee te maken zal hebben
 */
// dit zijn de libarries die worden gebruikt


import android.content.Intent;
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
        showWebView = (Button) findViewById(R.id.showWebView);// de button om de webview te laten zien
        closeWebView = (Button) findViewById(R.id.closeWebView);// de button om de webview af te sluiten
        toolbar = (Toolbar) findViewById(R.id.toolbar);// de toolbar die boven op komt

        scroll = (NestedScrollView) findViewById(R.id.scroll);// de scroll view waar de tekst in staat
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);


        setSupportActionBar(toolbar);// dit zorgt ervoor dat de toolbar tevoorschijn komt

        makeDrawerMenuClickable();
        seeWebView();


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
                Intent listViewIntent = new Intent (this,ListView_page.class);
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
                        Intent ListViewIntent = new Intent (MainActivity.this,ListView_page.class);
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

//        // laad de url in de webview
//        webView.loadUrl("http://support.adform.com/documentation/build-with-html5-studio/introduction/");
        // maak een string aan met HTML codee erin
        /**
         * in deze string zit de code van:
         * - de layout van de video
         * - de formaat van de video
         */
        String playVideo = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "\t\n" +
                "</head>\n" +
                "<body>\n" +
                "<article style=\"\">\n" +
                "\n" +
                "  <div id=\"container-media-player\">\n" +
                "    <video id='video' controls preload='none'\n" +
                "      poster=\"https://media.w3.org/2010/05/sintel/poster.png\" width=\"320\" height=\"240\" padding:50px; top:50px;  left:600px;  position: fixed; autoplay>\n" +
                "\n" +
                "      <source id='mp4'\n" +
                "        src=\"https://media.w3.org/2010/05/sintel/trailer.mp4\"\n" +
                "        type='video/mp4'>\n" +
                "    </video>\n" +
                "    <div>\n" +
                "    </article>\n" +
                "\n" +
                "     </body>\n" +
                "</html>";//einde van de HTML code

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

        // maak een nieuwe instantie van de webviewclient
        webView.setWebViewClient(new WebViewClient() {

            /**
             *
             * @param view
             * @param url
             * autoplay gaat in ze werking zodra de pagina is geladen dit wordt gedaan via een javascript functie
             */
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()");

            }

        });

        // maak een nieuwe instantie van de chrome webview client
        webView.setWebChromeClient(new WebChromeClient() {

        });
        // laad de datat in in de webview
        webView.loadData(playVideo, "text/html", "utf-8");

    }

    /**
     * dit is een methode die ervoor zorgt dat de webview weer openklapt en weer dicht gaat
     * het zorgt er ook voor dat de video tussen de tekst door komt
     */
    private void seeWebView() {
        int dimensionInPixel = 233;// we maken hier een variable aan die de pixels aangeeft
        webView = (WebView) findViewById(R.id.webViewTest); // we pakken hier de ID van de webview zodat we die kunnen gebruiken
        scroll = (NestedScrollView) findViewById(R.id.scroll);// we pakken hier de ID van de scrollview waar de webview in zit
        // de pixels die we hierboven hadden aangemaakt converteren we naar db dit zorgt ervoor dat de video op elke telefoon hetzelfde is
        final int dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimensionInPixel, getResources().getDisplayMetrics());

        // vervolgens maken we listeners aan voor de knopjes die ervoor zorgen dat de webview gezien en weer gesloten kan worden
        showWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.getLayoutParams().height = dimensionInDp; // dit bepaalde de hoogte van de video in dp
                webView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT; // en deze voor de breedte van de video
                webView.requestLayout();// deze methode moet je opvragen anders werkt het niet
                webView.setVisibility(View.VISIBLE); // maak de webview eer zichtbaar
                // de webview wordt weer geactiveerd waardoor het filmpje weer afspeelt
                webView.setActivated(true);
                // de video wordt hervat met geluid en al
                webView.onResume();
                closeWebView.setVisibility(View.VISIBLE);
                setView();// hier roepen wij de methode aan die hier beneden is gemaakt
            }
        });
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
