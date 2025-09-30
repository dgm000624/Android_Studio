package com.iot.rpg;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

// surfaceView에서 구현할 Player객체용

public class Player {
    private Drawable pImage = null;
    private Point pPoint = new Point();
    private Point pSize = new Point();
    private Point pDelta = new Point();

    public void setImage(Drawable image) {
        pImage = image;
    }

    public Point getPoint() {
        return pPoint;
    }

    public void setPoint(Point point) {
        this.pPoint = point;
    }

    public void setSize(Point size) {
        this.pSize = size;
    }

    public void setDelta(int dx, int dy) {
        this.pDelta.x = dx;
        this.pDelta.y = 0;
    }

    public void draw(Canvas canvas){
        pImage.setBounds(pPoint.x, pPoint.y, pPoint.x+pSize.x, pPoint.y+pSize.y);
        pImage.draw(canvas);
    }

    public void move(Rect surfaceFrame) {
        pPoint.x += 2 * pDelta.x;

    }

    public void player_set(Rect surfaceFrame){
        pPoint.x = -290;
    }
}
