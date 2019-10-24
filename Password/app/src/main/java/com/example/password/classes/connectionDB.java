package com.example.password.classes;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class connectionDB extends SQLiteOpenHelper {
    public connectionDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase bdu) {
        //Create table(s)
        bdu.execSQL("create table users(" +
                "id integer primary key autoincrement not null," +
                "firstname text not null," +
                "lastname text not null," +
                "email text not null," +
                "password text not null," +
                "birth date null," +
                "country text null," +
                "phone text null," +
                "gender text null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Cursor SelectUsersData(){
        SQLiteDatabase bdu = this.getReadableDatabase();
        Cursor rows = bdu.rawQuery(
                "SELECT * FROM users", null);
        return rows;
    }
}
