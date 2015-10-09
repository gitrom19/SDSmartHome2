package com.skidata.sdsmarthome2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class Rooms extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        Spinner spinner = (Spinner) findViewById(R.id.rooms_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rooms_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i("Rooms", "onItemsSelected position: " + position + " id: " + id);
        ImageView image = (ImageView) findViewById(R.id.imageView_rooms);
        switch (position) {
            case 0:
                if (image != null) {
                    image.setImageResource(R.drawable.bathroom);
                } else {
                    Log.e("Rooms", "onItemSelected could not get ImageView resource to display room");
                }
                break;
            case 1:
                if (image != null) {
                    image.setImageResource(R.drawable.bedroom);
                } else {
                    Log.e("Rooms", "onItemSelected could not get ImageView resource to display room");
                }
                break;
            case 2:
                if (image != null) {
                    image.setImageResource(R.drawable.kitchen);
                } else {
                    Log.e("Rooms", "onItemSelected could not get ImageView resource to display room");
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
