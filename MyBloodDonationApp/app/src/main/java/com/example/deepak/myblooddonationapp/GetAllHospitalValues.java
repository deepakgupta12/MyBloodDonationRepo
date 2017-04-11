package com.example.deepak.myblooddonationapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by standarduser on 18/03/17.
 */
public class GetAllHospitalValues extends AsyncTask<String,Void,String> {
    String link ;
    Context context;
    ProgressDialog dialog;
    public GetAllHospitalValues(Context context) {
        this.context=context;
        dialog= new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);

    }


    @Override
    protected void onPreExecute() {
        dialog.setMessage("wait While Fetching.....");
        dialog.show();
    }

    @Override
    protected String doInBackground(String args[]) {
        String result = null;
        String data;
        BufferedReader bufferedReader;

        try {

//            data = "?B_group=" + URLEncoder.encode(B_group, "UTF-8");
            link = context.getString(R.string.link)+"/blood/all_hospital.php";
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception" + e.getMessage());
        }


    }

    @Override
    protected void onPostExecute(String result) {
        dialog.dismiss();

        Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(context,HospitalSearchListActivity.class);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray inf = jsonObject.getJSONArray("stuff");
            ArrayList<String> arrayList = new ArrayList();
            ArrayList<String> addressList= new ArrayList();

            for (int i = 0; i < inf.length(); i++) {
                JSONObject c = inf.getJSONObject(i);
                String name = c.getString("Name");
                String address=c.getString("Address");
                arrayList.add(name);
                addressList.add(address);
            }

            intent.putStringArrayListExtra("names", arrayList);
            intent.putStringArrayListExtra("address",addressList);

            context.startActivity(intent);


        } catch (Exception e) {
            Toast.makeText(context, "ERROR" + e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(context,"hahahah", Toast.LENGTH_SHORT).show();
        }


    }
}
