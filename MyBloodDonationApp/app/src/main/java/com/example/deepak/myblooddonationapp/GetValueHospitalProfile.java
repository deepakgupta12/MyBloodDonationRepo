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

/**
 * Created by standarduser on 15/03/17.
 */
public class GetValueHospitalProfile extends AsyncTask<String, Void, String> {
    Context context;
    ProgressDialog dialog;
    String link;
    String username;

    public GetValueHospitalProfile(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);

    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Fetching Data .......");
        dialog.show();
    }

    @Override
    protected String doInBackground(String args[]) {
        String result = null;
        username = args[0];
        String data;
        BufferedReader bufferedReader;

        try {

            data = "?Name=" + URLEncoder.encode(username, "UTF-8");
            link = context.getString(R.string.link) + "/blood/HospValues.php" + data;
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
        Toast.makeText(context, "" + result, Toast.LENGTH_SHORT).show();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray inf = jsonObject.getJSONArray("stuff");
            Intent in = new Intent(context, HospitalProfileActivity.class);

            for (int i = 0; i < inf.length(); i++) {
                JSONObject c = inf.getJSONObject(i);
                String name = c.getString("Name");
                in.putExtra("Name", name);
                String addr = c.getString("Address");
                in.putExtra("Address", addr);
                String contact = c.getString("Contact");
                in.putExtra("Contact", contact);
                String City = c.getString("City");
                in.putExtra("City", City);
                String B_plus = c.getString("B_plus");
                in.putExtra("B_plus", B_plus);
                String B_minus = c.getString("B_minus");
                in.putExtra("B_minus", B_minus);

                String A_plus = c.getString("A_plus");
                in.putExtra("A_plus", A_plus);

                String A_minus = c.getString("A_minus");
                in.putExtra("A_minus", A_minus);

                String AB_plus = c.getString("AB_plus");
                in.putExtra("AB_plus", AB_plus);

                String AB_minus = c.getString("AB_minus");
                in.putExtra("AB_minus", AB_minus);

                String O_plus = c.getString("O_plus");
                in.putExtra("O_plus", O_plus);

                String O_minus = c.getString("O_minus");
                in.putExtra("O_minus", O_minus);
            }

            context.startActivity(in);


        } catch (Exception e) {
            Toast.makeText(context, "ERROR" + e.getMessage(), Toast.LENGTH_SHORT).show();
            ;
        }
    }
}
