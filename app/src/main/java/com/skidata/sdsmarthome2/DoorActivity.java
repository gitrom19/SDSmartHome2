package com.skidata.sdsmarthome2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DoorActivity extends AppCompatActivity {
    Button doorStatusButton;
    boolean closed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door);
        doorStatusButton = (Button) findViewById(R.id.button_maindoor);
        doorStatusButton.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.dooropen1,0,0);
        closed = false;
    }

    public void changeDoorLockStatus(View view) {
        if (doorStatusButton != null) {
            doorStatusButton.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.closeddoor1,0,0);
        }
    }
}
