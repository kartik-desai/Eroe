package com.example.eroe;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

public class EmergencyContacts extends AppCompatActivity {

    EditText c1,c2,c3,c4,c5,n1,n2,n3,n4,n5;
    String c[]= new String[5];
    String n[] = new String[5];
    RadioGroup rg;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contacts);
        getId();
        db= openOrCreateDatabase("Eroe.db", Context.MODE_PRIVATE, null);

    }

    public void getId(){
        c1 = (EditText) findViewById(R.id.editTextC1);
        c2 = (EditText) findViewById(R.id.editTextC2);
        c3 = (EditText) findViewById(R.id.editTextC3);
        c4 = (EditText) findViewById(R.id.editTextC4);
        c5 = (EditText) findViewById(R.id.editTextC5);
        n1 = (EditText) findViewById(R.id.editTextN1);
        n2 = (EditText) findViewById(R.id.editTextN2);
        n3 = (EditText) findViewById(R.id.editTextN3);
        n4 = (EditText) findViewById(R.id.editTextN4);
        n5 = (EditText) findViewById(R.id.editTextN5);
    }

    public void getValue(){
        c[0] = c1.getText().toString();
        c[1] = c2.getText().toString();
        c[2] = c3.getText().toString();
        c[3] = c4.getText().toString();
        c[4] = c5.getText().toString();

        n[0] = n1.getText().toString();
        n[1] = n2.getText().toString();
        n[2] = n3.getText().toString();
        n[3] = n4.getText().toString();
        n[4] = n5.getText().toString();

    }
    public void next(View view) {
        getValue();
        new EmergencyContacts.SendPostRequest().execute();
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(EmergencyContacts.this);

        protected void onPreExecute() {
            this.dialog.setMessage("Please wait...");
            this.dialog.show();
        }

        protected String doInBackground(String... arg0) {
            try {
                String file = "addEmer.php";
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("aadhar", UserDatabase.getAadhar(db));

                postDataParams.put("n1",n[0]);
                postDataParams.put("c1",c[0]);

                postDataParams.put("n2",n[1]);
                postDataParams.put("c2",c[1]);

                postDataParams.put("n3",n[2]);
                postDataParams.put("c3",c[2]);

                postDataParams.put("n4",n[3]);
                postDataParams.put("c4",c[3]);

                postDataParams.put("n5",n[4]);
                postDataParams.put("c5",c[4]);


                Log.e("params", postDataParams.toString());
                return (com.example.eroe.SendData.sendData(file, postDataParams));

            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            UserDatabase.putDatas(db,(UserDatabase.getAadhar(db)),c);
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();
            if(result.contains("Successful")) {
                Intent i = new Intent(EmergencyContacts.this,Landing.class);
                startActivity(i);
            }
        }
    }
}
