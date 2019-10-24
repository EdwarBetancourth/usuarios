package com.example.password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.password.classes.connectionDB;

public class MainActivity extends AppCompatActivity {

    EditText user;
    EditText pws;
    connectionDB bdu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.id_email);
        pws = (EditText) findViewById(R.id.id_password);

    }

    public void OpenSignupActivity(View view){
        Intent intent = new Intent(this, signup.class);
        startActivity(intent);
    }

    public void OpenSigninActivity(View view){
        Intent intent = new Intent(this, signin.class);
        startActivity(intent);
    }

    public void loginUser (View view){
        connectionDB manager = new connectionDB(this,"data",null,1);
        SQLiteDatabase bdu = manager.getWritableDatabase();
        String Email = user.getText().toString();
        String Passwd = pws.getText().toString();
        if(!Email.isEmpty() && !Passwd.isEmpty()){
            Cursor row = bdu.rawQuery("SELECT email " + "FROM users WHERE email = '"+Email+"'",null);
            if(row.moveToFirst()){
                Cursor row2 = bdu.rawQuery("SELECT password " + "FROM users WHERE password = '"+Passwd+"'",null);
                if(row2.moveToFirst()){
                    OpenSigninActivity(null);
                    bdu.close();
                }else{
                    Toast.makeText(this, "The password no is correct.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "The user already exists.",Toast.LENGTH_SHORT).show();
            }
        }else{
            user.setError("This field mustn't be empty");
            pws.setError("This field mustn't be empty");
            Toast.makeText(this, "You must complete all fields.", Toast.LENGTH_LONG).show();
        }
    }

}
