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
 * Created by Deepak on 9/18/2016.
 */
public class LoginCheck extends AsyncTask<String,Void,String> {
    String link;
    String user_name;
    private Context context;
    private ProgressDialog dialog;

    public LoginCheck (Context context){this.context=context;
        dialog = new ProgressDialog(context);
    dialog.setCanceledOnTouchOutside(false);}


    @Override
    protected void onPreExecute() {
        dialog.setMessage("Wait While Login......");
        dialog.show();

    }

    @Override
    protected String doInBackground(String[] args) {
        String result;
        String username = args[0];
        String password=args[1];
        user_name=args[0];
        String data;
        BufferedReader bufferedReader;

        try
        {
            data="?email="+ URLEncoder.encode(username,"UTF-8");
            data+="&password="+URLEncoder.encode(password,"UTF-8");
            link=context.getString(R.string.link)+"/blood/check.php"+data;
            URL url=new  URL(link);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            bufferedReader= new BufferedReader(new InputStreamReader(con.getInputStream()));
            result=bufferedReader.readLine();

            return result;
        }catch(Exception e)
        {
            return new String("Exception"+e.getMessage());
        }


    }

    @Override
    protected void onPostExecute(String result) {
        String jsonStr=result;
        dialog.dismiss();
        if (jsonStr!=null)
        {
            try
            {
                JSONObject jsonObject=new JSONObject(jsonStr);

                String query_result=jsonObject.getString("query_result");
                if(query_result.equals("OK"))
                {

                    Intent i=new Intent(context,DashBoardActivity.class);
                    i.putExtra("user",user_name);
                    context.startActivity(i);
                }else if(query_result.equals("NotOK"))
                {
                    Toast.makeText(context, "username or password is incorrect ", Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(context, "Could not connect to remote Database", Toast.LENGTH_SHORT).show();
                }

            }catch(JSONException e)
            {
                Toast.makeText(context, " 2 Exception : "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
