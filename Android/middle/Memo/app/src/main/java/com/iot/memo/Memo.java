package com.iot.memo;

import android.app.Application;

import java.util.ArrayList;

public class Memo extends Application {

    ArrayList<Memo> memos = new ArrayList<>();

    public Memo() {
    }

    private String title;
    private String memo;

    public Memo(String title, String memo) {
        this.title = title;
        this.memo = memo;
    }

    public void AddMemo(Memo memo){
        memos.add(new Memo(memo.title, memo.memo));
    }

    public Memo IndexMemo(int index){
        return memos.get(index);
    }

    public void DeleteMemo(int index){
        memos.remove(index);
    }

    public int getIndex(){
        return memos.size();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
