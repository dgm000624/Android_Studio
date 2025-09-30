package com.iot.rpg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

// 전투 기능 구현용

public class Fight extends SurfaceView implements SurfaceHolder.Callback {
    private final SurfaceHolder holder;
    private Player player;
    private Monster monster;
    private boolean IsFight;
    private Adventurer temp_adventurer;
    private Monster temp_monster;
    private Button timingBtn;
    private printStats stats;
    private Context context;
    private volatile boolean running = false;

    private int stage;
    private boolean timingflag = false;
    private int criticalFlag = 0;
    private int buttonInvalid = -1;
    private double multiple;
    private int streng;

    public Fight(Context context, printStats stats, int stage, Adventurer adventurer, Monster monster, Button button) {
        super(context);
        this.context = context;
        this.timingBtn = button;
        this.stats = stats;
        this.temp_adventurer = adventurer;
        this.temp_monster = monster;
        this.holder = getHolder();
        this.holder.addCallback(this);
        this.stage = stage;
        ((AdventureActivity)context).runOnUiThread(()->
                timingBtn.setVisibility(VISIBLE));

        ((AdventureActivity)context).runOnUiThread(()->
                ((AdventureActivity) context).criticalImage.setVisibility(VISIBLE));

        timingBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AdventureActivity)context).runOnUiThread(()->
                        timingBtn.setVisibility(INVISIBLE));
                timingflag = true;
                buttonInvalid = 0;
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        monster = new Monster(temp_monster.getATK(), temp_monster.getDEF(), temp_monster.getHP());
        player = new Player();

        if(stage>=1 && stage <=3) monster.setImage(getResources().getDrawable(R.drawable.goblin1, null));
        else if(stage>=4 && stage <=6) monster.setImage(getResources().getDrawable(R.drawable.orc1, null));
        else if(stage>=7 && stage <=9) monster.setImage(getResources().getDrawable(R.drawable.boss, null));
        else monster.setImage(getResources().getDrawable(R.drawable.darkknight, null));
        player.setImage(getResources().getDrawable(R.drawable.adventurer, null));

        monster.setSize(new Point(300, 300));
//        if(stage==10) monster.setSize(new Point(500,500));
        monster.setPoint(new Point(width - 10, height / 2));
        player.setSize(new Point(300, 300));
        player.setPoint(new Point(-290, height / 2));

        running = true;

        Thread renderer = new Thread(() -> {


            Drawable drawable = getResources().getDrawable(R.drawable.arena, null);
            drawable.setBounds(holder.getSurfaceFrame());
            player.setDelta(10, 0);
            monster.setDelta(-10, 0);
            IsFight = true;

            while (running && IsFight) {
                if (Math.abs(player.getPoint().x - monster.getPoint().x) < 3) {
                    player.player_set(holder.getSurfaceFrame());
                    monster.monster_set(holder.getSurfaceFrame());

                    if(timingflag&&(criticalFlag<=2)){multiple = 1.4; streng = 1;}
                    else if(timingflag) {multiple = 1.2; streng= 2;}
                    else {multiple = 1.0; streng = 3;}

                    IsFight = monsterFight(temp_adventurer, temp_monster, multiple);
                    ((Activity) context).runOnUiThread(() -> {
                        ((AdventureActivity) context).printFighters(temp_adventurer, temp_monster);
                    });

                    if (!IsFight) {
                        if (context instanceof AdventureActivity) {
                            ((AdventureActivity) context).runOnUiThread(() -> {
                                ((AdventureActivity) context).enabledClick();
                                ((AdventureActivity) context).switchButtons(true);
                                ((AdventureActivity) context).toastMessage("결과 종료!");
                            });
                        }

                        break;
                    }
                }

                player.move(holder.getSurfaceFrame());
                monster.move(holder.getSurfaceFrame());

                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    drawable.draw(canvas);
                    player.draw(canvas);
                    monster.draw(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }

                try {
                    Thread.sleep(16);
                    if(timingflag){
                        criticalFlag++;
                        if(buttonInvalid == 0) buttonInvalid++;
                        if(criticalFlag == 4){
                            timingflag = false;
                            criticalFlag = 0;
                        }
                    }
                    if(buttonInvalid !=-1){
                        buttonInvalid++;
                        if(buttonInvalid >= 20){
                            ((AdventureActivity)context).runOnUiThread(()->
                                    timingBtn.setVisibility(VISIBLE));
                            buttonInvalid = -1;
                        }
                        else ((AdventureActivity)context).runOnUiThread(()->
                                timingBtn.setVisibility(INVISIBLE));
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        renderer.start();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        running = false;
        ((AdventureActivity)context).runOnUiThread(()->
                timingBtn.setVisibility(INVISIBLE));
                ((AdventureActivity) context).criticalImage.setImageDrawable(null);
                ((AdventureActivity) context).criticalImage.setVisibility(INVISIBLE);
    }

    // 전투가 종료되면(모험자나 몬스터의 HP가 0이되면) false 반환 아니면 true 반환
    public boolean monsterFight(Adventurer adventurer, Monster monster, double multiple) {
        monster.setHP((int) (monster.getHP() - Math.max(adventurer.getATK() * multiple - monster.getDEF(), 1)));
        if (monster.getHP() <= 0) {
            return false;
        } else if (!adventurer.damaged((int) ((Math.max(monster.getATK() / multiple - adventurer.getDEF(), 1))))) {
            return false;
        }
        criticaled(context, multiple);
        return true;
    }

    private void criticaled(Context context, double multiple){
        ((AdventureActivity)context).runOnUiThread(()->
                ((AdventureActivity) context).setCriticalImage(streng));
    }
}
