package com.example.mysqlitedetabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static android.database.sqlite.SQLiteDatabase.*;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student.db";
    private static final String TABLE_NAME = "Student_details";
    private static final String VERSON_NUMBER = "2";
    private static final String ID = "_id";
    private static final String NAME = "Name";
    private static final String AGE = "Age";
    private static final String GENDER = "Gender";
    private static final String DROP_TABLE = "DROP TABLE IF EXIST" +TABLE_NAME;
    private static final String CREATE_TABLE  = "\"CREATE TABLE \"+TABLE_NAME+\"(\"+ID+\" INTEGER KEY AUTOINCREMENT, \"+NAME+\" VARCHAR(255), \"_+AGE+\" INTEGER, "+GENDER+" VARCHAR(255)";
    private static final String SELECT_ALL = "SELECT \"* FROMStudent_details";
    private Context context;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, Integer.parseInt(VERSON_NUMBER));
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

       try{
           Toast.makeText(context, "onCreate is called: ", Toast.LENGTH_LONG).show();
           SQLiteDatabase.execSQL(CREATE_TABLE);
       }
       catch (Exception e){
           Toast.makeText(context, "Exception: "+e, Toast.LENGTH_LONG).show();
       }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            Toast.makeText(context, "onUpgrate is called: ", Toast.LENGTH_LONG).show();
            SQLiteDatabase.execSQL(DROP_TABLE);
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

        long rowID = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return rowID;
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
