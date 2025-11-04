package com.iot.surfaceview;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Ball {

    private Drawable Image = null;
    private Point point = new Point();
    private Point size = new Point();
    private Point delta = new Point();

    public Drawable getImage() {return Image;}
    public Point getPoint() {return point;}
    public Point getSize() {return size;}
    public Point getDelta() {return delta;}
    public void setImage(Drawable Image) {this.Image = Image;}
    public void setPoint(Point point) {this.point = point;}
    public void setSize(Point size) {this.size = size;}
    public void setDelta(int dx, int dy) {this.delta.x = dx; this.delta.y = dy;}
    public void draw(Canvas canvas){
        Image.setBounds(point.x, point.y, point.x+size.x, point.y+size.y);
        Image.draw(canvas);
    }
    public void move(Rect surfaceFrame){
        if(point.x+delta.x < 0 || point.x + delta.x +size.x > surfaceFrame.right) delta.x *= -1;
        else point.x += delta.x;

        if(point.y + delta.y < 0 || point.y+delta.y + size.y > surfaceFrame.bottom) delta.y *= -1;
        else point.y += delta.y;
    }

}
