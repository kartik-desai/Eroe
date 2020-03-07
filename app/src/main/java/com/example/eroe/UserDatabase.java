package com.example.eroe;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static android.content.ContentValues.TAG;


public class UserDatabase {
    static SQLiteDatabase db;
    static private int c;
    static private String da;
    public static void makeTable(SQLiteDatabase d){
        d.execSQL("CREATE TABLE IF NOT EXISTS User(aadhar TEXT, name TEXT);");
        d.execSQL("CREATE TABLE IF NOT EXISTS Emergency(aadhar TEXT, contact TEXT)");
    }

    public static void dropTable(SQLiteDatabase d){
        d.execSQL("delete from User;");
        d.execSQL("delete from Emergency;");
        d.execSQL("drop table User");
        d.execSQL("drop table Emergency");
    }

    public static void putData(SQLiteDatabase d,String a,String n){
        String sql = "Insert into User values('"+a+"','"+n+"');";
        d.execSQL(sql);
    }
    public static String getAadhar(SQLiteDatabase d) {
        String str = "null";
        try {
            Cursor c = d.rawQuery("SELECT * FROM User", null);
            if (c.moveToFirst()) {
                str = c.getString(c.getColumnIndex("aadhar"));
            }
            return str;
        } catch (Exception ex) {
            Log.e("Exception", ex.toString());
            return ("null");
        }
    }
    public static void putDatas(SQLiteDatabase d,String a, String[] contacts){

        String sql;
        sql = "delete from Emergency";
        d.execSQL(sql);
        for(int i=0;i<5;i++){
            sql = "Insert into Emergency values('"+a+"','"+contacts[i]+"');";
            d.execSQL(sql);
        }
    }
    public static String[] getContacts(SQLiteDatabase d){
        String a[] = new String[5];
        a[0] = "null";
        try {
            int i=0;
            Cursor c = d.rawQuery("SELECT * FROM Emergency", null);
            while (c.moveToNext()) {
                a[i] = c.getString(c.getColumnIndex("contact"));
                Log.d("dd",a[i]+i);
                i++;
            }
            return a;
        } catch (Exception ex) {
            Log.e("Exception", ex.toString());
            return (a);
        }
    }
}