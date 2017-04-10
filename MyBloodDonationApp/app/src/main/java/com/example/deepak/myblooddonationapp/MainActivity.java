package com.example.deepak.myblooddonationapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void logIn(View view)
    {
        Intent i=new Intent(this,LogInActivity.class);
        startActivity(i);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
   //     Toast.makeText(MainActivity.this, "hahah", Toast.LENGTH_SHORT).show();
        Intent in=new Intent(this,HelpActivity.class);
        startActivity(in);
        return super.onOptionsItemSelected(item);
    }

    public void signup(View view)
    {
        Intent in=new Intent(this,SignUpActivity.class);
        startActivity(in);
    }



    public void hospitalCLick(View v)
    {
        Intent in=new Intent(this,HospitalSignupActivity.class);
        startActivity(in);


    }

    public void Hospital_Log_In(View v)
    {
        Intent i = new Intent(this,HospitalLoginActivity.class);
        startActivity(i);
    }


}
