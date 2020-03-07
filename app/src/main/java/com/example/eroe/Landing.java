package com.example.eroe;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
;import static android.content.Context.LOCATION_SERVICE;

public class Landing extends AppCompatActivity {
    private String SOS_MESSAGE=" IS IN AN EMERGENCY AND NEEDS YOUR HELP. PLEASE HELP THEM." +
            "THE ALERT WAS SENT FROM LOCATION ";

    private LocationManager locationManager;
    private LocationListener locationListener;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        locationListener = new LocationListener() {
            @RequiresApi(api = Build.VERSION_CODES.DONUT)
            @Override
            public void onLocationChanged(Location location) {
                Log.d("long", String.valueOf(location.getLongitude()));
                ActivityCompat.requestPermissions(Landing.this,new String[]{Manifest.permission.SEND_SMS},1);
                SmsManager.getDefault().sendTextMessage("09869207983",null, String.valueOf(location.getLongitude()),null,null);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME,MIN_DIST,locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
        String[] contactList=null;
        String location="";
        SQLiteDatabase db =  openOrCreateDatabase("Eroe.db", Context.MODE_PRIVATE, null);
        contactList = UserDatabase.getContacts(db);
        Log.d("f", "onCreate: "+UserDatabase.getContacts(db));
        if(contactList==null){
            //filling DUMMY values
            String number="9869207983";
            //sendMessage(number,"Location unavailable");

        }
        else{

            /*Log.d("dest",contactList[0]);
            Log.d("dest",contactList[1]);
            Log.d("dest",contactList[3]);
            Log.d("dest",contactList[4]);
            */
            for(int i=0;i<contactList.length;i++){
                if(location==null)
                    sendMessage(contactList[i],"Location unavailable");
                else
                    sendMessage(contactList[i],location);
            }
        }
        //Toast.makeText(getApplicationContext(), "Sent SOS Messages", Toast.LENGTH_LONG).show();
        //this.stopSelf();//FINISH the service
    }

    public void sendMessage(String number,String location){
        String messageToSend= SOS_MESSAGE+location;
        Log.d("dest","0"+number);
        //SmsManager.getDefault().sendTextMessage("0"+number, null, messageToSend, null,null);
    }

}
