package com.iot.rpg;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdventureActivity extends AppCompatActivity implements getReward, printStats {

    private int stage = 1;
    private ProgressBar progressBar, timingBar;
    private Button move, end, timingButton;
    private TextView stats, equips;
    private FrameLayout frameLayout;
    private Map map;
    public ImageView criticalImage;
    private ImageView select1, select2, select3;

    private int select;
    private boolean move_statement = false;

    @SuppressLint({"MissingInflatedId", "CutPasteId"})
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
        timingBar = findViewById(R.id.timingBar);
        timingButton = findViewById(R.id.timingBtn);
        criticalImage = findViewById(R.id.criticalImage);
        select1 = findViewById(R.id.select1);
        select2 = findViewById(R.id.select2);
        select3 = findViewById(R.id.select3);

        Adventurer adventurer = new Adventurer();
        adventurer.resetAdventurer();
        Event event = new Event();
        move.setVisibility(INVISIBLE);
        end.setVisibility(INVISIBLE);
        ChangeJob changejob = new ChangeJob(this, timingBar, timingButton, adventurer);

        printStats(adventurer, equips);

        map = new Map(this, stage);
        frameLayout.addView(map);
        //setContentView(new Fight(this, adventurer, new Monster(50, 50, 100)));

        move.setOnClickListener(v -> {

            switchButtons(true);
            if (stage == 10) {Fight fight = new Fight(this, this, stage,  adventurer, new Monster(90, 50, 600), timingButton);
                select = 0;
                frameLayout.removeAllViews();
                frameLayout.addView(fight);
                move.setVisibility(INVISIBLE);
                end.setVisibility(INVISIBLE);
            }


            if(select==2){
                frameLayout.removeAllViews();
                Fight fight;
                if(stage <= 9)
                {fight = new Fight(this, this, stage, adventurer, new Monster(20+7*stage, 10+5*stage, 80 + 25 * stage), timingButton);
                    frameLayout.addView(fight);
                    move.setVisibility(INVISIBLE);
                    end.setVisibility(INVISIBLE);
                }
                event.EquipEvent(adventurer);

            }
            else if(select==1)
            {
                event.RandomEvent(adventurer);
            }
            else if(select==3)
            {
                frameLayout.removeAllViews();
                event.adventurerHealed(adventurer);
                frameLayout.setBackgroundResource(R.drawable.healed);
            }
            printStats(adventurer, stats);
            printEquips(adventurer, equips);
            progressBar.setProgress(stage * 11);
        });

        end.setOnClickListener(v -> {
            printStats(adventurer, stats);
            printEquips(adventurer, equips);

            if(adventurer.getCurrHP()<=0) {
                Intent intent = new Intent(AdventureActivity.this, Result.class);
                intent.putExtra("결과", false);
                startActivity(intent);
            }
            if(stage == 10 && adventurer.getCurrHP()>0)
            {
                Intent intent = new Intent(AdventureActivity.this, Result.class);
                intent.putExtra("결과", true);
                startActivity(intent);
            }
            stage++;
            frameLayout.removeAllViews();
            map = new Map(this, stage);
            frameLayout.addView(map);
            switchButtons(false);

//            adventurer.getEquip(new Adventurer.Equipment(900,-10,900,"임시검"));
            printStats(adventurer, stats);
            printEquips(adventurer, equips);
            progressBar.setProgress(stage * 11);

            if(stage != 10) imageSelect(select1, select2, select3, move, end);

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
            move.setVisibility(INVISIBLE);
            end.setEnabled(true);
            end.setVisibility(VISIBLE);
        } else {
            end.setEnabled(false);
            end.setVisibility(INVISIBLE);
            move.setEnabled(true);
            move.setVisibility(VISIBLE);
        }
    }

    public void toastMessage(String Message){
        Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void applyJob(Adventurer adventurer, int score) {
        Toast.makeText(this, "점수는 "+score+"점 입니다.", Toast.LENGTH_SHORT).show();

        if(score < 70 ) adventurer.getJob("용병");
        else if(score <85) adventurer.getJob("기사");
        else if(score < 95) adventurer.getJob("방패 기사");
        else adventurer.getJob("버서커");

        printStats(adventurer, stats);
        printEquips(adventurer, equips);
        Toast.makeText(this, "당신은 "+ adventurer.getJob()+"으로 전직했습니다!", Toast.LENGTH_SHORT).show();
        move.setVisibility(VISIBLE);

        imageSelect(select1, select2, select3, move, end);
    }

    @Override
    public Adventurer afterFight(Adventurer adventurer) {
        return null;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void printFighters(Adventurer adventurer, Monster monster) {
        stats.setText("모험가의 체력" + adventurer.getCurrHP()+"\n\n" + "몬스터의 체력" + monster.getHP());
    }

    @Override
    public void enabledClick() {
        move.setVisibility(VISIBLE);
        end.setVisibility(VISIBLE);
    }

    public void setCriticalImage(double critical){

        if(critical ==1) criticalImage.setImageResource(R.drawable.ciriticals);
        else if(critical == 2) criticalImage.setImageResource(R.drawable.goods);
        else criticalImage.setImageResource(R.drawable.normals);
    }

    public boolean getFlag()
    {
        if(timingBar.getVisibility() == VISIBLE)
        {
            return false;
        }
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void imageSelect(ImageView s1, ImageView s2, ImageView s3, Button btn1, Button btn2)
    {
        select = 0;
        s1.setVisibility(VISIBLE);
        s2.setVisibility(VISIBLE);
        s3.setVisibility(VISIBLE);
        btn1.setEnabled(false);
        btn2.setEnabled(false);

        s1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                select = 1;
                btn1.setEnabled(true);
                btn2.setEnabled(true);
                s1.setVisibility(INVISIBLE);
                s2.setVisibility(INVISIBLE);
                s3.setVisibility(INVISIBLE);
                return false;
            }
        });
        s2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                select = 2;
                btn1.setEnabled(true);
                btn2.setEnabled(true);
                s1.setVisibility(INVISIBLE);
                s2.setVisibility(INVISIBLE);
                s3.setVisibility(INVISIBLE);
                return false;
            }
        });
        s3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                select = 3;
                btn1.setEnabled(true);
                btn2.setEnabled(true);
                s1.setVisibility(INVISIBLE);
                s2.setVisibility(INVISIBLE);
                s3.setVisibility(INVISIBLE);
                return false;
            }
        });
    }
}