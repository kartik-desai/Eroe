package com.example.eroe;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText name, phone, age, email, address,aadhar;
    String adhar, n, p, a, h, c, gender,e,addresss;
    RadioGroup rg;
    SQLiteDatabase db;
    TextView next,back;
    RadioButton r,m,f;
    Pattern regex = Pattern.compile("[$&+,:;=\\\\?#|/'<>^*()@.%!-]");
    Pattern regex2 = Pattern.compile("[0-9]+");
    SQLiteDatabase d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db= openOrCreateDatabase("Eroe.db", Context.MODE_PRIVATE, null);

        UserDatabase.makeTable(db);
        //UserDatabase.dropTable(db);

        getId();
        //Log.d("dd", UserDatabase.getAadhar(db));
        if(!UserDatabase.getAadhar(db).equals("null")){
            String contacts[] = UserDatabase.getContacts(db);
            //if(contacts == NULL )
            Log.d("ss",contacts[0]+"f");
            if(!contacts[0].equals("null")) {
                Intent intent = new Intent(MainActivity.this, Landing.class);
                startActivity(intent);
            }
            else{
                Intent i = new Intent(MainActivity.this, EmergencyContacts.class);
                startActivity(i);
            }
        }

    }

    private boolean getValue() {
        int i=0;
        boolean val=true;
        n = name.getText().toString();//name
        p = phone.getText().toString();//phone
        a = age.getText().toString();//age
        e = email.getText().toString();//name
        adhar = aadhar.getText().toString();//phone
        addresss = address.getText().toString();
        int id = rg.getCheckedRadioButtonId();
        r = (RadioButton) findViewById(id);//gender
        gender = r.getText().toString();
        return val;
    }


    private void getId() {
        name = (EditText) findViewById(R.id.editTextName);
        phone = (EditText) findViewById(R.id.editTextPhone);
        aadhar = findViewById(R.id.editTextAadhar);
        age = (EditText) findViewById(R.id.editTextAge);
        email = (EditText) findViewById(R.id.editTextEmail);
        address = (EditText) findViewById(R.id.editTextAddress);
        m= (RadioButton) findViewById(R.id.radioMale);
        f= (RadioButton) findViewById(R.id.radioFemale);
        rg = (RadioGroup) findViewById(R.id.radioSex);
        next= (TextView) findViewById(R.id.next);
    }

    public void next(View view) {
        getValue();
        new SendPostRequest().execute();
    }
    public void onRadioButtonClicked(View view) {
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioMale : m.setButtonDrawable(R.drawable.malecolor);
                f.setButtonDrawable(R.drawable.femaleblack);
                break;
            case R.id.radioFemale : f.setButtonDrawable(R.drawable.femalecolor);
                m.setButtonDrawable(R.drawable.malebalck);
                break;
        }
    }
    public class SendPostRequest extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        protected void onPreExecute() {
            this.dialog.setMessage("Please wait...");
            this.dialog.show();
        }

        protected String doInBackground(String... arg0) {
            try {
                String file = "addData.php";
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("email", e);
                postDataParams.put("name", n);
                postDataParams.put("phone", p);
                postDataParams.put("age", a);
                postDataParams.put("aadhar",adhar);
                postDataParams.put("address", addresss);
                postDataParams.put("gender", gender);

                Log.e("params", postDataParams.toString());
                return (com.example.eroe.SendData.sendData(file, postDataParams));

            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            UserDatabase.putData(db,adhar,n);
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, EmergencyContacts.class);
            startActivity(i);
        }
    }

}


