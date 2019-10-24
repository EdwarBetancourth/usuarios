package com.example.password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.password.classes.connectionDB;

import java.util.ArrayList;

public class signin extends AppCompatActivity {

    //Call Data Base class connection
    connectionDB dbu;
    //Create a ListView variable
    ListView userlist;
    //Create an array List variable
    ArrayList<String> listItem;
    //Create an adapter variable
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //Instance DB connection
        dbu = new connectionDB(this, "data",
                null, 1);
        //Create an empty array
        listItem = new ArrayList<>();
        //Call ListView id
        userlist = findViewById(R.id.idUserList);
        //List users information
        viewData();
        //Events
        userlist.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String text = userlist.getItemAtPosition(i).toString();
                        Intent intent = new Intent(signin.this, usersUpdate.class);
                        intent.putExtra("Recuperar",text);
                        startActivity(intent);
                        Toast.makeText(signin.this,"Info:" +text, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void viewData() {
        Cursor cursor = dbu.SelectUsersData();

        if(cursor.getCount() == 0){
            Toast.makeText(this,
                    "Empty Data Base", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                String datosU = cursor.getString(1)+" "+cursor.getString(2)
                        +"\n"+cursor.getString(3);
                listItem.add(datosU);
            }
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, listItem);
            userlist.setAdapter(adapter);
        }

    }


}
