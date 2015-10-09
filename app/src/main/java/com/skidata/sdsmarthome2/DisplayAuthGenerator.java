package com.skidata.sdsmarthome2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by hannesgraf on 09.10.15.
 */
public class DisplayAuthGenerator extends AppCompatActivity {

    public final static String PHONE_NUMBER = "com.skidata.sdsmarthome.PHONE_NUMBER";
    public final static String PERMISSION_TYP = "com.skidata.sdsmarthome.PERMISSION_TYP";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra(PHONE_NUMBER);
        String permissionType = intent.getStringExtra(PERMISSION_TYP);


        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                String phoneNumber = null;
                String permissionType = null;
                if (params != null && params.length > 0) {
                    phoneNumber = (String) params[0];
                    permissionType = (String) params[1];
                }

                return generateAuth(phoneNumber, permissionType);
            }

            @Override
            protected void onPostExecute (Object result) {
                setAuthResult((String) result);
            }
        }.execute(phoneNumber, permissionType);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void setAuthResult (String state) {
        TextView autGenTextView = (TextView) findViewById(R.id.textView_authGenView);
        if (autGenTextView != null) {
            autGenTextView.setText(state);
        } else {
            Log.e("setAuthResult", "onCreate Did not get the TextView");
        }
    }

    private String generateAuth(String phoneNumber, String permissionType ) {

        String resultCode = null;

        Log.d("generateAuth", "**********Starting getPinCode*******");
        URLConnection connection = null;
        String query = "phoneNumber=" + phoneNumber + "&permissionId=" + permissionType;
        try {
            connection = new URL("http://sd-hackathon.appspot.com/SetPermissionServlet" + "?" + query).openConnection();
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
            resultCode = jOb.get("result").toString();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("generateAuth", "**********generate authentication " + permissionType +" for number " + phoneNumber +
                " finished with status: " + resultCode);
        return resultCode;
    }
}

