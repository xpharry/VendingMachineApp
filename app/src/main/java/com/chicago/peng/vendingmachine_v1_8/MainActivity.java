package com.chicago.peng.vendingmachine_v1_8;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chicago.peng.vendingmachine_v1_8.Beverage;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;

    Spinner spinner;

    HashMap<Integer, Beverage> beverage_map = new HashMap<>();
    HashMap<Integer, Integer> quantity_map = new HashMap<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beverage_map.put(R.id.radioButton1, new Beverage(R.id.radioButton1, "Coke", 1.85, 200, 10));
        beverage_map.put(R.id.radioButton2, new Beverage(R.id.radioButton2, "Pepsi", 1.99, 180, 7));
        beverage_map.put(R.id.radioButton3, new Beverage(R.id.radioButton3, "Fireball", 14.20, 350, 1));
        beverage_map.put(R.id.radioButton4, new Beverage(R.id.radioButton4, "Sprite", 1.35, 200, 0));
        beverage_map.put(R.id.radioButton5, new Beverage(R.id.radioButton5, "Blue Guy", 3.60, 300, 3));

        quantity_map.put(R.id.radioButton1, R.id.textQuantity1);
        quantity_map.put(R.id.radioButton2, R.id.textQuantity2);
        quantity_map.put(R.id.radioButton3, R.id.textQuantity3);
        quantity_map.put(R.id.radioButton4, R.id.textQuantity4);
        quantity_map.put(R.id.radioButton5, R.id.textQuantity5);

        // intialize spinner
        spinner = (Spinner) findViewById(R.id.dollars_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dollars_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        /* Initialize Radio Group and attach click handler */
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        /* Attach CheckedChangeListener to radio group */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(MainActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        final Button button_insert = (Button) findViewById(R.id.button_insert);
        button_insert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertAction();
            }
        });

        final Button button_buy = (Button) findViewById(R.id.button_buy);
        button_buy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buyAction();
            }
        });

        final Button button_cancel = (Button) findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cancelAction();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /*
     * Insert action
     */
    public void insertAction() {
//        // debugging status bar
//        TextView textNone = (TextView) findViewById(R.id.text_none);
//        textNone.setText("Inserting ...");

        // get the value to be inserted
        String text = spinner.getSelectedItem().toString();

        if( text == "$ 0.00" ) {
            Toast.makeText(MainActivity.this, // getApplicationContext()
                    "Invalid money value to be inserted!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // update the current
        TextView textCurrent = (TextView) findViewById(R.id.text_current);
        double current = Double.parseDouble(textCurrent.getText().toString().substring(1));
        double inserted = Double.parseDouble(text.substring(1));
        current += inserted;
        String str = String.format("%.2f", current);
        if(current >= 10.00) str = "$" + str;
        else str = "$ " + str;
        textCurrent.setText(str);

        // show the money value inserted in toast
        Toast.makeText(MainActivity.this, // getApplicationContext()
                text,
                Toast.LENGTH_SHORT).show();
    }

    /*
     * Buy action
     */
    public void buyAction() {
//        // debugging status bar
//        TextView textNone = (TextView) findViewById(R.id.text_none);
//        textNone.setText("Buying ...");

        String text; // info to be shown as status

        // get current
        TextView textCurrent = (TextView) findViewById(R.id.text_current);
        double current = Double.parseDouble(textCurrent.getText().toString().substring(1));

        // check if has selection
        int id = radioGroup.getCheckedRadioButtonId();
        if (id == -1) {
            text = "You need to choose one beverage first!";
            Toast.makeText(MainActivity.this, // getApplicationContext()
                    text,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // get beverage info
        RadioButton rb = (RadioButton) radioGroup.findViewById(id);
        Beverage beverage = beverage_map.get(id);
        double cost = beverage.cost;
        int quantity = beverage.quantity;

        // dealing
        if (quantity == 0) {
            text = "Sorry! Sold out.";
        } else {
            if (cost > current) {
                text = "Money inserted is not enough!";
            } else {
                current -= cost;
                quantity -= 1;

                // clear current account
                textCurrent.setText("$ 0.00");

                // update quantity
                beverage.quantity = quantity;
                TextView text_quantity = (TextView) findViewById(quantity_map.get(id));
                text_quantity.setText(String.valueOf(quantity));

                text = "Successfully bought the beverage! Enjoy!\n" + getChanges(current);
            }
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dollars_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // show changes in toast
        Toast.makeText(MainActivity.this, // getApplicationContext()
                text,
                Toast.LENGTH_LONG).show();

//        // show changes in history bar
//        TextView textHistory = (TextView) findViewById(R.id.text_history);
//        textHistory.setText(text);
    }

    /*
     * Cancel action
     */
    public void cancelAction() {
//        // debugging status bar
//        TextView textNone = (TextView) findViewById(R.id.text_none);
//        textNone.setText("Canceling ...");

        // claer spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dollars_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // clear beverage selection
        radioGroup.clearCheck();

        // clear current
        TextView textCurrent = (TextView) findViewById(R.id.text_current);
        double current = Double.parseDouble(textCurrent.getText().toString().substring(1));
        textCurrent.setText("$ 0.00");

        // dispense changes
        String text = getChanges(current);

        // show changes in toast
        Toast.makeText(MainActivity.this, // getApplicationContext()
                text,
                Toast.LENGTH_SHORT).show();

//        // show changes in history bar
//        TextView textHistory = (TextView) findViewById(R.id.text_history);
//        textHistory.setText(text);
    }

    /*
     * Get changes
     */
    public String getChanges(double current) {
        // eg: $ 1.00 * 2, $ 0.25 * 1
        int cur = (int) (Math.round(current * 100));
        String text = "Please receive changes:";

        if (cur >= 20 * 100) {
            text += "\n" + cur / 2000 + "* $20.00";
            cur %= 2000;
        }

        if (cur >= 10 * 100) {
            text += "\n" + cur / 1000 + "* $10.00";
            cur %= 1000;
        }

        if (cur >= 5 * 100) {
            text += "\n" + cur / 500 + "* $ 5.00";
            cur %= 500;
        }

        if (cur >= 1 * 100) {
            text += "\n" + cur / 100 + "* $ 1.00";
            cur %= 100;
        }

        if (cur >= 50) {
            text += "\n" + cur / 50 + "* $ 0.50";
            cur %= 50;
        }

        if (cur >= 25) {
            text += "\n" + cur / 25 + "* $ 0.25";
            cur %= 25;
        }

        if (cur >= 10) {
            text += "\n" + cur / 10 + "* $ 0.10";
            cur %= 10;
        }

        if (cur >= 0.05 * 100) {
            text += "\n" + cur / 5 + "* $ 0.05";
            cur %= 5;
        }

        if (cur >= 1) {
            text += "\n" + cur + "* $ 0.01";
        }

        return text;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.chicago.peng.vendingmachine_v1_8/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.chicago.peng.vendingmachine_v1_8/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
