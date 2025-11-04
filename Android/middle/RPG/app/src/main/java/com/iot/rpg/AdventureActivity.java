package com.iot.rpg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdventureActivity extends AppCompatActivity {

    private int stage = 0;
    private ProgressBar progressBar;
    private Button move, end;
    private TextView stats, equips;
    private FrameLayout frameLayout;
    private Map map;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adventure);

        move = findViewById(R.id.move);
        end = findViewById(R.id.end);
        progressBar = findViewById(R.id.progressBar);
        stats = findViewById(R.id.stats);
        equips = findViewById(R.id.equipments);
        frameLayout = findViewById(R.id.mainFrame);

        Adventurer adventurer = new Adventurer();
        adventurer.resetAdventurer();
        Event event = new Event();

        printStats(adventurer, equips);

        map = new Map(this, stage);
        frameLayout.addView(map);
        //setContentView(new Fight(this, adventurer, new Monster(50, 50, 100)));

        move.setOnClickListener(v -> {
            frameLayout.removeAllViews();
            Fight fight = new Fight(this, adventurer, new Monster(10, 10, 10)); // 새로 생성!
            frameLayout.addView(fight);
            switchButtons(true);
            printStats(adventurer, stats);
            printEquips(adventurer, equips);
            progressBar.setProgress(stage * 11);
        });

        end.setOnClickListener(v -> {
            stage++;
            frameLayout.removeAllViews();
            map = new Map(this, stage);
            frameLayout.addView(map);
            switchButtons(false);
            printStats(adventurer, stats);
            printEquips(adventurer, equips);
            progressBar.setProgress(stage * 11);
        });
    }

//    @SuppressLint("SetTextI18n")
//    private void setField(int stage, ImageView image){
//        switch(stage)
//        {
//
//            case 3:
//                image.setImageResource(R.drawable.forest);
//                move.setText((stage+1) + "스테이지로");
//                break;
//            case 6:
//                image.setImageResource(R.drawable.castle);
//                move.setText((stage+1) + "스테이지로");
//                break;
//            case 9:
//                move.setText("마지막 스테이지로");
//                break;
//            default :
//                move.setText((stage+1) + "스테이지로");
//                break;
//
//        }
//    }


    // 화면 위 왼쪽에 유저 스탯 표시
    @SuppressLint("SetTextI18n")
    void printStats(Adventurer adventurer, TextView textView) {
        textView.setText("직업 : " + adventurer.getJob() + "\n" + "HP : " + adventurer.getCurrHP() + "/" +
                adventurer.getMaxHP() + "\nATK : " + adventurer.getATK() + "\nDEF : " +
                adventurer.getDEF()
        );
    }

    // 화면 위 오른쪽에 유저가 얻은 장비 표시
    void printEquips(Adventurer adventurer, TextView textView) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("장비창 : \n");
        for (Adventurer.Equipment i : adventurer.getEquipList()) {
            stringBuilder.append(i.getEquipinfo()).append("\n");
        }
        textView.setText(stringBuilder);
    }

    void switchButtons(boolean on) {
        if (move == null || end == null) return; // 예외 방지
        if (on) {
            move.setEnabled(false);
            move.setVisibility(View.INVISIBLE);
            end.setEnabled(true);
            end.setVisibility(View.VISIBLE);
        } else {
            end.setEnabled(false);
            end.setVisibility(View.INVISIBLE);
            move.setEnabled(true);
            move.setVisibility(View.VISIBLE);
        }
    }
}