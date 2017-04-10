package com.example.deepak.myblooddonationapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HospitalDashboardActivity extends AppCompatActivity {

    private int GALLERY_REQUEST_CODE = 0;
    private String CurrentUser;
    StorageReference mStorageRef;
    Bundle b;
    ImageView Iv;
    private File file;
    private Uri selectedImageUri;
    private int CAMERA_REQUEST_CODE = 1;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_dashboard);
        Intent i = getIntent();
        b = i.getExtras();
        CurrentUser = b.getString("Current_User");
        Iv = (ImageView) findViewById(R.id.Hospital_Image);
        bar= (ProgressBar) findViewById(R.id.Hospital_Image_Upload_progress);
        loadImage();
    }


    private void loadImage() {

        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference model = mStorageRef.child("Hospital_profiles/" + CurrentUser + ".jpg");
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(model)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.drawable.ic_person_add_black_24dp)
                .listener(new RequestListener<StorageReference, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Toast.makeText(HospitalDashboardActivity.this, "No Profile Pic", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onBackPressed() {
        warningDialog();
    }

    private void warningDialog() {
        new MaterialDialog.Builder(this)
                .title("Are You Sure to Logout ?")
                .positiveText("Yes")
                .negativeText("No")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Toast.makeText(HospitalDashboardActivity.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .show();
    }

    public void Hospital_profile_click(View v) {
        GetValueHospitalProfile getValueHospitalProfile = new GetValueHospitalProfile(this);
        getValueHospitalProfile.execute(CurrentUser);
    }


    public void uploadHospitalimage(View view) {
        new MaterialDialog.Builder(this)
                .title("Upload Option")
                .items(R.array.options)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        Toast.makeText(HospitalDashboardActivity.this, "" + position + ":::" + text, Toast.LENGTH_SHORT).show();
                        Intent intent;
                        switch (position) {
                            case 0:
                                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                file = getOutputMediaFile(1);
                                selectedImageUri = Uri.fromFile(file);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUri);
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(intent, CAMERA_REQUEST_CODE);
                                }
                                break;
                            case 1:
                                intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .show();
    }


    public File getOutputMediaFile(int type)
    {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getResources().getString(R.string.app_name)
        );
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyHHdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                savetoFirebase();
                Iv.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "Camera Image Added", Toast.LENGTH_SHORT).show();
            Bitmap bitmap= BitmapFactory.decodeFile(selectedImageUri.getPath());
            savetoFirebase();
            Iv.setImageBitmap(bitmap);
        }
    }

    public void savetoFirebase()
    {
        mStorageRef= FirebaseStorage.getInstance().getReference();
        StorageReference imageref= mStorageRef.child("Hospital_profiles/"+CurrentUser+".jpg");
        imageref.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(HospitalDashboardActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
