package com.example.eroe;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.widget.Toast;

public class SendSMSService extends Service {
    private String[] contactList=null; //TODO: stores the number of the emergency contacts
    private String senderName;
    private String location;
    private String SOS_MESSAGE=" IS IN AN EMERGENCY AND NEEDS YOUR HELP. PLEASE HELP THEM." +
            "THE ALERT WAS SENT FROM LOCATION ";
    public SendSMSService() {


    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }


    public String getSenderName() {
        return senderName;
    }

    //TODO: Initiliase the list with contact numbers of the user
    public void setContactList(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
        //call setContactList
        SQLiteDatabase db =  openOrCreateDatabase("Eroe.db", Context.MODE_PRIVATE, null);
        contactList = UserDatabase.getContacts(db);
        if(contactList==null){
            //filling DUMMY values
            String number="9819931901";
            setSenderName("DG");
            sendMessage(number,"Location unavailable");

        }
        else{


            for(int i=0;i<contactList.length;i++){
                if(location==null)
                    sendMessage(contactList[i],"Location unavailable");
                else
                    sendMessage(contactList[i],location);
            }
        }
        Toast.makeText(getApplicationContext(), "Sent SOS Messages", Toast.LENGTH_LONG).show();
        this.stopSelf();//FINISH the service
    }

    public void sendMessage(String number,String location){
        String messageToSend= getSenderName()+SOS_MESSAGE+location;
        SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}