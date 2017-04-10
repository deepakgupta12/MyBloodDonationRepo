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
 * Created by Deepak on 11/13/2016.
 */
public class GetSearchedProfile extends AsyncTask<String, Void, String> {
    String link;
    String B_group;
    private Context context;
    private ProgressDialog dialog;

    public GetSearchedProfile(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setMessage("Wait While Searching.... ");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... args) {
        String result = null;
        B_group = args[0];
        String data;
        BufferedReader bufferedReader;

        try {

            data = "?B_group=" + URLEncoder.encode(B_group, "UTF-8");
            link = context.getString(R.string.link)+"/blood/blood_contacts.php" + data;
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
        // Toast.makeText(context, ""+jsonStr, Toast.LENGTH_SHORT).show();
        dialog.dismiss();

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray inf = jsonObject.getJSONArray("stuff");
            Intent in = new Intent(context, SearchListActivity.class);
            ArrayList<String> arrayList = new ArrayList();
            ArrayList<String> emailList= new ArrayList();

            for (int i = 0; i < inf.length(); i++) {
                JSONObject c = inf.getJSONObject(i);
                String name = c.getString("name");
                String email=c.getString("email");
                arrayList.add(name);
                emailList.add(email);
            }

            in.putStringArrayListExtra("names", arrayList);
            in.putStringArrayListExtra("emails",emailList);

            context.startActivity(in);


        } catch (Exception e) {
            Toast.makeText(context, "ERROR" + e.getMessage(), Toast.LENGTH_SHORT).show();
            ;
        }
    }
}

