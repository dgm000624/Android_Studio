package com.iot.rpg;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

//surfaceView에서 구현할 몬스터용
public class Monster {

    private Drawable mImage = null;
    private Point mPoint = new Point();
    private Point mSize = new Point();
    private Point mDelta = new Point();

    private int ATK;
    private int DEF;
    private int HP;

    public Monster(int ATK, int DEF, int HP) {
        this.ATK = ATK;
        this.DEF = DEF;
        this.HP = HP;
    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public Drawable getImage() {
        return mImage;
    }

    public void setImage(Drawable image) {
        mImage = image;
    }

    public Point getPoint() {
        return mPoint;
    }

    public void setPoint(Point point) {
        this.mPoint = point;
    }

    public Point getSize() {
        return mSize;
    }

    public void setSize(Point size) {
        this.mSize = size;
    }

    public Point getDelta() {
        return mDelta;
    }

    public void setDelta(int dx, int dy) {
        this.mDelta.x = dx;
        this.mDelta.y = 0;
    }

    public void draw(Canvas canvas){
        mImage.setBounds(mPoint.x, mPoint.y, mPoint.x+mSize.x, mPoint.y+mSize.y);
        mImage.draw(canvas);
    }

    public void move(Rect surfaceFrame){
        mPoint.x += 2* mDelta.x;
    }

    public void monster_set(Rect surfaceFrame){
        mPoint.x = surfaceFrame.right-10;
    }

}
