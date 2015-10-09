package com.skidata.sdsmarthome2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {
    public static String phoneNumberSetting = "06641234567";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        EditText et = (EditText) findViewById(R.id.editText_phone);
        et.setText(phoneNumberSetting);
    }

    public void setPhoneNumber(View view) {
        String phoneNumber = ((EditText)findViewById(R.id.editText_phone)).getText().toString();
        Log.i("SettingsActivity", "setPhoneNumber The current number is " + phoneNumber);
        phoneNumberSetting = phoneNumber;
        this.finish();
    }
}
