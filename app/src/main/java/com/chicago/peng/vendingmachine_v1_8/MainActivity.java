package com.chicago.peng.vendingmachine_v1_8;

import android.graphics.Color;
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

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;

    HashMap<Integer, Beverage> beverage_map = new HashMap<>();
    HashMap<Integer, Integer> quantity_map = new HashMap<>();

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

        /* Initialize Radio Group and attach click handler */
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        /* Attach CheckedChangeListener to radio group */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if(null!=rb && checkedId > -1){
                    Toast.makeText(MainActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        final Button button_insert = (Button) findViewById(R.id.button_insert);
        button_insert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.text_history);
                textView.setText("Inserting ...");

                Spinner spinner = (Spinner)findViewById(R.id.dollars_spinner);
                String text = spinner.getSelectedItem().toString();

                Toast.makeText(MainActivity.this, // getApplicationContext()
                        text,
                        Toast.LENGTH_SHORT).show();

                TextView textCurrent = (TextView) findViewById(R.id.text_current);
                double current = Double.parseDouble(textCurrent.getText().toString().substring(1));
                double inserted = Double.parseDouble(text.substring(1));
                current += inserted;
                String s = String.format("%.2f", current);
                textCurrent.setText("$ " + s);
            }
        });

        final Button button_buy = (Button) findViewById(R.id.button_buy);
        button_buy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.text_history);
                textView.setText("Buying ...");

                String text; // info to be shown as status

                TextView textCurrent = (TextView) findViewById(R.id.text_current);
                double current = Double.parseDouble(textCurrent.getText().toString().substring(1));

                int id = radioGroup.getCheckedRadioButtonId();
                if(id == -1) {
                    text = "You need to choose one beverage first!";
                    Toast.makeText(MainActivity.this, // getApplicationContext()
                            text,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton rb = (RadioButton) radioGroup.findViewById(id);
                Beverage beverage = beverage_map.get(id);
                double cost = beverage.cost;
                int quantity = beverage.quantity;

                if(quantity == 0) {
                    text = "Sorry! Sold out.";
                } else {
                    if(cost > current) {
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

                Toast.makeText(MainActivity.this, // getApplicationContext()
                        text,
                        Toast.LENGTH_SHORT).show();
            }
        });

        final Button button_cancel = (Button) findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.text_history);
                textView.setText("Canceling ...");

                // clear current
                TextView textCurrent = (TextView) findViewById(R.id.text_current);
                double current = Double.parseDouble(textCurrent.getText().toString().substring(1));
                textCurrent.setText("$ 0.00");

                String text = getChanges(current);
                Toast.makeText(MainActivity.this, // getApplicationContext()
                        text,
                        Toast.LENGTH_SHORT).show();

                TextView textHistory = (TextView) findViewById(R.id.text_history);
                textHistory.setText(text);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.dollars_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dollars_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton1:
                if (checked)

                    break;
            case R.id.radioButton2:
                if (checked)

                    break;
            case R.id.radioButton3:
                if (checked)

                    break;
            case R.id.radioButton4:
                if (checked)

                    break;
            case R.id.radioButton5:
                if (checked)

                    break;
            default:
                    break;
        }
    }

    public String getChanges(double current) {
        // eg: $ 1.00 * 2, $ 0.25 * 1
        int cur = (int)(Math.round(current * 100));
        String text = "Please receive changes:";

        if(cur >= 20*100) {
            text += "\n" + cur/2000 + "* $20.00";
            cur %= 2000;
        }

        if(cur >= 10*100) {
            text += "\n" + cur/1000 + "* $10.00";
            cur %= 1000;
        }

        if(cur >= 5*100) {
            text += "\n" + cur/500 + "* $ 5.00";
            cur %= 500;
        }

        if(cur >= 1*100) {
            text += "\n" + cur/100 + "* $ 1.00";
            cur %= 100;
        }

        if(cur >= 50) {
            text += "\n" + cur/50 + "* $ 0.50";
            cur %= 50;
        }

        if(cur >= 25) {
            text += "\n" + cur/25 + "* $ 0.25";
            cur %= 25;
        }

        if(cur >= 10) {
            text += "\n" + cur/10 + "* $ 0.10";
            cur %= 10;
        }

        if(cur >= 0.05*100) {
            text += "\n" + cur/5 + "* $ 0.05";
            cur %= 5;
        }

        if(cur >= 1) {
            text += "\n" + cur + "* $ 0.01";
        }

        return text;
    }
}
