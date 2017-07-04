package com.example.user.newapp;

/**
 * dit is de main activity dat is de plek waar de programma draait wanneer het programma opstart
 * dit is dus de plek waar de gebruiker van de app het eerste mee te maken zal hebben
 */
// dit zijn de libarries die worden gebruikt

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.webkit.WebViewClient;
import android.widget.VideoView;


public class MainActivity extends AppCompatActivity {
    // dit zijn de variabelen die zijn aangemaakt
    // je declareert ze private zodat niet alle klassen erbij kunnen komen en aanpassingen kunnen  maken aan deze variabelen

    //    private Button btnCalc;
    private WebView webView;
    private TextView txtLorem;


    /**
     * De onCreate() methode AKA function is het punt wat er gebeurd wanneer de app is opgestart
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // wannneer er een android app is aangemaakt staan deze twee methodes er standaart in
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Je klikt en sleept componenten in je app en die krijgen allemaal een unieke ID mee om te werken met deze componenten
         * hebben we dus de ID's nodig
         */

        txtLorem = (TextView) findViewById(R.id.txtLorem); // lorem ipsum tekst
        webView = (WebView) findViewById(R.id.webViewTest); // de webview

     // roep de methode aan die wij hier beneden hebben gemaakt
        setView();
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
                "</html>";

        // maak een nieuwe instantie aan van de WebSettings klasse


        WebSettings webSettings = webView.getSettings();

        // gebruikt de methode SetJavaScriptEnabled() op true
        webSettings.setJavaScriptEnabled(true);



        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);

        webView.setWebViewClient(new WebViewClient() {
            // autoplay when finished loading via javascript injection
            public void onPageFinished(WebView view, String url) { webView.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()"); }
        });
        webView.setWebChromeClient(new WebChromeClient());

        webView.loadData(playVideo, "text/html", "utf-8");

    }
}
