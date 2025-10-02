package com.iot.memo;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String dbName = "MEMO";
    private static final int dbVersion = 1;
    public static final String TABLE_NAME = "memoApp";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    private static final String SQL_CREATE_TABLE = " CREATE TABLE " + TABLE_NAME + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITLE + " TEXT, "
            + CONTENT + " TEXT);";

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
