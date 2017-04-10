package com.example.deepak.myblooddonationapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class SearchListActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Model> ls;

    private ArrayList<String> str;
    private ArrayList<String> emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        Intent in = getIntent();
        Bundle bn = in.getExtras();
        str = bn.getStringArrayList("names");
        emails = bn.getStringArrayList("emails");

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ls = new ArrayList<Model>();
        int i = 0;
        for (String value : str) {
            ls.add(new Model(value, emails.get(i)));
            i++;
        }

        adapter = new MyAdapter(this, ls);

        recyclerView.setAdapter(adapter);




    }

    public void destroyclick(View v)
    {
        finish();
    }
}
