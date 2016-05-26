package com.chicago.peng.vendingmachine_v1_8;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class BuyingInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buying_info);

        // receive intent with data
        Intent mIntent = getIntent();
        String beverage = mIntent.getStringExtra("EXTRA_BEVERAGE");

        int image_id = R.drawable.image_face;
        switch (beverage) {
            case "Coke":
                image_id = R.drawable.image_coke;
                break;
            case "Pepsi":
                image_id = R.drawable.image_pepsi;
                break;
            case "Fireball":
                image_id = R.drawable.image_fireball;
                break;
            case "Sprite":
                image_id = R.drawable.image_sprite;
                break;
            case "Blue Guy":
                image_id = R.drawable.image_blue_guy;
                break;
            default:
                break;
        }

        showText(beverage);

        displayPicture(image_id);

        playSound();

        // Execute some code after 4 seconds have passed
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 5000);

    }

    // show text info
    public void showText(String text) {
        text += " got! Enjoy!";

        TextView textWelcome = (TextView) findViewById(R.id.text_welcome);
        textWelcome.setText(text);
    }

    public void displayPicture(int image_id) {
        ImageView image = (ImageView) findViewById(R.id.image_beverage);
        image.setImageResource(image_id);
    }

    public void playSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.bottle_down);
        mediaPlayer.start();
    }
}
