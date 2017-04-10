package com.example.deepak.myblooddonationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HospitalLoginActivity extends AppCompatActivity {


    private EditText username;
    private  EditText Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_hospital);
        username = (EditText) findViewById(R.id.Login_H_Name);
        Password= (EditText) findViewById(R.id.Login_H_Password);
    }


    public void Hospital_Log_In_Button(View view)
    {

        if(!(username.getText().toString().equals("") || Password.getText().toString().equals(""))) {
            String password=Password.getText().toString();
            Password.setText("");
            HospitalLoginCheck hospitalLoginCheck = new HospitalLoginCheck(this);
            hospitalLoginCheck.execute(username.getText().toString(), password);

        }
        else
        {
            if(username.getText().toString().equals(""))
            username.setError("Username is Empty");
            else
            Password.setError("password is empty ");
        }

    }
}
