package com.iot.rpg;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.iot.rpg.SQLiteHelper.PASS;
import static com.iot.rpg.SQLiteHelper.TABLE_NAME;
import static com.iot.rpg.SQLiteHelper.USER_ID;

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


public class ImageActivity extends AppCompatActivity {

    private SQLiteHelper sqLiteHelper;
    private Button toAdventure, newMember, enroll;
    private EditText id;
    private EditText pass;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image);

        toAdventure = findViewById(R.id.ImageBtn);
        sqLiteHelper = new SQLiteHelper(this);


        id = findViewById(R.id.idEdit);
        pass = findViewById(R.id.passEdit);
        newMember = findViewById(R.id.newBtn);
        enroll = findViewById(R.id.enroll);





        toAdventure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (readDB(id.getText().toString().trim(), pass.getText().toString().trim())) {
                    Toast.makeText(ImageActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ImageActivity.this, AdventureActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ImageActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                    id.setText("");
                    pass.setText("");
                }

            }
        });
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().trim().isEmpty() || pass.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(ImageActivity.this, "ID 또는 PASS 미입력. 다시 진행하세요", Toast.LENGTH_LONG).show();
                    enroll.setVisibility(INVISIBLE);
                } else if (checkID(id.getText().toString().trim())) {
                    Toast.makeText(ImageActivity.this, "중복된 ID", Toast.LENGTH_SHORT).show();
                    id.setText("");
                } else
                {
                    writeDB(id.getText().toString().trim(), pass.getText().toString().trim());
                    Toast.makeText(ImageActivity.this, "등록완료", Toast.LENGTH_SHORT).show();
                    enroll.setVisibility(INVISIBLE);
                }
            }
        });

        newMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.setText("");
                pass.setText("");
                Toast.makeText(ImageActivity.this, "등록할 ID,PASS 입력후 확인 클릭", Toast.LENGTH_LONG).show();
                enroll.setVisibility(VISIBLE);

            }
        });

    }
    private boolean readDB(String userId, String password){
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_NAME + " WHERE " + USER_ID + " = ? AND " + PASS + " = ?",
                    new String[]{ userId, password }
            );
            return cursor.moveToFirst();
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    private void writeDB(String id, String pass){
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, id);
        values.put(PASS, pass);
        db.insertOrThrow(TABLE_NAME, null, values);
    }

    private boolean checkID(String userId){
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_NAME + " WHERE " + USER_ID + " = ?",
                    new String[]{ userId}
            );
            return cursor.moveToFirst();
        } finally {
            if (cursor != null) cursor.close();
        }
    }
}