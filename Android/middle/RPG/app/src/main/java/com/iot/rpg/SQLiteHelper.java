package com.iot.rpg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String dbName = "RPG";
    private static final int dbVersion = 1;
    public static final String TABLE_NAME = "PLAYER";
    private static final String ID = "id";
    public static final String PASS = "pass";
    public static final String USER_ID = "user_id";
    private static final String SQL_CREATE_TABLE = " CREATE TABLE " + TABLE_NAME + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_ID + " TEXT UNIQUE, "
            + PASS + " TEXT);";

    public SQLiteHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
