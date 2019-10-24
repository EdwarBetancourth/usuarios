package com.example.password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.password.classes.connectionDB;

public class usersUpdate extends AppCompatActivity {

    Spinner countrie;
    Spinner gender;
    EditText fname;
    EditText lname;
    EditText email;
    EditText password;
    EditText birth;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_update);

        fname = (EditText) findViewById(R.id.id_fname);
        lname = (EditText) findViewById(R.id.id_lname);
        email = (EditText) findViewById(R.id.id_email);
        password = (EditText) findViewById(R.id.id_password);
        birth = (EditText) findViewById(R.id.id_birth);
        phone = (EditText) findViewById(R.id.id_phone);
        countrie = (Spinner) findViewById(R.id.id_countries);
        gender = (Spinner) findViewById(R.id.id_gender);

        ArrayAdapter<CharSequence> adapterC = ArrayAdapter.createFromResource(this,R.array.Countries, android.R.layout.simple_spinner_item);
        countrie.setAdapter(adapterC);
        ArrayAdapter<CharSequence> adapterG = ArrayAdapter.createFromResource(this,R.array.Genders, android.R.layout.simple_spinner_item);
        gender.setAdapter(adapterG);

        String llave;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                llave= null;
            } else {
                llave= extras.getString("Recuperar");
            }
        } else {
            llave= (String) savedInstanceState.getSerializable("Recuperar");
        }
        rellenarUsers(llave);

    }

    public void OpenSigninActivity(View view){
        Intent intent = new Intent(this, signin.class);
        startActivity(intent);
    }

    public void rellenarUsers(String info){
        String definitive = info.substring(0,info.indexOf(" "));
        Toast.makeText(this, "esta si es "+definitive, Toast.LENGTH_SHORT).show();
        fname.setText(definitive);
        connectionDB manager = new connectionDB(this,
                "data",null,1);
        SQLiteDatabase bdu = manager.getWritableDatabase();

        Cursor row = bdu.rawQuery("SELECT * FROM users WHERE firstname = '"+definitive+"'",null);
        if(row.getCount() == 0){
            Toast.makeText(this,
                    "Empty Data Base", Toast.LENGTH_SHORT).show();
        }else{
            while(row.moveToNext()){
                fname.setText(row.getString(1));
                lname.setText(row.getString(2));
                email.setText(row.getString(3));
                email.setEnabled(false);
                password.setText(row.getString(4));
                birth.setText(row.getString(5));
                phone.setText(row.getString(7));
                countrie.setSelection(1);
                gender.setSelection(1);
            }
        }
    }

    public void UpdateUsers (View view){
        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
            connectionDB manager = new connectionDB(this,"data",null,1);
            SQLiteDatabase bdu = manager.getWritableDatabase();
            String Emailt = email.getText().toString();
            String Passwdt = password.getText().toString();
            String Lnamet = lname.getText().toString();
            String Fnamet = fname.getText().toString();
            String birtht = birth.getText().toString();
            String phonet = phone.getText().toString();
            String countriest = countrie.getSelectedItem().toString();
            String gendert = gender.getSelectedItem().toString();
            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
            if(!Emailt.isEmpty() && !Passwdt.isEmpty() && !Lnamet.isEmpty() && !Fnamet.isEmpty()) {

                Cursor row = bdu.rawQuery("SELECT email " + "FROM users WHERE email = '"+Emailt+"'",null);
                Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
                    ContentValues DATA = new ContentValues();
                    //5. Create data pack
                    DATA.put("email", Emailt);
                    DATA.put("password", Passwdt);
                    DATA.put("firstname", Fnamet);
                    DATA.put("lastname", Lnamet);
                    DATA.put("birth", birtht);
                    DATA.put("country", countriest);
                    DATA.put("phone", phonet);
                    DATA.put("gender", gendert );
                    bdu.update("users", DATA, "email='"+Emailt+"'", null);
                    Toast.makeText(this, "The user has been update !!!",Toast.LENGTH_SHORT).show();
                    fname.setText("");
                    lname.setText("");
                    email.setText("");
                    password.setText("");
                    birth.setText("");
                    phone.setText("");
                    countrie.setSelection(1);
                    gender.setSelection(1);
                    bdu.close();
                    OpenSigninActivity(null);
            }else{
                email.setError("This field mustn't be empty");
                fname.setError("This field mustn't be empty");
                lname.setError("This field mustn't be empty");
                password.setError("This field mustn't be empty");
                Toast.makeText(this, "You must complete all fields.", Toast.LENGTH_LONG).show();
            }
    }
}
