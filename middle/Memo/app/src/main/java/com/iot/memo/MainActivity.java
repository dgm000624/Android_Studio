package com.iot.memo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import static com.iot.memo.SQLiteHelper.TABLE_NAME;
import static com.iot.memo.SQLiteHelper.CONTENT;
import static com.iot.memo.SQLiteHelper.TITLE;

public class MainActivity extends AppCompatActivity {

    private SQLiteHelper sqLiteHelper;
    private EditText Title;
    private EditText Content;
    private Button Save;
    private Button list;
    private Memo memo;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        memo = (Memo)getApplication();
        Title = findViewById(R.id.Title);
        Content = findViewById(R.id.Content);
        Save = findViewById(R.id.Save);
        list = findViewById(R.id.list);

        sqLiteHelper = new SQLiteHelper(this);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeDB(Title.getText().toString().trim(), Content.getText().toString().trim());
                Toast.makeText(MainActivity.this, "저장 완료", Toast.LENGTH_SHORT).show();
                Title.setText("");
                Content.setText("");
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!readDB()){Toast.makeText(MainActivity.this, "저장된 메모가 없습니다.",
                        Toast.LENGTH_SHORT).show(); return;}

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

    }

    private void writeDB(String title, String content){
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(CONTENT, content);
        db.insertOrThrow(TABLE_NAME, null, values);
    }

    private boolean readDB(){
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_NAME, null
            );
            return cursor.moveToFirst();
        } finally {
            if (cursor != null) cursor.close();
        }
    }
}