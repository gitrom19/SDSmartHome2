package com.skidata.sdsmarthome2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
public class DisplayAuthGenerator extends AppCompatActivity  {

    public final static String PHONE_NUMBER = "com.skidata.sdsmarthome.PHONE_NUMBER";
    public final static String PERMISSION_TYP = "com.skidata.sdsmarthome.PERMISSION_TYP";
    private String permissionType = "OneTimePermission";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("generateAuth", "**********Starting*******");

        setContentView(R.layout.activity_display_gernerateauth_view);

        Spinner spinner = (Spinner) findViewById(R.id.permTypeSpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.auth_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        Button sendButton = (Button) findViewById(R.id.button_ok);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText eText = (EditText) findViewById(R.id.editText_phoneNumber);
                Spinner spinner = (Spinner) findViewById(R.id.permTypeSpinner);
                String phoneNumber = eText.getText().toString();
                String type = (String)spinner.getSelectedItem();
                Log.d("phoneNumber", "**********phoneNumber: " + phoneNumber + " type = " + type);

                executeGenerateAuth(phoneNumber, type);

            }
        });

    }

    public void executeGenerateAuth(String phoneNumber, String permissionType) {
        Intent intent = getIntent();

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

        }.execute(phoneNumber, permissionType);
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

