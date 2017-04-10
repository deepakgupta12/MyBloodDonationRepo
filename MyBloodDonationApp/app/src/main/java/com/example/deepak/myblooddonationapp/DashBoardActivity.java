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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashBoardActivity extends AppCompatActivity {
    Button btn;
    Spinner Ed;
    String st;
    ImageView Iv;
    private int GALLERY_REQUEST_CODE = 0;
    private int CAMERA_REQUEST_CODE = 1;
    private Uri selectedImageUri;
    private File file;
    private StorageReference mStorageRef;
    private ProgressBar bar;
    private String profile_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Iv = (ImageView) findViewById(R.id.ProfileIcon);
        btn = (Button) findViewById(R.id.SearchBtn);
        Ed = (Spinner) findViewById(R.id.SearchEdit);

        try {
            Intent i = getIntent();
            Bundle b = i.getExtras();
            profile_Name = b.getString("user");
            loadprofile(profile_Name);
            Toast.makeText(DashBoardActivity.this, "HELO " + profile_Name.toUpperCase(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(DashBoardActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void loadprofile(String name) {

        bar = (ProgressBar) findViewById(R.id.progress_profile);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference model = mStorageRef.child("profiles/" + name + ".jpg");
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(model)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.drawable.ic_person_add_black_24dp)
                .listener(new RequestListener<StorageReference, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, StorageReference model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Toast.makeText(DashBoardActivity.this, "No Profile Pic", Toast.LENGTH_SHORT).show();
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

    public void helpopen(View v) {
        Intent i = new Intent(this, HelpActivity.class);
        startActivity(i);

    }

    public void logout(View view) {
        warningDialog();
    }


    public void searchBtnClick(View v) {
        Toast.makeText(DashBoardActivity.this, "" + Ed.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
        Intent in = new Intent(DashBoardActivity.this, SearchListActivity.class);
        in.putExtra("B_group", Ed.getSelectedItem().toString());
        GetSearchedProfile getSearchedProfile = new GetSearchedProfile(DashBoardActivity.this);
        getSearchedProfile.execute(Ed.getSelectedItem().toString());
    }


    public void search_By_Hospital(View view) {
        new MaterialDialog.Builder(this)
                .title("SEARCH BY")
                .titleColorRes(R.color.md_material_blue_600)
                .backgroundColorRes(R.color.themeapp)
                .items(R.array.searchBy)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position) {
                            case 0:
                                Toast.makeText(DashBoardActivity.this, "Name", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(DashBoardActivity.this, "All", Toast.LENGTH_SHORT).show();
                                GetAllHospitalValues getAllHospitalValues= new GetAllHospitalValues(DashBoardActivity.this);
                                getAllHospitalValues.execute();
                                break;
                        }
                    }
                })
                .show();
    }

    public void proClick(View v) {
        GetValueProfile l = new GetValueProfile(this);
        l.execute(profile_Name);

    }

    public void updateprofile(View v) {
        Intent in = new Intent(this, UpdateActivity.class);
        in.putExtra("name", profile_Name);
        startActivity(in);
    }

    public void UploadImage(View v) {
        new MaterialDialog.Builder(this)
                .title("Upload Option")
                .items(R.array.options)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        Toast.makeText(DashBoardActivity.this, "" + position + ":::" + text, Toast.LENGTH_SHORT).show();
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
            Bitmap bitmap = BitmapFactory.decodeFile(selectedImageUri.getPath());
            savetoFirebase();
            Iv.setImageBitmap(bitmap);
        }
    }

    public void savetoFirebase() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageref = mStorageRef.child("profiles/" + profile_Name + ".jpg");
        imageref.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(DashBoardActivity.this, "Successfully Saved", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public File getOutputMediaFile(int type) {
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
                        Toast.makeText(DashBoardActivity.this, "Welcome Back", Toast.LENGTH_SHORT).show();
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
}
