package com.muhammed.noteapp;

public class DataBase {
    public static final String Database_name = "Registers";
    public static final int Database_version = 1;
    public static final String Table_name = "My_Table";


    public static final String Constant_ID = "ID";
    public static final String Constant_Name = "Name";
    public static final String Constant_Image = "Image";
    public static final String Constant_Address = "Address";
    public static final String Constant_Mail = "Mail";
    public static final String Constant_Number = "Number";
    public static final String Constant_BirthDay = "BirthDay";
    public static final String Constant_Upload = "UploadDate";
    public static final String Constant_Upgrade = "UpgradeDate";


    public static final String Make_Table = " CREATE TABLE " + Table_name + "("  + Constant_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + Constant_Name + " TEXT,"
        + Constant_Image + " TEXT,"
        + Constant_Address + " TEXT,"
        + Constant_Number + " TEXT,"
        + Constant_Upload + " TEXT,"
        + Constant_Upgrade + " TEXT,"
        + Constant_BirthDay + " TEXT,"
        + Constant_Mail + " TEXT " +
        ")";
}
