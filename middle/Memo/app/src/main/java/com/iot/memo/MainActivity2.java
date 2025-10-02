package com.iot.memo;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.iot.memo.SQLiteHelper.TABLE_NAME;
import static com.iot.memo.SQLiteHelper.CONTENT;
import static com.iot.memo.SQLiteHelper.TITLE;
import static com.iot.memo.SQLiteHelper.ID;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private SQLiteHelper sqLiteHelper;
    private Cursor cursor;

    private TextView Title;
    private TextView Content;
    private EditText editText;

    private Button back;
    private Button prev;
    private Button next;
    private Button del;
    private Button edit;

    private boolean editing = false;

    @SuppressLint({"MissingInflatedId", "Range"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        sqLiteHelper = new SQLiteHelper(this);

        Title = findViewById(R.id.title);
        Content = findViewById(R.id.content);
        editText = findViewById(R.id.editContent);

        back = findViewById(R.id.back);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        del = findViewById(R.id.delete);
        edit = findViewById(R.id.editButton);

        cursor = readDB();

        if (cursor.moveToFirst()) {
            updateUIFromCursor();
        } else {
            //있긴한데 애초에 실행될 일이 없음
            Title.setText("저장된 메모가 없습니다.");
            Content.setText("");
            editText.setVisibility(INVISIBLE);

            prev.setEnabled(false);
            next.setEnabled(false);
            del.setEnabled(false);
            edit.setEnabled(false);
        }

        back.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
        });

        prev.setOnClickListener(v -> {
            if (cursor != null && !cursor.isClosed()) {
                if (!cursor.moveToPrevious()) {
                    Toast.makeText(MainActivity2.this, "첫 메모 입니다.", Toast.LENGTH_SHORT).show();
                    cursor.moveToFirst();
                }
                updateUIFromCursor();
            }
        });

        next.setOnClickListener(v -> {
            if (cursor != null && !cursor.isClosed()) {
                if (!cursor.moveToNext()) {
                    Toast.makeText(MainActivity2.this, "마지막 메모 입니다.", Toast.LENGTH_SHORT).show();
                    cursor.moveToLast();
                }
                updateUIFromCursor();
            }
        });

        del.setOnClickListener(v -> {
            if (cursor != null && !cursor.isClosed()) {
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                deleteDB(id);
                Toast.makeText(MainActivity2.this, "삭제 완료.", Toast.LENGTH_SHORT).show();

                cursor.close();
                cursor = readDB();

                if (cursor.moveToFirst()) {
                    updateUIFromCursor();
                } else {
                    Title.setText("저장된 메모가 없습니다.");
                    Content.setText("");
                    editText.setVisibility(INVISIBLE);

                    prev.setEnabled(false);
                    next.setEnabled(false);
                    del.setEnabled(false);
                    edit.setEnabled(false);
                }
            }
        });

        edit.setOnClickListener(v -> {
            if (cursor == null || cursor.isClosed()) return;

            if (!editing) {
                // 편집 시작
                editing = true;
                editText.setText(Content.getText().toString());
                Content.setVisibility(INVISIBLE);
                editText.setVisibility(VISIBLE);

                prev.setEnabled(false);
                next.setEnabled(false);
                back.setEnabled(false);
                del.setEnabled(false);

                edit.setText("저장");
            } else {
                // 편집 완료 및 DB 업데이트
                editing = false;
                String newTitle = Title.getText().toString();
                String newContent = editText.getText().toString();

                int id = cursor.getInt(cursor.getColumnIndex(ID));
                updateDB(id, newTitle, newContent);

                Content.setText(newContent);
                Content.setVisibility(VISIBLE);
                editText.setVisibility(INVISIBLE);

                prev.setEnabled(true);
                next.setEnabled(true);
                back.setEnabled(true);
                del.setEnabled(true);

                edit.setText("편집");

                // 커서 재조회 및 UI 업데이트 (반영 확인)
                cursor.close();
                cursor = readDB();
                cursor.moveToFirst();
                updateUIFromCursor();

                Toast.makeText(MainActivity2.this, "수정 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("Range")
    private void updateUIFromCursor() {
        if (cursor != null && !cursor.isClosed() && !cursor.isAfterLast() && !cursor.isBeforeFirst()) {
            Title.setText(cursor.getString(cursor.getColumnIndex(TITLE)));
            Content.setText(cursor.getString(cursor.getColumnIndex(CONTENT)));
            editText.setVisibility(INVISIBLE);
            Content.setVisibility(VISIBLE);
        }
    }

    private Cursor readDB() {
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    private void updateDB(int id, String newTitle, String newContent) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, newTitle);
        values.put(CONTENT, newContent);
        db.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    private void deleteDB(int id) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        try {
            db.execSQL(
                    "DELETE FROM " + TABLE_NAME + " WHERE " + ID + "= ?", new Object[]{id}
            );
        } finally {
            db.close();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }
}