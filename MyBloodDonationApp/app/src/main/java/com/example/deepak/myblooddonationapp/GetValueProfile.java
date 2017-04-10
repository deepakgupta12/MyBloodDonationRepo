package com.example.deepak.myblooddonationapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Deepak on 11/13/2016.
 */
public class GetValueProfile extends AsyncTask<String ,Void,String> {
private  Context context;
    String link;
    String username;
    ProgressDialog dialog;

    public GetValueProfile(Context context)
{
    this.context=context;
    dialog= new ProgressDialog(context);
    dialog.setCanceledOnTouchOutside(false);
}

    @Override
    protected void onPreExecute() {
        dialog.setMessage("wait while Loading.......");
        dialog.show();
    }

    @Override
    protected String doInBackground(String[] args) {
        String result=null;
        username=args[0];
        String data;
        BufferedReader bufferedReader;

        try {

            data="?email="+ URLEncoder.encode(username,"UTF-8");
            link=context.getString(R.string.link)+"/blood/values.php"+data;
            URL url=new  URL(link);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            bufferedReader= new BufferedReader(new InputStreamReader(con.getInputStream()));
            result=bufferedReader.readLine();
            return result;

        }catch (Exception e)
        {
            return new String("Exception" +e.getMessage());
        }


    }


    @Override
    protected void onPostExecute(String result) {
        dialog.dismiss();

      String jsonStr=result;
        Toast.makeText(context, ""+jsonStr, Toast.LENGTH_SHORT).show();

        try {
            JSONObject jsonObject= new JSONObject(result);
           JSONArray inf= jsonObject.getJSONArray("stuff");
            Intent in=new Intent(context,ProfileAcitvity.class);
            for (int i=0;i<inf.length();i++)
            {
                JSONObject c=inf.getJSONObject(i);
                String name=c.getString("name");
                in.putExtra("user",name);
                String addr=c.getString("addr");
                in.putExtra("addr",addr);
                String contact=c.getString("contact");
                in.putExtra("contact",contact);
                String B_group=c.getString("B_Group");
                in.putExtra("B_Group",B_group);
                String city=c.getString("city");
                in.putExtra("city",city);
                String email=c.getString("email");
                in.putExtra("email",email);
            }

            context.startActivity(in);




        } catch (Exception e) {
            Toast.makeText(context, "ERROR"+e.getMessage(), Toast.LENGTH_SHORT).show();;
        }


    }
}
