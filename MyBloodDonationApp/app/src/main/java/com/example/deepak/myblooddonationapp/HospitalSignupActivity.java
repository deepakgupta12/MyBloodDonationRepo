package com.example.deepak.myblooddonationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HospitalSignupActivity extends AppCompatActivity {
    EditText Hname,
            HPassword,
            Haddress,
            Hcity, Hcontact,
            B_plus_unit,
            B_minus_unit, A_plus_unit,
            A_minus_unit, AB_plus_unit,
            AB_minus_unit, O_plus_unit,
            O_minus_unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_signup);

        Hname = (EditText) findViewById(R.id.H_Name);
        Haddress = (EditText) findViewById(R.id.H_Address);
        HPassword= (EditText) findViewById(R.id.H_Password);
        Hcity = (EditText) findViewById(R.id.H_City);
        Hcontact = (EditText) findViewById(R.id.H_Contact);
        B_plus_unit = (EditText) findViewById(R.id.B_plus_unit);
        B_minus_unit = (EditText) findViewById(R.id.B_minus_unit);
        A_plus_unit = (EditText) findViewById(R.id.A_plus_unit);
        A_minus_unit = (EditText) findViewById(R.id.A_minus_unit);
        AB_plus_unit = (EditText) findViewById(R.id.AB_plus_unit);
        AB_minus_unit = (EditText) findViewById(R.id.AB_minus_unit);
        O_plus_unit = (EditText) findViewById(R.id.O_plus_unit);
        O_minus_unit = (EditText) findViewById(R.id.O_minus_unit);


    }


    public void SignupHospital(View v) {

        Toast.makeText(this, "" + Hname.getText() +
                Haddress.getText() +
                Hcity.getText() +
                Hcontact.getText() +
                B_plus_unit.getText() +
                B_minus_unit.getText() +
                A_plus_unit.getText() +
                A_minus_unit.getText() +
                AB_plus_unit.getText() +
                AB_minus_unit.getText() +
                O_plus_unit.getText() +
                O_minus_unit.getText(), Toast.LENGTH_SHORT).show();


        HospitalSignUpInsert hospitalSignUpInsert= new HospitalSignUpInsert(this);
        hospitalSignUpInsert.execute(
                Hname.getText().toString().trim(),
                Haddress.getText().toString().trim(),
                Hcity.getText().toString().trim(),
                Hcontact.getText().toString().trim(),
                B_plus_unit.getText().toString().trim(),
                B_minus_unit.getText().toString().trim(),
                A_plus_unit.getText().toString().trim(),
                A_minus_unit.getText().toString().trim(),
                AB_plus_unit.getText().toString().trim(),
                AB_minus_unit.getText().toString().trim(),
                O_plus_unit.getText().toString().trim(),
                O_minus_unit.getText().toString().trim(),
                HPassword.getText().toString().trim()

        );





    }
}
