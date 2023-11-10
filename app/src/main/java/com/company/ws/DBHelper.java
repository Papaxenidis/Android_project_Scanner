package com.company.ws;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.Random;


public class DBHelper extends SQLiteOpenHelper
{



    public DBHelper(Context context) {
        super(context,"base3.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL("create Table Table_nn(name TEXT, contact TEXT)");


        //πρωτο barcode δευτερο ποσοτητα


    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {

        DB.execSQL("DROP TABLE IF EXISTS Table_clues");

    }


    //INSERT
    public Boolean insertuserdata(String name, String contact) {


        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Date date = new Date();
        String dd = date.toString();

        contentValues.put("name", name);
        contentValues.put("contact", contact + "\n"+"• DATE : " +dd );
        //contentValues.put("dob", dob);


        long result = DB.insert("Table_nn", null, contentValues);//EDW

        if (result == -1) {
            return false;
        } else {
            return true;
        }







    }


    //UPDATE
    /*public Boolean updateuserdata(String name, String contact, String dob) {


        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);


        Cursor cursor = DB.rawQuery("select * from Userdetails where name = ?", new String[]{name});//κριτηριο το όνομα
        if (cursor.getCount() > 0) {


            long result = DB.update("Userdetails", contentValues, "name=?", new String[]{name});

            if (result == -1) {
                return false;

            }else {
                return true;
            }


        }else {


            return false;
        }


    }*/

    public Boolean updateuserdata(String gamename,String name1) {


        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        Date date = new Date();
        String dd = date.toString();


        contentValues.put("name",gamename);
        contentValues.put("contact", name1+ "\n"+"• *DATE : " +dd);
        //contentValues.put("dob", name2);




        Cursor cursor = DB.rawQuery("SELECT * FROM Table_nn WHERE name = ?", new String[]{gamename});//κριτηριο το όνομα
        if (cursor.getCount() > 0) {

            long result = DB.update("Table_nn", contentValues, "name=?", new String[]{gamename});



            if (result == -1) {
                return false;

            }else {
                return true;
            }


        }else {


            return false;
        }



    }



    //DELETE
    public Boolean deletedata(String name) {


        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("SELECT * FROM Table_nn WHERE name = ?", new String[]{name});
        if (cursor.getCount() > 0) {

            long result = DB.delete("Table_nn", "name=?", new String[]{name});


            if (result == -1) {
                return false;
            }else {
                return true;
            }


        }else {


            return false;
        }





    }

    public Boolean deletedata() {


        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("DELETE FROM Table_nn", null);
        if (cursor.getCount() > 0) {

            long result = DB.delete("Table_nn", null, null);


            if (result == -1) {
                return true;
            } else {
                return false;
            }


        } else {


            return false;
        }

    }
        ///edw


    //DISPLAY
    public Cursor getdata(String name) {


        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("select * from Table_nn ", null);

        // Cursor cursor = DB.rawQuery("SELECT * FROM Table_clues WHERE name = ?", new String[]{name});

        return cursor;



    }











}
