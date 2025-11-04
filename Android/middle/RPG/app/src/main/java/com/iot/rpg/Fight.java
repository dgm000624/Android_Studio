package com.iot.rpg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButtonToggleGroup;

// 전투 기능 구현용

public class Fight extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = Fight.class.getName();
    private final SurfaceHolder holder;
    private Player player;
    private Monster monster;
    private boolean IsFight;
    private Adventurer temp_adventurer;
    private Monster temp_monster;
    private Button proceed;
    private Context context;
    private volatile boolean running = false;

    public Fight(Context context, Adventurer adventurer, Monster monster) {
        super(context);
        this.context = context;
        this.temp_adventurer = adventurer;
        this.temp_monster = monster;
        this.holder = getHolder();
        this.holder.addCallback(this);
    }

    public boolean isFight() {
        return IsFight;
    }

    public void setFight(boolean fight) {
        IsFight = fight;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        monster = new Monster(temp_monster.getATK(), temp_monster.getDEF(), temp_monster.getHP());
        player = new Player();

        monster.setImage(getResources().getDrawable(R.drawable.boss, null));
        player.setImage(getResources().getDrawable(R.drawable.adventurer, null));

        monster.setSize(new Point(300, 300));
        monster.setPoint(new Point(width - 10, height / 2));
        player.setSize(new Point(300, 300));
        player.setPoint(new Point(-290, height / 2));

        running = true;

        Thread renderer = new Thread(() -> {
            Drawable drawable = getResources().getDrawable(R.drawable.filed, null);
            drawable.setBounds(holder.getSurfaceFrame());
            player.setDelta(10, 0);
            monster.setDelta(-10, 0);
            IsFight = true;

            while (running && IsFight) {
                if (Math.abs(player.getPoint().x - monster.getPoint().x) < 3) {
                    player.player_set(holder.getSurfaceFrame());
                    monster.monster_set(holder.getSurfaceFrame());
                    IsFight = monsterFight(temp_adventurer, temp_monster);

                    if (!IsFight) {
                        if (context instanceof AdventureActivity) {
                            ((AdventureActivity) context).runOnUiThread(() -> {
                                ((AdventureActivity) context).switchButtons(true);
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
                    Thread.sleep(16); // 60fps
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
    }

    public boolean monsterFight(Adventurer adventurer, Monster monster) {
        monster.setHP(monster.getHP() - Math.max(adventurer.getATK() - monster.getDEF(), 1));
        if (monster.getHP() <= 0) {
            return false;
        } else if (!adventurer.damaged(Math.max(monster.getATK() - adventurer.getDEF(), 1))) {
            return false;
        }
        return true;
    }
}
