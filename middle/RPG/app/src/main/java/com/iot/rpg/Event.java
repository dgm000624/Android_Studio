package com.iot.rpg;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;
import java.util.Random;

//전체적인 이벤트를 정의하는 클래스. 회복, 장비획득, 전직 등등
public class Event {

    Random random = new Random();

    //보스전 용으로 만들었던건데 Fight 클래스를 만들었으므로 삭제해도 무방함.
    public boolean bossEvent(Adventurer adventurer)
    {
        int bossHP = 100000;
        int bossATK = 100;
        int bossDEF = 50;
        while(true) {
            bossHP -= ((adventurer.getATK() - bossDEF) > 0 ? adventurer.getATK() - bossDEF:1);
            if (bossHP <= 0) {
                return true;
            }
            else if (!(adventurer.damaged((bossATK - adventurer.getDEF()) > 0 ? bossATK - adventurer.getDEF() : 1))) {
                return false;
            }
        }
    }

    //전투 후 확률 기반 랜덤 장비 획득 이벤트
    public void EquipEvent(Adventurer adventurer){

        getEuqip(adventurer);
    }

    // 랜덤 이벤트 선택시 실행되는 이벤트
    public void RandomEvent(Adventurer adventurer){

        int num;
        num = random.nextInt(100);
        if(num<=30)
        {
            adventurer.damaged(adventurer.getCurrHP()/2);
        }
        else if(num<=55)
        {
            adventurer.healed(adventurer.getMaxHP()/3);
        }
        else if(num<=80)
        {
            getEuqip(adventurer);
        }
        else if(num<=90)
        {
            adventurer.getEquip(new Adventurer.Equipment(-50, -10, -10, "저주받은 칼"));
        }
        else if(num<=98)
        {
            adventurer.getEquip(new Adventurer.Equipment(80,40,30,"이름모를 칼"));
        }
        else if(adventurer.getCurrHP() < adventurer.getMaxHP()/3)
        {
            adventurer.getEquip(new Adventurer.Equipment(999,999,999,"???"));
        }
        else{
            adventurer.getEquip(new Adventurer.Equipment(777,77,77,"행운의 칼"));
        }
    }


    // 유저 체력 회복
    public void adventurerHealed(Adventurer adventurer){
        int num = random.nextInt(adventurer.getMaxHP()/2) + adventurer.getMaxHP()/2;

        adventurer.healed(num);
    }

    // 유저 장비 획득 따로 장비 종류를 설정해두진 않아서 여기서 별도로 선언, 추가함
    private void getEuqip(Adventurer adventurer) {
        int num = random.nextInt(100);

        if (num < 5)
            adventurer.getEquip(new Adventurer.Equipment(120, 40, 20, "전설의 검"));
        else if (num <40) {
            adventurer.getEquip(new Adventurer.Equipment(30, 15, 4, "마법 검"));
        }
        else if(num < 97){
            adventurer.getEquip(new Adventurer.Equipment(10,5,1,"흔한 검"));
        }
        else{
            adventurer.getEquip(new Adventurer.Equipment(300, 99, 50, "월드 에디터"));
        }
    }

    // 전직 이벤트. 직업종류는 Adventurer에서 정의됨
    // 그냥 따로 구현했으니 삭제
//    private void getJob(Adventurer adventurer){
//        int num = random.nextInt(100);
//
//        if (num < 15)
//            adventurer.getJob("방패 기사");
//        else if (num <30) {
//            adventurer.getJob("기사");
//        }
//        else if(num < 98){
//            adventurer.getJob("용병");
//        }
//        else{
//            adventurer.getJob("버서커");
//        }
//    }

}
