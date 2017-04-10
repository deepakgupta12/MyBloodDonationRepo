package com.example.deepak.myblooddonationapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by standarduser on 28/02/17.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    List<Model> ls;
    Context context;
    private StorageReference mStorageRef;

    public MyAdapter(Context context, List<Model> ls) {
        this.ls = ls;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View profileView = inflater.inflate(R.layout.content_main, parent, false);
        ViewHolder viewHolder = new ViewHolder(profileView);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, int position) {

        Model md = ls.get(position);
        holder.name.setText(md.getName());
        holder.email.setText(md.getEmail());
        mStorageRef= FirebaseStorage.getInstance().getReference();
        StorageReference model= mStorageRef.child("profiles/"+holder.email.getText().toString()+".jpg");
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(model)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.drawable.ic_person_add_black_24dp)
                .into(holder.Iv);
        holder.Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetValueProfile l = new GetValueProfile(context);
                l.execute(holder.email.getText().toString());

            }
        });


    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView email;
        public ImageView Iv;
        public Button Btn;
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.R_P_Name);
            email=(TextView)itemView.findViewById(R.id.R_E_Mail);
            Iv= (ImageView) itemView.findViewById(R.id.R_ProfilesIcon);
            Btn= (Button) itemView.findViewById(R.id.R_ProfileDetails);
        }
    }




}
