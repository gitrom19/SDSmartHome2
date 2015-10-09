package com.skidata.sdsmarthome2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DisplayPinView extends AppCompatActivity {
    public final static String PHONE_NUMBER = "com.skidata.sdsmarthome.PHONE_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pin_view);
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra(PHONE_NUMBER);
        Log.d("DisplayPinView", "onCreate: " + phoneNumber);

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                String phoneNumber = null;
                if (params != null && params.length > 0) {
                    phoneNumber = (String) params[0];
                }

                return getPinCode(phoneNumber);
            }

            @Override
            protected void onPostExecute (Object result) {
                setPin((String)result);
            }
        }.execute(phoneNumber);
    }

    public void setPin (String pin) {
        TextView pinTextView = (TextView) findViewById(R.id.textView_pin);
        if (pinTextView != null) {
            pinTextView.setText(pin);
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

    private String getPinCode(String phoneNumber) {

        String pinCode = null;

        Log.d("getPinCode", "**********Starting getPinCode*******");
        URLConnection connection = null;
        String query = "phoneNumber=" + phoneNumber;
        try {
            connection = new URL("http://sd-hackathon.appspot.com/PermissionServlet" + "?" + query).openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            String strResult = result.toString();
            JSONObject jOb = new JSONObject(strResult);
            pinCode = jOb.get("pin").toString();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("getPinCode", "**********PinCode for number " + phoneNumber + " is " + pinCode);
        return pinCode;
    }
}
