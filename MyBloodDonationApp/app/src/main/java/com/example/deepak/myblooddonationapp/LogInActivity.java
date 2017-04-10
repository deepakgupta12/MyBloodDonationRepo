package com.example.deepak.myblooddonationapp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {
  TextView user,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        user= (TextView)findViewById(R.id.UserName);
        pass= (TextView)findViewById(R.id.Password);


    }

    public void onclickLogin(View view)
    {
        String username,password;
        username=user.getText().toString();
        password=pass.getText().toString();
        pass.setText("");
        if(!(username.equals("")||password.equals(""))) {
            LoginCheck l = new LoginCheck(this);
            l.execute(username, password);
        }
        else
        {
            Toast.makeText(LogInActivity.this, "Username or password empty", Toast.LENGTH_SHORT).show();
        }

    }
}
