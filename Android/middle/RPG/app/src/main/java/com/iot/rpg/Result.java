package com.iot.rpg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

// 결과화면 전용 액티비티. 뭐 없음

public class Result extends AppCompatActivity {

    public ImageView result;
    public Button toTitle;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        result = findViewById(R.id.result);
        toTitle = findViewById(R.id.toTitle);

        Intent intent = getIntent();
        boolean bossResult;
        bossResult = intent.getBooleanExtra("결과", false);
        if(bossResult) Victory();
        else Death();

        toTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, ImageActivity.class);
                startActivity(intent);
            }
        });
    }

    // 보스전 승리시 이동
    public void Victory(){
        result.setImageResource(R.drawable.treasure);
    }

    // 사망시 이동
    public void Death(){
        result.setImageResource(R.drawable.death);
    }
}