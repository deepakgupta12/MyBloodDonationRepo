package com.example.deepak.myblooddonationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HospitalSearchListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<HospitalModel> ls;
    private ArrayList<String> str;
    private ArrayList<String> emails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_search_list);

        Intent i = getIntent();
        Bundle Values=i.getExtras();
        ArrayList name = Values.getStringArrayList("names");
        ArrayList addresses=Values.getStringArrayList("address");
        recyclerView = (RecyclerView) findViewById(R.id.Hospital_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ls = new ArrayList<HospitalModel>();

        for (int j=0; j<name.size();j++){
            ls.add(new HospitalModel(name.get(j).toString(),addresses.get(j).toString()));


        }


        adapter = new MyHospitalAdapter(this,ls);
        recyclerView.setAdapter(adapter);


    }
}
