package com.example.user.newapp;

/**
 * dit is de main activity dat is de plek waar de programma draait wanneer het programma opstart
 * dit is dus de plek waar de gebruiker van de app het eerste mee te maken zal hebben
 */
// dit zijn de libarries die worden gebruikt
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    // dit zijn de variabelen die zijn aangemaakt
    // je declareert ze private zodat niet alle klassen erbij kunnen komen en aanpassingen kunnen  maken aan deze variabelen
    private EditText txtNumber ;
    private Button btnCalc;
    private WebView webView;
    private TextView txtLorem;
    private EditText txtOutput;
    private int number;

    /**
     * De onCreate() methode AKA function is het punt wat er gebeurd wanneer de app is opgestart
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
        txtNumber = (EditText) findViewById(R.id.txtNumber);
        txtLorem = (TextView) findViewById(R.id.txtLorem); // lorem ipsum tekst
        btnCalc = (Button) findViewById(R.id.calcBtn); // de button
        webView = (WebView) findViewById(R.id.webViewTest); // de webview
        txtOutput = (EditText) findViewById(R.id.txtOutput); // de output tekst
        // zet de output text op disabled
        txtOutput.setEnabled(false);

        // roep de methode aan die wij hier beneden hebben gemaakt
        setRating();
        setView();
    }

    /**
     * Maak een private methode dit betekent dat andere klassen niet bij deze methode kunnen komen
     * void betekent dat er geen return value is
     * er is ook geen parameter meegegeven
     */
    private void setRating(){
// Je maakt hiermee een action op de knopje die boven op is aangemaakt
        btnCalc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // pak de nummer die is ingevoerd en zet het om van tekst naar nummer
                number = Integer.parseInt(txtNumber.getText().toString());
                // kijk of de ingevoerde een getal is dit wordt aangegeven met de ID 2
                if (txtNumber.getInputType() != 2){
                    txtOutput.setText("You need to enter a number!! "); // als dat geen nummer is dan geeft het aan dat het een nummer moet zijn
                    System.exit(0);// en hij sluit de app
                }
               // als de nummer die ingevoerd is kleinder dan 0 is of gelijk is aan 0 dan wordt de tekst kleur rood
                if (number < 0 || number == 0) {
                    txtOutput.setTextColor(Color.RED);
                    txtOutput.setText("You cant enter numbers lower than 0 or 0 ");
                } else { // als de nummer hoger is dan 0
                    // Dan wordt er een for loop gemaakt die doorgaat tot de grootte bereikt is van de ingevoerde getal
                    for (int i = 1; i < number; i++) {
                        // output the text
                        txtOutput.setText("Hello From android " + i + " \n");
                        // als de nummer hoger is dan 5 dan wordt de text kleur geel
                        if (number > 5) {
                            txtOutput.setTextColor(Color.YELLOW);
                        }else if (number < 5){// als de nummer lager is dan 5
                            txtOutput.setText("you entered a lower number then 5"); // dan wordt de volgende tekst aan de outputText gezet
                            txtOutput.setTextColor(Color.BLUE); // en de text kleur wordt dan blauw
                        }
                    }
                }
            }


        });
    }// einde van de methode setRating()

    /**
     * hier maken we een methode die ervoor zorgt dat er een webpagina ingeladen wordt
     * - we maken de methode weer private we hoeven de methode nergens aan te passen aan de methode
     * - we gebruiken de variable die wij webView hebben genoemd en daar laden we een URL in
     * - vervolgens willen we dat Javascript ook erin wordt geladen
     * - dit doen we door WebSettings aan te zetten en daarna met een methode die in de klasse WebSettings is gemaakt aan te roepen en de boolean waarde op true
     * te zetten
     */
    private void setView(){
        // laad de url in de webview
        webView.loadUrl("http://support.adform.com/documentation/build-with-html5-studio/introduction/");
        // maak een nieuwe instantie aan van de WebSettings klasse
        WebSettings webSettings = webView.getSettings();
        // gebruikt de methode SetJavaScriptEnabled() op true
        webSettings.setJavaScriptEnabled(true);





    }
}
