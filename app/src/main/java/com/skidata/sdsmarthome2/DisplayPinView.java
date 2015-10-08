package com.skidata.sdsmarthome2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class DisplayPinView extends AppCompatActivity {
    public final static String PIN_TEXT = "com.skidata.sdsmarthome.PIN_TEXT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pin_view);
        Intent intent = getIntent();
        TextView pinTextView = (TextView) findViewById(R.id.textView_pin);
        Log.d("DisplayPinView", "onCreate: " + intent.getStringExtra(PIN_TEXT));
        String pinText = intent.getStringExtra(PIN_TEXT);
        if (pinTextView != null) {
            pinTextView.setText(pinText);
        } else {
            Log.e("DisplayPinView", "onCreate Did not get the TextView");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
