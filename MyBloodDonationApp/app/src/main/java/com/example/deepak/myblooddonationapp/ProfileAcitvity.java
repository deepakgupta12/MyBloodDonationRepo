package com.example.deepak.myblooddonationapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

import java.net.URI;

public class ProfileAcitvity extends AppCompatActivity {
String I_user ,I_contact,I_address,I_city,I_Bgroup,I_Email;
    TextView name,addr,city,contact,B_group,Email;
    ProgressBar bar;
    ImageView Iv;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_acitvity);
        Intent i=getIntent();
        Bundle bd=i.getExtras();
        TextView title= (TextView) findViewById(R.id.ProfileName);

        I_user= bd.getString("user");
        I_Email=bd.getString("email");
        I_contact=bd.getString("contact");
        I_address=bd.getString("addr");
        I_city=bd.getString("city");
        I_Bgroup=bd.getString("B_Group");

        title.setText(I_user);
        name= (TextView) findViewById(R.id.name);
        contact= (TextView) findViewById(R.id.contact);
        addr= (TextView) findViewById(R.id.address);
        city= (TextView) findViewById(R.id.city);
        B_group= (TextView) findViewById(R.id.B_group);
        bar= (ProgressBar) findViewById(R.id.Progress_Bar);
        Iv= (ImageView) findViewById(R.id.ProfileImage);
        Email= (TextView) findViewById(R.id.mail);



        name.setText(I_user.toUpperCase());
        contact.setText(I_contact);
        addr.setText(I_address);
        city.setText(I_city);
        B_group.setText(I_Bgroup);
        Email.setText(I_Email);

        loadprofile(I_Email);




    }

    private void loadprofile(String name){


        mStorageRef= FirebaseStorage.getInstance().getReference();
        StorageReference model= mStorageRef.child("profiles/"+ name+".jpg");
        Glide.with(this)
                     .using(new FirebaseImageLoader())
                .load(model)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.drawable.ic_person_add_black_24dp)
                .listener(new RequestListener<StorageReference, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Toast.makeText(ProfileAcitvity.this, "No Profile Pic", Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, StorageReference model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        bar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(Iv);
    }
    public void oncallclick(View v)
    {
        Intent i=new Intent();
        i.setAction(Intent.ACTION_CALL);
     Uri uri=Uri.parse("tel:"+I_contact);
        i.setData(uri);
        startActivity(i);

    }


}
