package com.example.deepak.myblooddonationapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class HospitalProfileActivity extends AppCompatActivity {

    TextView H_pro_name,
            H_pro_address,
            H_pro_city,
            H_pro_contact,
            B_plus_unit,
            B_minus_unit,
            A_plus_unit,
            A_minus_unit,
            AB_plus_unit,
            AB_minus_unit,
            O_plus_unit,
            O_minus_unit;
    ImageView Iv;
    private ProgressBar bar ;

    private StorageReference mStorageRef;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hospital_profile);
        Iv= (ImageView) findViewById(R.id.H_pro_image);
        H_pro_name = (TextView) findViewById(R.id.H_pro_input_Name);
        H_pro_address = (TextView) findViewById(R.id.H_pro_input_Address);
        H_pro_city = (TextView) findViewById(R.id.H_pro_input_City);
        H_pro_contact = (TextView) findViewById(R.id.H_pro_input_Contact);
        B_plus_unit = (TextView) findViewById(R.id.B_plus_TV);
        B_minus_unit = (TextView) findViewById(R.id.B_minus_TV);
        A_plus_unit = (TextView) findViewById(R.id.A_plus_input_TV);
        A_minus_unit = (TextView) findViewById(R.id.A_minus_input_TV);
        AB_plus_unit = (TextView) findViewById(R.id.AB_plus_input_TV);
        AB_minus_unit = (TextView) findViewById(R.id.AB_minus_input_TV);
        O_plus_unit = (TextView) findViewById(R.id.O_plus_input_TV);
        O_minus_unit = (TextView) findViewById(R.id.O_minus_input_TV);
        bar= (ProgressBar) findViewById(R.id.H_pro_progress);

        String unit = " units";
        Intent i = getIntent();
        bundle = i.getExtras();

        H_pro_name.setText(bundle.getString("Name"));
        H_pro_address.setText(bundle.getString("Address"));
        H_pro_city.setText(bundle.getString("City"));
        H_pro_contact.setText(bundle.getString("Contact"));
        B_plus_unit.setText(bundle.getString("B_plus") + unit);
        B_minus_unit.setText(bundle.getString("B_minus") + unit);
        AB_plus_unit.setText(bundle.getString("AB_plus") + unit);
        AB_minus_unit.setText(bundle.getString("AB_minus") + unit);
        A_plus_unit.setText(bundle.getString("A_plus") + unit);
        A_minus_unit.setText(bundle.getString("A_minus") + unit);
        O_plus_unit.setText(bundle.getString("O_plus") + unit);
        O_minus_unit.setText(bundle.getString("O_minus") + unit);
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();

        loadImage();


    }


    private void loadImage() {

        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference model = mStorageRef.child("Hospital_profiles/" + bundle.getString("Name") + ".jpg");
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(model)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.drawable.ic_person_add_black_24dp)
                .listener(new RequestListener<StorageReference, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Toast.makeText(HospitalProfileActivity.this, "No Profile Pic", Toast.LENGTH_SHORT).show();
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


}
