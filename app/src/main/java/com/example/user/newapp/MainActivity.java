package com.example.user.newapp;

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
    // this is where we create the variables
    private EditText txtNumber ;
    private Button btnCalc;
    private RatingBar ratingBar;
    private EditText txtOutput;
    private int number;

    /**
     * when the app starts we initialize all the variables
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the id of the inputfields
        txtNumber = (EditText) findViewById(R.id.txtNumber);
        btnCalc = (Button) findViewById(R.id.calcBtn);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtOutput = (EditText) findViewById(R.id.txtOutput);
        txtOutput.setEnabled(false);

        // we make a call to the method which we made
        setRating();
    }

    /**
     * method to get the number that the user inputted
     * it checks whether there is a number that the user inputted or not
     * if the number entered is not equal to a number then is it will return a number
     */
    private void setRating(){
// when there is a click on the btnCalc the following action gets activated
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
                        }
                    }
                }
            }


        });
    }
}
