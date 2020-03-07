package com.example.eroe;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText name, phone, age, email, address, city;
    String w, activity, wei, n, p, a, h, c, gender;
    RadioGroup rg;
    TextView next,back;
    RadioButton r,m,f;
    Pattern regex = Pattern.compile("[$&+,:;=\\\\?#|/'<>^*()@.%!-]");
    Pattern regex2 = Pattern.compile("[0-9]+");
    SQLiteDatabase d;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void getId() {
        name = (EditText) findViewById(R.id.editTextName);
        phone = (EditText) findViewById(R.id.editTextPhone);
        age = (EditText) findViewById(R.id.editTextAge);
        email = (EditText) findViewById(R.id.editTextEmail);
        address = (EditText) findViewById(R.id.editTextAddress);
        m= (RadioButton) findViewById(R.id.radioMale);
        f= (RadioButton) findViewById(R.id.radioFemale);
        rg = (RadioGroup) findViewById(R.id.radioSex);
        next= (TextView) findViewById(R.id.next);
    }

    public void verifyAadhar(View view) {
        
    }
    public void onRadioButtonClicked(View view) {
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioMale:m.setButtonDrawable(R.drawable.malecolor);
                f.setButtonDrawable(R.drawable.femaleblack);
                break;
            case R.id.radioFemale:f.setButtonDrawable(R.drawable.femalecolor);
                m.setButtonDrawable(R.drawable.malebalck);
                break;
        }
    }
}
