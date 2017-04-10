package com.example.deepak.myblooddonationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
  TextView t1,t2,t4,t5,t6,t7;
    Spinner sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        t1= (TextView) findViewById(R.id.username);
        t2= (TextView) findViewById(R.id.Password);
        sp= (Spinner) findViewById(R.id.bloodGroup);
       t4= (TextView) findViewById(R.id.phoneNo);
       t5= (TextView) findViewById(R.id.address);
        t6= (TextView) findViewById(R.id.city);
        t7= (TextView) findViewById(R.id.User_Email);



    }

    public void SignupBtn(View view)
    {
        Toast.makeText(SignUpActivity.this, ""+t1.getText()+t2.getText()+sp.getSelectedItem().toString()+t4.getText()+t5.getText()+t6.getText(), Toast.LENGTH_SHORT).show();
        SignUpInsert si=new SignUpInsert(this);
        si.execute(t1.getText().toString(),t2.getText().toString(),sp.getSelectedItem().toString(),t4.getText().toString(),t5.getText().toString(),t6.getText().toString(),t7.getText().toString());
    }


}
