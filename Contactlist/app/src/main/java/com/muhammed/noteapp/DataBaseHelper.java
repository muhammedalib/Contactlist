package com.muhammed.noteapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context) {
        super(context, DataBase.Database_name, null, DataBase.Database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBase.Make_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + DataBase.Table_name);
        onCreate(db);
    }

    public long AddRegister(String name, String image, String address, String mail, String number, String birthday, String upload, String upgrade) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBase.Constant_Name, name);
        values.put(DataBase.Constant_Image, image);
        values.put(DataBase.Constant_Address, address);
        values.put(DataBase.Constant_Mail, mail);
        values.put(DataBase.Constant_Number, number);
        values.put(DataBase.Constant_BirthDay, birthday);
        values.put(DataBase.Constant_Upload, upload);
        values.put(DataBase.Constant_Upgrade, upgrade);

        long id = sqLiteDatabase.insert(DataBase.Table_name, null, values);

        sqLiteDatabase.close();

        return id;


    }


    public void DeleteValues(String id)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(DataBase.Table_name,DataBase.Constant_ID+" =? ",new String[]{id});
        sqLiteDatabase.close();

    }

    public void DeleteAll()
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(" DELETE FROM "+ DataBase.Table_name);
        sqLiteDatabase.close();
    }
    public void RegisterUpdate(String id,String name, String image, String address, String mail, String number, String birthday, String upload, String upgrade)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBase.Constant_Name, name);
        values.put(DataBase.Constant_Image, image);
        values.put(DataBase.Constant_Address, address);
        values.put(DataBase.Constant_Mail, mail);
        values.put(DataBase.Constant_Number, number);
        values.put(DataBase.Constant_BirthDay, birthday);
        values.put(DataBase.Constant_Upload, upload);
        values.put(DataBase.Constant_Upgrade, upgrade);

        sqLiteDatabase.update(DataBase.Table_name, values,DataBase.Constant_ID +" = ? ",new String[]{id});
        sqLiteDatabase.close();
    }


    public ArrayList<ExampleRegister> TakeAllRegisters(String SortToNew) {
        ArrayList<ExampleRegister> registerArrayList = new ArrayList<>();
        String SelectionCheck = " SELECT * FROM " + DataBase.Table_name + " ORDER BY " + SortToNew;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SelectionCheck, null);


        if (cursor.moveToFirst()) {
            do {
                ExampleRegister exampleRegister = new ExampleRegister(
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(DataBase.Constant_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Name)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Image)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Number)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Mail)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Address)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_BirthDay)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Upload)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(DataBase.Constant_Upgrade)));
                registerArrayList.add(exampleRegister);
                /*
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String image = cursor.getString(2);
                String number = cursor.getString(3);
                String mail = cursor.getString(4);
                String address = cursor.getString(5);
                String birthday = cursor.getString(6);
                String upload = cursor.getString(7);
                String upgrade = cursor.getString(8);
                */


            }
            while (cursor.moveToNext());


        }

        sqLiteDatabase.close();
        return registerArrayList;

    }


    public int Counter() {
        String CounterCheck = " SELECT * FROM " + DataBase.Table_name;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(CounterCheck, null);
        int CursorCount = cursor.getCount();
        return CursorCount;
    }
}
