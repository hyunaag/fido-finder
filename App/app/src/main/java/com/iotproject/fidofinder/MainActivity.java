package com.iotproject.fidofinder;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView devLV;
    EditText deviceID;
    Button addDevice;
    ArrayList<HashMap<String,String>> deviceData;
    ArrayList<String> listKeys = new ArrayList<String>();
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference deviceDB = db.getReference("Devices");
        deviceID = findViewById(R.id.deviceID);
        String username = "user";
        devLV = findViewById(R.id.deviceList);
        deviceData = new ArrayList<>();
        String[] data = {"data","status"};
        int[] ids = {R.id.list_item_text_view,R.id.stat_img};

        simpleAdapter=new SimpleAdapter(this,deviceData, R.layout.list_item_layout, data, ids);
        devLV.setAdapter(simpleAdapter);
        devLV.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        devLV.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(),Device.class);

                        intent.putExtra("username", username);//get from db ref
                        intent.putExtra("DeviceID", String.valueOf(deviceID));
                        startActivity(intent);
                    }
                });


//        for (int i = 0; i < deviceData.size(); i++) {
//            HashMap<String,String> entry = new HashMap<>();
//            //get status from db
//            if(status = "ok") {
//                //state = "GREEN";
//                entry.put("status",R.drawable.green+"");
//            } else {
//                // state = "RED";
//                entry.put("status",R.drawable.red+"");
//            }
//            deviceData.set(i, entry);
//            simpleAdapter.notifyDataSetChanged();
//        }
        addDevice = findViewById(R.id.addDevice);

        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> entry = new HashMap<>();
                //add device to db
               String device = deviceID.getText().toString();
                String key = deviceDB.push().getKey();
                deviceID.setText("");
                deviceDB.child(key).child("deviceID").setValue(device);
                //add device to device list
                entry.put("data", device);
                deviceData.add(entry);
                simpleAdapter.notifyDataSetChanged();
            }
        });
       addChildEventListener();
    }

    private void addChildEventListener() {
        ChildEventListener childListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                simpleAdapter.add(
                        (String) dataSnapshot.child("deviceID").getValue());

                listKeys.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = listKeys.indexOf(key);

                if (index != -1) {
                    listItems.remove(index);
                    listKeys.remove(index);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        dbRef.addChildEventListener(childListener);
    }



    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }



    public void buzzAll(View view) {
        //buzzAll devices in list
    }
}