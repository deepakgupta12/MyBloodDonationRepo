package com.example.deepak.myblooddonationapp;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Deepak on 11/15/2016.
 */
public class UpdateValues extends AsyncTask<String ,Void, String > {
    private Context context;
    String link;
    public UpdateValues(Context context)
    {
        this.context=context;
    }

    @Override
    protected String doInBackground(String[] args) {
        String result=null;

        String username=args[0];
        String password=args[1];
        String bloodgroup=args[2];
        String contact=args[3];
        String address=args[4];
        String city=args[5];

        String data;
        BufferedReader bufferedReader;



        try
        {
            data="?username="+ URLEncoder.encode(username,"UTF-8");
            data += "&password="+URLEncoder.encode(password,"UTF-8");
            data += "&BG="+URLEncoder.encode(bloodgroup,"UTF-8");
            data += "&contact="+URLEncoder.encode(contact,"UTF-8");
            data += "&address="+URLEncoder.encode(address,"UTF-8");
            data += "&city="+URLEncoder.encode(city,"UTF-8");
            link=context.getString(R.string.link)+"/blood/update.php"+data;
            URL url=new  URL(link);

            HttpURLConnection con= (HttpURLConnection) url.openConnection();


            bufferedReader= new BufferedReader(new InputStreamReader(con.getInputStream()));
            result=bufferedReader.readLine();
        }catch (Exception e)
        {
            return new String("Exception  :" +e.getMessage());
        }


        return result;
    }


    @Override
    protected void onPostExecute(String result) {

        String jsonStr=result;
        Toast.makeText(context, ""+link, Toast.LENGTH_LONG).show();
        Toast.makeText(context, "+"+jsonStr, Toast.LENGTH_SHORT).show();
    }
}
