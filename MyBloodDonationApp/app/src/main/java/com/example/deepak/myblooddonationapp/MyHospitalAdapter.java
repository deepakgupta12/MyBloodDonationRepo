package com.example.deepak.myblooddonationapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by standarduser on 18/03/17.
 */
public class MyHospitalAdapter extends RecyclerView.Adapter<MyHospitalAdapter.ViewHolder> {

    List<HospitalModel> ls;
    Context context;
    private StorageReference mStorageRef;
    HospitalModel HM;

    public MyHospitalAdapter(Context context, List<HospitalModel> ls) {
        this.ls=ls;
        this.context=context;
    }

    @Override
    public MyHospitalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View profileView = inflater.inflate(R.layout.content_main, parent, false);
        MyHospitalAdapter.ViewHolder viewHolder = new MyHospitalAdapter.ViewHolder(profileView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyHospitalAdapter.ViewHolder holder, int position) {

        HM = ls.get(position);
        holder.name.setText(HM.getName());
        holder.address.setText(HM.getAddresses());
        holder.Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetValueHospitalProfile getValueHospitalProfile = new GetValueHospitalProfile(context);
                getValueHospitalProfile.execute(holder.name.getText().toString());
            }
        });




    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView address;
        public ImageView Iv;
        public Button Btn;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.R_P_Name);
            address=(TextView)itemView.findViewById(R.id.R_E_Mail);
            Iv= (ImageView) itemView.findViewById(R.id.R_ProfilesIcon);
            Btn= (Button) itemView.findViewById(R.id.R_ProfileDetails);
        }
    }
}
