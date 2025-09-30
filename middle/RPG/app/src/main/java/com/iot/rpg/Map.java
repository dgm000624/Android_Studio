package com.iot.rpg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import androidx.annotation.NonNull;

//맵 이동 구현용 클래스

public class Map extends SurfaceView implements SurfaceHolder.Callback {

    private int stage;
    private static final String TAG = Map.class.getName();
    private final SurfaceHolder holder;
    private Player player;
    private boolean map;

    Context context;

    public Map(Context context, int stage) {
        super(context);
        this.context = context;
        holder = getHolder();
        holder.addCallback(this);
        this.stage = stage;
    }

//    private final Thread renderer = new Thread(){
//        @Override
//        public void run() {
//            super.run();
//            Drawable drawable = getResources().getDrawable(R.drawable.filed, null);
//            drawable.setBounds(holder.getSurfaceFrame());
//            player.setDelta(10, 0);
//            map = true;
//
//            while (map) {
//                player.move(holder.getSurfaceFrame());
//                Canvas canvas = holder.lockCanvas();
//                drawable.draw(canvas);
//                player.draw(canvas);
//                holder.unlockCanvasAndPost(canvas);
//
//                player.setPoint(new Point((stage%3) * holder.getSurfaceFrame().right/3,
//                        holder.getSurfaceFrame().centerY()/2));
//                if(stage % 3 == 0) {player.setDelta(0,0); map = false;}
//                if(player.getPoint().x > ((stage+1)%3) * holder.getSurfaceFrame().right/3 &&
//                        player.getPoint().x < ((stage+1)%3) * holder.getSurfaceFrame().right/3+3)
//                {
//                    map = false;
//                    if (context instanceof AdventureActivity) {
//                        ((AdventureActivity) context).runOnUiThread(() -> {
//                            ((AdventureActivity) context).switchButtons(true);
//                        });
//                    }
//                }
//
//            }
//            }
//        };
@Override
public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
    player = new Player();
    player.setImage(getResources().getDrawable(R.drawable.adventurer, null));
    player.setSize(new Point(300, 300));
    player.setPoint(new Point(((stage-1) % 3) * holder.getSurfaceFrame().right / 3
            - holder.getSurfaceFrame().right /3, height / 2));



    @SuppressLint("UseCompatLoadingForDrawables") Thread renderer = new Thread(() -> {
        Drawable drawable;
        if(stage>=1 && stage <=3) {
            drawable = getResources().getDrawable(R.drawable.filed, null);
        }
        else if(stage <=6){
            drawable = getResources().getDrawable(R.drawable.forest, null);
        }
        else{
            drawable = getResources().getDrawable(R.drawable.castle, null);
        }
        drawable.setBounds(holder.getSurfaceFrame());
        player.setDelta(10, 0);
        map = true;

        int destX = ((stage-1) % 3) * holder.getSurfaceFrame().right / 3;

        if(stage==10){player.setDelta(0,0); player.setPoint(
                new Point(2 * holder.getSurfaceFrame().right / 3, height / 2));
        }

        while (map) {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                player.move(holder.getSurfaceFrame());
                drawable.draw(canvas);
                player.draw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }

//            if (stage % 3 == 0) {
//                player.setDelta(0, 0);
//            }

            if (Math.abs(player.getPoint().x - destX) <= 10) {
                player.setDelta(0, 0);
                map = false;

                if (context instanceof AdventureActivity) {
                    ((AdventureActivity) context).runOnUiThread(() -> {
                        if(((AdventureActivity) context).getFlag()) {
                            ((AdventureActivity) context).switchButtons(false);
                        }
                    });
                }
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

    }


}


