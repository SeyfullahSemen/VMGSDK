package com.example.user.newapp;

/**
 * dit is de main activity dit is de plek waar de programma draait wanneer het programma opstart
 * dit is dus de plek waar de gebruiker van de app het eerste mee te maken zal hebben
 */
// dit zijn de libarries die worden gebruikt

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    private WebView webView;
    private TextView txtLorem;
    private Button showWebView;


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
        /**
         * hier beneden hebben we een event voor de button die ervoor zorgt dat de webview open gaat
         */
        showWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.setVisibility(View.VISIBLE);
                setView();// hier roepen wij de methode aan die hier beneden is gemaakt
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
                "</head>\n" +
                "<body>\n" +
                "  <div>\n" +
                "    <video id='video' controls preload='none'\n" +
                "      poster=\"https://media.w3.org/2010/05/sintel/poster.png\" width=\"320\" height=\"240\" autoplay>\n" +
                "\n" +
                "      <source id='mp4'\n" +
                "        src=\"https://media.w3.org/2010/05/sintel/trailer.mp4\"\n" +
                "        type='video/mp4'>\n" +
                "      \n" +
                "\n" +
                "    </video>\n" +
                "    \n" +
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
        webSettings.setPluginState(WebSettings.PluginState.ON);
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
        webView.setWebChromeClient(new WebChromeClient());
        // laad de datat in in de webview
        webView.loadData(playVideo, "text/html", "utf-8");

    }
}
