package com.example.deepak.myblooddonationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class UpdateActivity extends AppCompatActivity {
 TextView user,pass,contact,city,addr;
    Spinner B_group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent in=getIntent();
        Bundle b=in.getExtras();

        user= (TextView) findViewById(R.id.Up_User);
        user.setText(b.getString("name"));
        pass= (TextView) findViewById(R.id.Up_pass);
        B_group= (Spinner) findViewById(R.id.Up_Bgroup);
        addr= (TextView) findViewById(R.id.addr);
        contact= (TextView) findViewById(R.id.Up_Contact);
        city= (TextView) findViewById(R.id.up_City);
    }

    public void upadateBTN(View v)
    {
    UpdateValues up=new UpdateValues(this);
        up.execute(user.getText().toString(),pass.getText().toString(),B_group.getSelectedItem().toString(),contact.getText().toString(),addr.getText().toString() ,city.getText().toString());
    }
}
