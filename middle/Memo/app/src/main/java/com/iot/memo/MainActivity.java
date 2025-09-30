package com.iot.memo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memo.AddMemo(new Memo(Title.getText().toString(), Content.getText().toString()));
                Toast.makeText(MainActivity.this, "저장 완료", Toast.LENGTH_SHORT).show();
                Title.setText("");
                Content.setText("");
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(memo.memos.isEmpty()){Toast.makeText(MainActivity.this, "저장된 메모가 없습니다.", Toast.LENGTH_SHORT).show(); return;}

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

    }
}