package com.muhammed.noteapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private CircleImageView ProfilImage;
    private EditText EdtName, EdtNumber, EdtMail, EdtAddress, EdtBirthday;
    private FloatingActionButton BtnSave;
    ActionBar actionbar;
    private static final int Camera_permission = 100;
    private static final int Storage_permission = 101;
    private static final int Gallery_permission = 102;
    private static final int Gallery_Storage_permission = 103;

    private String[] Camera_String_permission;
    private String[] Storage_String_permission;


    private String id, name, address, mail, number, birthday, upload, upgrade;
    Uri ResultUri;
    Uri ImageUri;
    DataBaseHelper dataBaseHelper;
    private boolean DateSituation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        actionbar = getSupportActionBar();
        actionbar.setTitle("Register");
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        ProfilImage = findViewById(R.id.btn_image_register);
        EdtName = findViewById(R.id.edit_name);
        EdtNumber = findViewById(R.id.edit_Number);
        EdtMail = findViewById(R.id.edit_Mail);
        EdtAddress = findViewById(R.id.edit_address);
        EdtBirthday = findViewById(R.id.edit_birthday);
        BtnSave = findViewById(R.id.Btn_save);
        Intent intent = getIntent();
        DateSituation = intent.getBooleanExtra("DATE_SITUATION", false);
        if (DateSituation) {
            actionbar.setTitle("Update the informations.");
            id = intent.getStringExtra("ID");
            name = intent.getStringExtra("NAME");
            number = intent.getStringExtra("NUMBER");
            address = intent.getStringExtra("ADDRESS");
            ImageUri = Uri.parse(intent.getStringExtra("IMAGE"));
            mail = intent.getStringExtra("MAIL");
            upgrade = intent.getStringExtra("UPGRADE");
            upload = intent.getStringExtra("UPDATE");
            birthday = intent.getStringExtra("BIRTHDAY");


            EdtName.setText(name);
            EdtNumber.setText(number);
            EdtAddress.setText(address);
            EdtMail.setText(mail);
            EdtBirthday.setText(birthday);
            if (ImageUri.toString().equals("null")) {
                ProfilImage.setImageResource(R.drawable.ic_baseline_person_24);
            } else {
                ProfilImage.setImageURI(ImageUri);
            }
        } else {
            actionbar.setTitle("Add your informations");
        }


        dataBaseHelper = new DataBaseHelper(this);
        Camera_String_permission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Storage_String_permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        ProfilImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog();
            }
        });
        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RegisterActivity.this,"Kayıt Buradan Olacak",Toast.LENGTH_SHORT).show();
                Addvalues();
            }
        });
    }

    private void Addvalues() {
        name = EdtName.getText().toString().trim();
        mail = EdtMail.getText().toString().trim();
        number = EdtNumber.getText().toString().trim();
        address = EdtAddress.getText().toString().trim();
        birthday = EdtBirthday.getText().toString().trim();

        String InstantTime = "" + System.currentTimeMillis();

        if(DateSituation)
        {
            dataBaseHelper.RegisterUpdate(
                    ""+id,
                    ""+name,
                    ""+ImageUri,
                    ""+address,
                    ""+mail,
                    ""+number,
                    ""+birthday,
                    ""+upload,
                    ""+InstantTime);

            Toast.makeText(this, "person Successfully Updated", Toast.LENGTH_SHORT).show();
        }
        else
        {
            long id = dataBaseHelper.AddRegister(
                    "" + name,
                    "" + ImageUri,
                    "" + address,
                    "" + mail,
                    "" + number,
                    "" + birthday,
                    "" + InstantTime,
                    "" + InstantTime);
            Toast.makeText(this, "Person Successfully Added", Toast.LENGTH_SHORT).show();
        }


    }

    private void PickImageDialog() {
        String[] Objects = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Image");
        builder.setItems(Objects, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    if (!CheckCameraControl()) {
                        RequestCamera();
                    } else {
                        PickImageFromGallery();
                    }
                }
                if (i == 1) {
                    if (!CheckStorageControl()) {
                        RequestStorage();
                    } else {
                        PickImageFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void PickImageFromGallery() {
        CropImage.activity().start(this);

    }

    private void PickImageFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Image Tittle");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image Description");
        ImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private boolean CheckStorageControl() {
        boolean Result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return Result;
    }

    private void RequestStorage() {
        ActivityCompat.requestPermissions(this, Storage_String_permission, Storage_permission);

    }

    private void RequestCamera() {
        ActivityCompat.requestPermissions(this, Camera_String_permission, Camera_permission);

    }

    private boolean CheckCameraControl() {
        boolean ResultForCamera1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        boolean ResultForCamera2 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return ResultForCamera1 && ResultForCamera2;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ImageUri = result.getUri();
                Picasso.with(this).load(ImageUri).into(ProfilImage);
                Toast.makeText(this, "Profile Picture  Succesfully Upgraded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Camera_permission: {
                if (grantResults.length > 0) {
                    boolean CameraAccepted = grantResults[0] == (PackageManager.PERMISSION_GRANTED);
                    boolean StorageAccepted = grantResults[1] == (PackageManager.PERMISSION_GRANTED);
                    if (CameraAccepted && StorageAccepted) {
                        PickImageFromGallery();
                    } else {
                        Toast.makeText(this, "Please Enable Camera And Storage Permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case Storage_permission: {
                if (grantResults.length > 0) {
                    boolean StorageAccepted = grantResults[0] == (PackageManager.PERMISSION_GRANTED);
                    if (StorageAccepted) {
                        PickImageFromGallery();

                    } else {
                        Toast.makeText(this, "Please Enable Storage Permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }
}