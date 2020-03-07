package com.example.eroe;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static android.content.ContentValues.TAG;


public class UserDatabase {
    /*static SQLiteDatabase db;
    static private int c;
    static private String da;
    public static void makeTable(SQLiteDatabase d){
        d.execSQL("CREATE TABLE IF NOT EXISTS User(aadhar TEXT, name TEXT, age INT, phone TEXT, gender TEXT ,email TEXT, );");
        d.execSQL("");
    }
    public static void insertLogin(String e, SQLiteDatabase d){
        ContentValues values = new ContentValues();
        values.put("email", e);
        values.put("name", "");
        values.put("age", 0);
        values.put("phone", "");
        values.put("gender", "");
        values.put("height", 0);
        values.put("weight", 0);
        values.put("bmi", 0);
        values.put("history", "");
        d.insert("Login", null, values);
    }
    public static int selectInfo(SQLiteDatabase d) {
        int i;
        db = d;
        Cursor cursor = db.rawQuery("SELECT * FROM Login", null);
        if (cursor.moveToFirst()) {
            return (1);
        }
        else {
            return (0);
        }
    }
    public static boolean exists(SQLiteDatabase db) {
        try {
            Cursor cursor=db.rawQuery("SELECT * FROM Login",null);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public static String getEmail(SQLiteDatabase d){
        String str="";
        try{
            Cursor c=d.rawQuery("SELECT * FROM Login",null);
            if (c.moveToFirst()) {
                str= c.getString(c.getColumnIndex("email"));
            }
            return str;
        }catch (Exception ex){
            Log.e("Exception",ex.toString());
            return(null);
        }
    }
    public static String getName(SQLiteDatabase d){
        String str="";
        try{
            Cursor c=d.rawQuery("SELECT * FROM Login",null);
            if (c.moveToFirst()) {
                str= c.getString(c.getColumnIndex("name"));
            }
            return str;
        }catch (Exception ex){
            Log.e("Exception",ex.toString());
            return(null);
        }
    }
    public static void updateBasic(SQLiteDatabase d,String email,String name,String age,String phone,String height,String weight,String bmi,String city){
        db=d;
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("age",age);
        contentValues.put("phone",phone);
        contentValues.put("height",height);
        contentValues.put("weight",weight);
        contentValues.put("bmi",bmi);
        contentValues.put("city",city);
        int id=d.update("Login",contentValues,"email='"+email+"'",null);
    }
    public static Cursor selectLogin(SQLiteDatabase d){
            db=d;
            Cursor c=db.rawQuery("Select * from Login",null);
           return c;
    }
    public static String getHistory(SQLiteDatabase d){
        String str="";
        try{
            Cursor c=d.rawQuery("SELECT * FROM Login",null);
            if (c.moveToFirst()) {
                str= c.getString(c.getColumnIndex("history"));
            }
            return str;
        }catch (Exception ex){
            Log.e("Exception",ex.toString());
            return(null);
        }
    }
    public static void  getUserValues(String res,SQLiteDatabase d){
        String name="";
        try {
            JSONObject jsonObj = new JSONObject(res);
            JSONArray users = jsonObj.getJSONArray("users");
            Log.e("length",users.length()+"");
            for (int i = 0; i < users.length(); i++) {
                JSONObject cup = users.getJSONObject(i);
                JSONObject c = cup.getJSONObject("user");
                String email = c.optString("email");
                name = c.optString("name");
                String phone = c.optString("phone");
                String age = c.optString("age");
                String gender = c.optString("gender");
                String height = c.optString("height");
                String weight = c.optString("weight");
                String bmi = c.optString("bmi");
                String city=c.optString("city");
                String hist = c.optString("medhistory");
                ContentValues values=new ContentValues();
                values.put("name",name);
                values.put("phone",phone);
                values.put("age",age);
                values.put("gender",gender);
                values.put("height",height);
                values.put("weight",weight);
                values.put("bmi",bmi);
                values.put("history",hist);
                values.put("city",city);
                int id=d.update("Login",values,"email='"+email+"'",null);
                Log.e("values",id+"");
            }
        }catch(Exception e){
            Log.e("Ex","Exception: "+Log.getStackTraceString(e));
        }
        //return name;
    }
    public static void insertTable(SQLiteDatabase d,int count,String date){
        db=d;
        c=count;
        da=date;
        ContentValues contentValues=new ContentValues();
        contentValues.put("date",da);
        contentValues.put("count",c);
        db.insert("waterGraph",null,contentValues);
    }
    public static void updateTable(SQLiteDatabase d,int count,String date){
        c=count;
        db=d;
        da=date;
        ContentValues contentValues=new ContentValues();
        contentValues.put("date",da);
        contentValues.put("count",c);
        String whereClause="date=?";
        String[] whereArgs={da};
        db.update("waterGraph",contentValues,whereClause,whereArgs);
    }
    public static int getData(SQLiteDatabase d,String date){
        int a=0;
        db=d;
        da=date;
        String[] projection={"date","count"};
        Cursor cursor=db.query("waterGraph",projection,"date=?",new String[]{da},null,null,null);
        if(cursor.getCount()!=0){
            while(cursor.moveToNext()){
                //date=co.getString(0);
                a=cursor.getInt(1);
            }
        }
        else{}
        return a;
    }
    public static boolean getIfDateExists(SQLiteDatabase d,String date){
        db=d;
        da=date;
        String[] projection={"date","count"};
        Cursor cursor=db.query("waterGraph",projection,"date=?",new String[]{da},null,null,null);
        return cursor.getCount() != 0;
    }
    public static void addQuery(String query,SQLiteDatabase db){
        ContentValues val=new ContentValues();
        val.put("Query",query);
        Log.e("q",query);
        db.insert("Search",null,val);
    }
    public static boolean checkItem(SQLiteDatabase db,String name){
        try {
            Cursor cursor=db.rawQuery("SELECT * FROM FoodItems where ItemName='"+name+"'",null);
            if (cursor.moveToFirst()) {
                return !cursor.getString(cursor.getColumnIndex("ItemName")).equals("");
            }
            else return false;
        } catch (SQLException e) {
            return false;
        }
    }
    public static Cursor getItem(SQLiteDatabase db,String name){
        try {
            Cursor cursor=db.rawQuery("SELECT * FROM FoodItems where ItemName='"+name+"'",null);
            return cursor;
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean searchQuery(SQLiteDatabase db,String q){
        Cursor cursor=null;
        try {
           cursor=db.rawQuery("SELECT * FROM Search WHERE Query='"+q+"'",null);
            return cursor.moveToFirst();
        } catch (SQLException e) {
            return false;
        }
        finally {
        }
    }
    public static Cursor getItems(SQLiteDatabase db,String q){
        try {
            Cursor cursor=db.rawQuery("SELECT * FROM FoodItems WHERE Query LIKE '%"+q+"%'",null);
            return cursor;
        } catch (SQLException e) {
            return null;
        }
    }
    public static Cursor getItems(SQLiteDatabase db){
        try {
            Cursor cursor=db.rawQuery("SELECT * FROM FoodData",null);
            return cursor;
        } catch (SQLException e) {
            return null;
        }
    }
    public static Cursor getDataFromDb(SQLiteDatabase db, String d,String time){
        try {
            Cursor cursor=db.rawQuery("SELECT * FROM FoodData WHERE Date='"+d+"' AND Time='"+time+"'",null);
            return cursor;
        } catch (SQLException e) {
            return null;
        }
    }
    public static void addFoodEaten(SQLiteDatabase db,String date,String time,String name){
        ContentValues values = new ContentValues();
        values.put("Date", date);
        values.put("Time", time);
        values.put("ItemName", name);
        Cursor chk=db.rawQuery("SELECT * from FoodItems WHERE ItemName='"+name+"'",null);
        if(chk.moveToFirst())
            db.insert("FoodData", null, values);
    }
    public static float getItemEnergy(String name){
        try {
            Cursor cursor=db.rawQuery("SELECT Energy FROM FoodItems WHERE ItemName='"+name+"'",null);
            cursor.moveToFirst();
            return cursor.getFloat(0);
        } catch (SQLException e) {
            return 0.0f;
        }
    }
    public static Cursor getTimeAndItemName(SQLiteDatabase db,String date){
        try{
            Cursor c=db.rawQuery("Select Time,ItemName from FoodData where date='"+date+"'",null);
            return c;
        }catch (Exception c) {
            return null;
        }
    }
    public static void updateStep(SQLiteDatabase db,String d,int c){
        ContentValues val=new ContentValues();
        val.put("Date",d);
        val.put("Count",c);
        db.insert("Steps",null,val);
    }
    public static void getSteps(SQLiteDatabase db){
        try{
            Cursor c=db.rawQuery("Select * from Steps",null);
            c.moveToNext();
            Log.e("Count",c.getString(0)+c.getInt(1));
            c.close();
        }catch (Exception c) {
        }
    }
    public static String getPhone(SQLiteDatabase d){
        String str="";
        try{
            Cursor c=d.rawQuery("SELECT * FROM Login",null);
            if (c.moveToFirst()) {
                str= c.getString(c.getColumnIndex("phone"));
            }
            return str;
        }catch (Exception ex){
            Log.e("Exception",ex.toString());
            return(null);
        }
    }
    public static String[] getPreviousDates(SQLiteDatabase d){
        db=d;
        Date d1=new Date();
        Log.e(TAG, "getPreviousDates: "+d1 );
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String current_Date=sdf.format(d1);
        List<Date> dates = new ArrayList<>();
        Cursor cursor=db.rawQuery("SELECT date('"+current_Date+"','-6 days')",null);
        String end_Date=null;
        Log.e(TAG, "getPreviousDates: "+cursor.getCount() );
        if(cursor.getCount()!=0){
            while(cursor.moveToNext()){
                end_Date=cursor.getString(0);
            }
            Log.e(TAG, "getPreviousDates: "+end_Date );
        }
        Date startDate;
        Date endDate;
        long interval=0,endTime=0,curTime=0;
        String dateArray[]=new String[7];
        try{
            startDate = sdf.parse(end_Date);
            endDate = sdf.parse(current_Date);
            interval = 24*1000 * 60 * 60;
            endTime =endDate.getTime() ; // create your endtime here, possibly using Calendar or Date
            curTime = startDate.getTime();
        }catch(Exception e){
            e.printStackTrace();
        }
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }
        Log.e(TAG, "getPreviousDates: "+dates.size() );
        for(int i=0;i<dates.size();i++){
            Date lDate =dates.get(i);
            String ds = sdf.format(lDate);
            dateArray[i]=ds;
            Log.e(TAG, "getPreviousDates: "+dateArray[i] );
        }
        return dateArray;
    }
    public static Cursor getStepData(SQLiteDatabase d,String date){
        db=d;
        da=date;
        String[] projection={"Date","Count"};
        Cursor cursor=db.query("Steps",projection,"Date=?",new String[]{da},null,null,null);
        return cursor;
    }*/
}