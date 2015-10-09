package com.skidata.sdsmarthome2;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by joog on 09/10/15.
 */
public class PinHandler {

    String pin;

    public void getPin(String phoneNumber){
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                String phoneNumber = null;
                if (params != null && params.length > 0) {
                    phoneNumber = (String) params[0];
                }

                return callUp(phoneNumber);
            }

            @Override
            protected void onPostExecute (Object result) {
            }
        }.execute(phoneNumber);
    }

    public String getPin(){
        return pin;
    }

    private String callUp(String phoneNumber) {

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

        pin = pinCode;
        return pinCode;
    }

}
