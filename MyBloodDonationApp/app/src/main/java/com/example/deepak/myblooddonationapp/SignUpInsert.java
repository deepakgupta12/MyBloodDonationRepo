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
 * Created by Deepak on 9/29/2016.
 */
public class SignUpInsert extends AsyncTask<String,Void,String> {
    Context context;
    private ProgressDialog dialog;
    SignUpInsert(Context c)
    {
        this.context=c;
        dialog= new ProgressDialog(c);
        dialog.setCanceledOnTouchOutside(false);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Wait While SignUp......");
        dialog.show();
    }

    @Override
    protected String doInBackground(String args[])
    {
        String result=null;

        String username=args[0];
        String password=args[1];
        String bloodgroup=args[2];
        String contact=args[3];
        String address=args[4];
        String city=args[5];
        String Email=args[6];

        BufferedReader bufferedReader;
        String data;
        String link;

        try
        {
            data="?username="+ URLEncoder.encode(username,"UTF-8");
            data += "&password="+URLEncoder.encode(password,"UTF-8");
            data += "&bloodgroup="+URLEncoder.encode(bloodgroup,"UTF-8");
            data += "&contact="+URLEncoder.encode(contact,"UTF-8");
            data += "&address="+URLEncoder.encode(address,"UTF-8");
            data += "&city="+URLEncoder.encode(city,"UTF-8");
            data += "&email="+URLEncoder.encode(Email,"UTF-8");
            link=context.getString(R.string.link)+"/blood/signup.php"+data;

            URL url=new URL(link);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            bufferedReader=new BufferedReader(new InputStreamReader(con.getInputStream()));
            result=bufferedReader.readLine();
            return result;
        }catch (Exception e)
        {
            return new String(""+e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        String jsonStr=result;
        dialog.dismiss();
        if(jsonStr!=null)
        {
            try
            {
                JSONObject jsonObj=new JSONObject(jsonStr);
            //    Toast.makeText(context, jsonObj.toString(), Toast.LENGTH_SHORT).show();
                String query_result=jsonObj.getString("query_result");
                //String query_res2=jsonObj.getString("num_rows");

                if (query_result.equals("SUCCESS"))
                {
                    Toast.makeText(context, "Done Now Sign In ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.setClass(context,LogInActivity.class);
                    context.startActivity(i);

                }else if(query_result.equals("FAILURE"))
                {
                    Toast.makeText(context, "Data Inserted unSuccessully", Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(context, "Could connect to remote database", Toast.LENGTH_SHORT).show();

                }
            }catch (JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(context, "Error in parsing JSON DATA", Toast.LENGTH_SHORT).show();
            }
        }else
        {
            Toast.makeText(context, " Could nt get any json data  ", Toast.LENGTH_SHORT).show();
        }



    }
}
