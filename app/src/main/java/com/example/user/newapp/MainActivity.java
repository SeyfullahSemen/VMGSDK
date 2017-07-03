package com.example.user.newapp;

/**
 * dit is de main activity dat is de plek waar de programma draait wanneer het programma opstart
 * dit is dus de plek waar de gebruiker van de app mee te maken zal hebben
 */
// dit zijn de libarries die worden gebruikt
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // dit zijn de variabelen die zijn aangemaakt
    // je declareert ze private zodat niet alle klassen erbij kunnen komen en aanpassingen kunnen  maken aan deze variabelen
    private EditText txtNumber ;
    private Button btnCalc;
    private RatingBar ratingBar;
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
        btnCalc = (Button) findViewById(R.id.calcBtn);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtOutput = (EditText) findViewById(R.id.txtOutput);
        // zet de output text op disabled
        txtOutput.setEnabled(false);

        // roep de methode aan die wij hier beneden hebben gemaakt
        setRating();
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
                // get the number that is entered and parse it tho a number
                number = Integer.parseInt(txtNumber.getText().toString());
                // check if the input is equal to a number
                if (txtNumber.getInputType() != 2){
                    txtOutput.setText("You need to enter a number!! ");
                    System.exit(0);
                }
               // while the number is not bigger then 0 then show a message which tells you that you need to enter higher then 0
                if (number < 0 || number == 0) {
                    txtOutput.setTextColor(Color.RED);
                    txtOutput.setText("You cant enter numbers lower than 0 or 0 ");
                } else {
                    System.out.println(txtNumber.getInputType());

                    // create a for loop to loop through till it reaches the amount that the user filled in
                    for (int i = 1; i < number; i++) {
                        // output the text
                        txtOutput.setText("Hello From android " + i + " \n");
                        // if the number is higher then 5 then make the text red
                        if (number > 5) {
                            txtOutput.setTextColor(Color.YELLOW);
                        }else if (number < 5){
                            txtOutput.setText("you entered a lower number then 5");
                            txtOutput.setTextColor(Color.BLUE);
                        }
                    }
                }
            }


        });
    }
}
