package com.iotproject.fidofinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Device extends AppCompatActivity {

    TextView devIdLbl, distance, lastLoc, timestamp, moving;
    ImageView status;
    Button buzzer;
    private float latitude, longitude;
    String username, deviceID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        Intent intent = getIntent();
        username = intent.getExtras().getString("username");
        deviceID = intent.getExtras().getString("DeviceID");

        devIdLbl = findViewById(R.id.deviceIDlbl);
        distance = findViewById(R.id.distance);
        lastLoc = findViewById(R.id.lastLoc);
        timestamp = findViewById(R.id.timeStamp);
        moving = findViewById(R.id.movingBool);
        status = findViewById(R.id.status);
        buzzer = findViewById(R.id.buzzer);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference deviceDB = db.getReference("Devices");
    }

    public void buzzer(View view) {
        //send buzz
    }
}