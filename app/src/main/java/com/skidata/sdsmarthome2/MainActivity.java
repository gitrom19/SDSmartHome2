package com.skidata.sdsmarthome2;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void requestPin(View view) {
        Log.d("MainActivity", "requestPin: Entered");
        Intent rpIntent = new Intent(this, DisplayPinView.class);
        rpIntent.putExtra(DisplayPinView.PHONE_NUMBER, SettingsActivity.phoneNumberSetting);


        PinHandler getPin = new PinHandler();
        AlertDialog mDialog = new AlertDialog.Builder(this).setNeutralButton("Ok", null).create();
        mDialog.setTitle("Generated PIN");

        getPin.getPin("666");

        while (getPin.getPin() == null) {
            Log.d("MainActivity", "Still " + getPin.getPin());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        mDialog.setMessage("Your Pin: " + getPin.getPin());
        mDialog.show();

        //startActivity(rpIntent);
    }

    public void generateAuth(View view) {
        Log.d("MainActivity", "generateAuth: Entered");
        Intent rpIntent = new Intent(this, DisplayAuthGenerator.class);
        rpIntent.putExtra(DisplayAuthGenerator.PHONE_NUMBER, SettingsActivity.phoneNumberSetting);
        rpIntent.putExtra(DisplayAuthGenerator.PERMISSION_TYP, SettingsActivity.phoneNumberSetting);
        startActivity(rpIntent);
    }

    public void openSettings(MenuItem item) {
        Log.d("MainActivity", "openSettings Entered");
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Hello")
                .setTitle("nice one");

// 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
        startActivity(settingsIntent);
    }

    public void openRoomsView(View view) {
        Intent openRoomsViewIntent = new Intent(this, Rooms.class);
        startActivity(openRoomsViewIntent);
    }
}
