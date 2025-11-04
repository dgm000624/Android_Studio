package com.iot.surfaceview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.jetbrains.annotations.NotNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = GameView.class.getName();
    private final SurfaceHolder holder;
    private Ball ball;
    private boolean goOnPlay = true;
    private final Thread renderer = new Thread(){   // ui스레드와 별개인 스레드에서 그래픽작업을 한다
        @Override
        public void run() {
            super.run();
            Drawable drawable = getResources().getDrawable(R.drawable.tomnjerry, null); // 리소스에서 Drawable 가져오기
            drawable.setBounds(holder.getSurfaceFrame());                                     // Drawable이 그려질 영약 지정(top, left) (bottom, right)
            ball.setDelta(15,30);

            while (goOnPlay){
                ball.move(holder.getSurfaceFrame());
                Canvas canvas = holder.lockCanvas();        // 그림을 그릴 빈 캠버스를 영어와 잠근다
                drawable.draw(canvas);                      // 캔버스에 Drawable 객체를 그린다
                ball.draw(canvas);
                holder.unlockCanvasAndPost(canvas);         //unlock이 되면 화면이 출력된다.

            }
        }
    };

    public GameView(Context context) {
        super(context);
        Log.i(TAG, "GameView created");
        holder = getHolder();                   // holder객체 생성, surface가 holder를 가지고 있기 때문에 getHolder();
        holder.addCallback(this);               // 메인스레드가 표면의 변화를 감지하여 스레드에게 그리기를 추가한다
    }

    @Override
    public void surfaceCreated(@NotNull SurfaceHolder holder) {
        renderer.start();
        ball = new Ball();
        ball.setImage(getResources().getDrawable(R.drawable.redball,null));
        ball.setSize(new Point(100, 100));
        ball.setPoint(new Point(0,0));
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {   //표면의 색상이나 포맷이 변경되었을때 호출

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {        //삭제시 호출

    }

}
