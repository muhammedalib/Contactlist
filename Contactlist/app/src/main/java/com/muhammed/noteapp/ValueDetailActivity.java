package com.muhammed.noteapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class ValueDetailActivity extends AppCompatActivity {
    ActionBar actionbar;
    String ValueId;

    ImageView ProfilImage;
    TextView TvName,TvNumber,TvMail,TvBirthday,TvAddress,TvUpload,TvUpdate;

    private DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_detail);

        actionbar=getSupportActionBar();
        actionbar.setTitle("Detail Page");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        ValueId = intent.getStringExtra("Value_ID");
        ProfilImage = findViewById(R.id.Profile_Image_Detail);
        TvName = findViewById(R.id.txt_name);
        TvNumber = findViewById(R.id.txt_number);
        TvMail = findViewById(R.id.txt_mail);
        TvBirthday = findViewById(R.id.txt_birthday);
        TvAddress = findViewById(R.id.txt_address);
        TvUpload = findViewById(R.id.txt_upload);
        TvUpdate = findViewById(R.id.txt_update);

        dataBaseHelper=new DataBaseHelper(this);
        ShowValueDetail();

    }

    private void ShowValueDetail() {
        String ChechToID = " SELECT * FROM " + DataBase.Table_name + " WHERE "+ DataBase.Constant_ID + " =\"" +ValueId +"\"";

        SQLiteDatabase sqLiteDatabase = this.dataBaseHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(ChechToID,null);


        if(cursor.moveToFirst())
        {
            do {
                String id =""+cursor.getInt(cursor.getColumnIndexOrThrow(DataBase.Constant_ID));
                String name ="" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Name));
                String number ="" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Number));
                String image ="" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Image));
                String address ="" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Address));
                String mail ="" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Mail));
                String birthday ="" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_BirthDay));
                String upload ="" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Upload));
                String upgrade ="" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Upgrade));


                Calendar calender = Calendar.getInstance(Locale.getDefault());
                calender.setTimeInMillis(Long.parseLong(upload));
                String UploadTime = "" + DateFormat.format("dd/MM/yyyy hh:mm:aa",calender);

                Calendar calender2 = Calendar.getInstance(Locale.getDefault());
                calender.setTimeInMillis(Long.parseLong(upgrade));
                String UpgradeTime = "" + DateFormat.format("dd/MM/yyyy hh:mm:aa",calender2);


                TvName.setText(name);
                TvNumber.setText(number);
                TvMail.setText(mail);
                TvBirthday.setText(birthday);
                TvAddress.setText(address);
                TvUpload.setText(UploadTime);
                TvUpdate.setText(UpgradeTime);

                if(image == null)
                {
                    ProfilImage.setImageResource(R.drawable.ic_baseline_person_24);
                }
                else
                {
                    ProfilImage.setImageURI(Uri.parse(image));
                }


            }
            while(cursor.moveToNext());
        }
            sqLiteDatabase.close();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}