package com.iot.sqlite;



import static com.iot.sqlite.SQLiteHelper.TITLE;
import static com.iot.sqlite.SQLiteHelper.TIME;
import static com.iot.sqlite.SQLiteHelper.TABLE_NAME;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private SQLiteHelper sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sqLiteHelper = new SQLiteHelper(this);
        writeDB("Hello Android");
        Cursor cursor = readDB();
        displayDB(cursor);
        sqLiteHelper.close();
    }
    private static final ZoneId ZONE = ZoneId.of("Asia/Seoul");
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss", Locale.KOREA);

    private static String formatEpochMillies(long millies) {
        return Instant.ofEpochMilli(millies).atZone(ZONE).format(FMT);
    }

    private void displayDB(Cursor cursor){
        StringBuilder builder = new StringBuilder("Saved DB: \n");
        while(cursor.moveToNext()){
           long id = cursor.getLong(0);
           long time = cursor.getLong(1);
           String title = cursor.getString(2);
           builder.append(id).append(": ");
           builder.append(formatEpochMillies(time)).append(": ");
           builder.append(title).append("\n");
           TextView textView = findViewById(R.id.textView);
           textView.setText(builder);
        }
    }


    private Cursor readDB() {
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
    private void writeDB(String title){
        SQLiteDatabase db =sqLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TIME, System.currentTimeMillis());
        values.put(TITLE, title);
        db.insertOrThrow(TABLE_NAME, null, values);
    }
}