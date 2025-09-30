package com.iot.memo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private int index;
    private TextView Title;
    private TextView Content;
    private Memo memo;

    private Button back;
    private Button prev;
    private Button next;
    private Button del;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        index = 0;

        memo = (Memo)getApplication();
        Title = findViewById(R.id.title);
        Content = findViewById(R.id.content);

        back = findViewById(R.id.back);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        del = findViewById(R.id.delete);

        Title.setText(memo.IndexMemo(index).getTitle());
        Content.setText(memo.IndexMemo(index).getMemo());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index <=0) {
                    Toast.makeText(MainActivity2.this, "첫 메모 입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    index--;
                    Title.setText(memo.IndexMemo(index).getTitle());
                    Content.setText(memo.IndexMemo(index).getMemo());
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index >= memo.memos.size()-1) {
                    Toast.makeText(MainActivity2.this, "마지막 메모 입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    index++;
                    Title.setText(memo.IndexMemo(index).getTitle());
                    Content.setText(memo.IndexMemo(index).getMemo());
                }
            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((index >= memo.memos.size()-1)&&index <=0){
                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    memo.memos.remove(index);
                    Toast.makeText(MainActivity2.this, "저장된 메모가 없습니다",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else if (index <= 0) {
                    memo.memos.remove(index);
                    Title.setText(memo.IndexMemo(index).getTitle());
                    Content.setText(memo.IndexMemo(index).getMemo());
                    Toast.makeText(MainActivity2.this, "삭제 완료",Toast.LENGTH_SHORT).show();
                }
                else {
                    memo.memos.remove(index);
                    index--;
                    Title.setText(memo.IndexMemo(index).getTitle());
                    Content.setText(memo.IndexMemo(index).getMemo());
                    Toast.makeText(MainActivity2.this, "삭제 완료",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}