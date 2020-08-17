package com.example.mysqlitedetabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.security.cert.Extension;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEditText, ageEditText, genderEditText, idEditText;
    private Button displayAllDetaButton;
    private Button updateButton, addButton, deleteButton;
    MyDatabaseHelper myDatabaseHelper;
    private Extension view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase =  myDatabaseHelper.getWritableDatabase();

        nameEditText = (EditText) findViewById(R.id.nameEditTextId);
        ageEditText = (EditText) findViewById(R.id.ageEditTextId);
        genderEditText = (EditText) findViewById(R.id.genderEditTextId);
        idEditText = (EditText) findViewById(R.id.idEditTextId);
        addButton = (Button) findViewById(R.id.addButtonId);
        displayAllDetaButton = (Button) findViewById(R.id.displayAllDetaButtonId);
        updateButton = (Button) findViewById(R.id.updateAllDetaButtonId);
        deleteButton = (Button) findViewById(R.id.deleteAllDetaButtonId);

        addButton.setOnClickListener(this);
        displayAllDetaButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();
        String id = idEditText.getText().toString();


        if(view.getId()==R.id.addButtonId){

           long rowId =  myDatabaseHelper.insertData(name,age,gender);

           if(rowId == -1){

               Toast.makeText(getApplicationContext(), "Unseccessful", Toast.LENGTH_LONG).show();
           }else{

               Toast.makeText(getApplicationContext(), "Row " +rowId+ " is successfully insarted", Toast.LENGTH_LONG).show();

           }

        }

        if(view.getId() == R.id.displayAllDetaButtonId){

            Cursor cursor =  myDatabaseHelper.displayAllData();

            if(cursor.getCount() == 0){

                showData("Error", "No data Found");
                return;
            }
            StringBuilder stringBuffer = new StringBuilder();
            while(cursor.moveToNext()){

                stringBuffer.append("ID:"+cursor.getString(0)+"\n");
                stringBuffer.append("Name:"+cursor.getString(1)+"\n");
                stringBuffer.append("Age:"+cursor.getString(2)+"\n");
                stringBuffer.append("Gender:"+cursor.getString(3)+"\n");
            }

            showData("ResultSet", stringBuffer.toString());

        }

        else if(view.getId()==R.id.updateAllDetaButtonId){

            Boolean isUpdated =  myDatabaseHelper.updateData(id, name, age, gender);

            if(isUpdated==true){

                Toast.makeText(getApplicationContext(), "Data is updated", Toast.LENGTH_LONG).show();

            }else{

                Toast.makeText(getApplicationContext(), "Data is not updated", Toast.LENGTH_LONG).show();

            }

        }

        if(view.getId()==R.id.deleteAllDetaButtonId){

           int value = myDatabaseHelper.deleteData(id);
           if(value > 0){
               Toast.makeText(getApplicationContext(), "Data is deleted", Toast.LENGTH_LONG).show();
           }else{

               Toast.makeText(getApplicationContext(), "Data is not deleted", Toast.LENGTH_LONG).show();

           }
        }

    }

    public void  showData(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();

    }
}
