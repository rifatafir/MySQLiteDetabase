package com.example.mysqlitedetabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static android.database.sqlite.SQLiteDatabase.*;
import static java.lang.String.*;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student.db";
    private static final String TABLE_NAME = "Student_details";
    private static final int VERSON_NUMBER = 1;
    private static final String ID = "_id" ;
    private static final String NAME = "Name";
    private static final String AGE = "Age";
    private static final String GENDER = "Gender";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS" +TABLE_NAME;
    private static final String CREATE_TABLE  = format("CREATE TABLE%s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %sVARCHAR(255), %sINTEGER,%s VARCHAR(15) );", TABLE_NAME, ID, NAME, AGE, GENDER);
    private static final String SELECT_ALL = "SELECT * FROM" + TABLE_NAME;
    private Context context;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSON_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

       try{
           Toast.makeText(context, "onCreate is called: ", Toast.LENGTH_LONG).show();
           sqLiteDatabase.execSQL(CREATE_TABLE);
       }
       catch (Exception e){
           Toast.makeText(context, "Exception: "+e, Toast.LENGTH_LONG).show();
       }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            Toast.makeText(context, "onUpgrate is called: ", Toast.LENGTH_LONG).show();
            sqLiteDatabase.execSQL(DROP_TABLE);
            onCreate(sqLiteDatabase);
        }
        catch (Exception e){
            Toast.makeText(context, "Exception: "+e, Toast.LENGTH_LONG).show();
        }


    }

    public long insertData(String name, String age, String gender)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(AGE, age);
        contentValues.put(GENDER, gender);

        long insert = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }


    public Cursor displayAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_ALL,null);
        return cursor;

    }

    public boolean updateData(String id, String name, String age, String gender){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, id);
        contentValues.put(NAME, name);
        contentValues.put(AGE, age);
        contentValues.put(GENDER, gender);

         sqLiteDatabase.update(TABLE_NAME, contentValues, ID +" = ?", new String[]{id});
        return true;
    }

    public Integer deleteData(String id){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, ID+" = ?",new String[]{ id });

    }
}
