package com.example.deepak.myblooddonationapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by standarduser on 23/02/17.
 */

public class HospitalLoginCheck extends AsyncTask<String,Void,String> {
    private  Context context;
    private String user;
    private ProgressDialog dialog;
    private String link;

    HospitalLoginCheck(Context context)
    {
        this.context=context;
        dialog= new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Wait while Logging.......");
        dialog.show();
    }

    @Override
    protected String doInBackground(String args[]) {

        String result=null;
         user=args[0];
        String password=args[1];
        String data;
        BufferedReader bufferedReader;
        try
        {
            data="?username="+ URLEncoder.encode(user,"UTF-8");
            data+="&password="+URLEncoder.encode(password,"UTF-8");
            link =context.getString(R.string.link)+"/blood/HospitalCheck.php"+data;
            URL url=new URL(link);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            bufferedReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
            result=bufferedReader.readLine();
            return result;
        }catch (Exception e)
        {
            return new String("Exception" + e.getMessage());
        }
    }


    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        try {
            JSONObject object= new JSONObject(s);
            String query_result=object.getString("query_result");
            if(query_result.equals("OK"))
            {
                dialog.dismiss();
                Intent i=new Intent(context,HospitalDashboardActivity.class);
                i.putExtra("Current_User",user);
                context.startActivity(i);
            }else if(query_result.equals("NotOK"))
            {
                Toast.makeText(context, "username or password is incorrect ", Toast.LENGTH_SHORT).show();

            }else
            {
                Toast.makeText(context, "Could not connect to remote Database", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
