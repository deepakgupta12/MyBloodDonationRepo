package com.example.deepak.myblooddonationapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by standarduser on 21/02/17.
 */

public class HospitalSignUpInsert extends AsyncTask<String,Void,String> {
    Context context;
    private String link;
    private ProgressDialog dialog;
    public HospitalSignUpInsert(Context context) {
        dialog =new ProgressDialog(context);
        this.context=context;
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Wait while Registering......");
        dialog.show();
    }

    @Override
    protected String doInBackground(String args[]) {
        String result=null;

        String Hname=args[0];
        String Haddress=args[1];
        String Hcity=args[2];
        String Hcontact=args[3];
        String B_plus_unit=args[4];
        String B_minus_unit=args[5];
        String A_plus_unit=args[6];
        String A_minus_unit=args[7];
        String AB_plus_unit=args[8];
        String AB_minus_unit=args[9];
        String O_plus_unit=args[10];
        String O_minus_unit=args[11];
        String H_password=args[12];



        BufferedReader bufferedReader;
        String data;

        try
        {
            data="?Name="+ URLEncoder.encode(Hname,"UTF-8");
            data += "&Password="+URLEncoder.encode(H_password,"UTF-8");
            data += "&Address="+URLEncoder.encode(Haddress,"UTF-8");
            data += "&City="+URLEncoder.encode(Hcity,"UTF-8");
            data += "&Contact="+URLEncoder.encode(Hcontact,"UTF-8");
            data += "&B_plus="+URLEncoder.encode(B_plus_unit,"UTF-8");
            data += "&B_minus="+URLEncoder.encode(B_minus_unit,"UTF-8");
            data += "&A_plus="+URLEncoder.encode(A_plus_unit,"UTF-8");
            data += "&A_minus="+URLEncoder.encode(A_minus_unit,"UTF-8");
            data += "&AB_plus="+URLEncoder.encode(AB_plus_unit,"UTF-8");
            data += "&AB_minus="+URLEncoder.encode(AB_minus_unit,"UTF-8");
            data += "&O_plus="+URLEncoder.encode(O_plus_unit,"UTF-8");
            data += "&O_minus="+URLEncoder.encode(O_minus_unit,"UTF-8");
            link =context.getString(R.string.link)+"/blood/hospitalsignup.php"+data;

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
    protected void onPostExecute(String s) {
        dialog.dismiss();
        Toast.makeText(context, ""+link, Toast.LENGTH_SHORT).show();
        Toast.makeText(context,s, Toast.LENGTH_SHORT).show();


    }
}
