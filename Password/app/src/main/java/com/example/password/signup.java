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

public class signup extends AppCompatActivity {

    EditText EMAIL;
    EditText PASSWORD;
    EditText PASSWORDC;
    EditText FIRSTNAME;
    EditText LASTNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Call id's
        EMAIL = findViewById(R.id.id_email);
        FIRSTNAME = findViewById(R.id.id_fname);
        LASTNAME = findViewById(R.id.id_lname);
        PASSWORD = findViewById(R.id.id_password);
        PASSWORDC = findViewById(R.id.id_passwordr);
    }

    public void Register(View view){
        //1. Create Database manager
        connectionDB manager = new connectionDB(this,
                "data",null,1);
        //2. Let Database Read/Write
        SQLiteDatabase bdu = manager.getWritableDatabase();
        //3. Get values/text/numbers
        String Email = EMAIL.getText().toString();
        String Passwd = PASSWORD.getText().toString();
        String Passwdr = PASSWORDC.getText().toString();
        String Lname = LASTNAME.getText().toString();
        String Fname = FIRSTNAME.getText().toString();


        //***Validate: Do not repeat email
        //Call validateEmail function/method
        //boolean status = validateEmail();

        //4. Validate empty data AND Create Content Values
        if(!Email.isEmpty() && !Passwd.isEmpty() &&
                !Lname.isEmpty() && !Fname.isEmpty()) {

            Cursor row = bdu.rawQuery("SELECT email " +
                    "FROM users WHERE email = '"+Email+"'",null);
            //if(row.getCount()<1)
            if(!row.moveToFirst()){
                if(Passwdr.equals(Passwd)){
                    if (validateEmail() ==  true) {
                        ContentValues DATA = new ContentValues();
                        //5. Create data pack
                        DATA.put("email", Email);
                        DATA.put("password", Passwd);
                        DATA.put("firstname", Fname);
                        DATA.put("lastname", Lname);
                        DATA.put("birth", "");
                        DATA.put("country", "");
                        DATA.put("phone", "");
                        DATA.put("gender", "");
                        //6. Save data into market Database
                        bdu.insert("users", null, DATA);
                        Toast.makeText(this, "The user has been created !!!", Toast.LENGTH_SHORT).show();
                        EMAIL.setText("");
                        FIRSTNAME.setText("");
                        LASTNAME.setText("");
                        PASSWORD.setText("");
                        PASSWORDC.setText("");
                        //7. Close connection
                        bdu.close();
                        OpenLoginActivity(null);
                    }else{
                        EMAIL.setError("Invalid email address");
                    }
                }else{
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "The user already exists.",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            EMAIL.setError("This field mustn't be empty");
            FIRSTNAME.setError("This field mustn't be empty");
            LASTNAME.setError("This field mustn't be empty");
            PASSWORD.setError("This field mustn't be empty");
            PASSWORDC.setError("This field mustn't be empty");
            Toast.makeText(this, "You must complete all fields.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void OpenLoginActivity(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public boolean validateEmail(){
        connectionDB manager = new connectionDB(this,"data",null,1);
        SQLiteDatabase bdu = manager.getWritableDatabase();
        String Email = EMAIL.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (Email.matches(emailPattern)){
            return true;
        }else{
            return false;
        }
    }

}

