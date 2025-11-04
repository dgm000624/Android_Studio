package com.iot.rpg;

import android.app.Application;

import java.util.ArrayList;

public class Adventurer extends Application {
    private int maxHP;
    private int currHP;



    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrHP() {
        return currHP;
    }

    public int getATK() {
        return ATK;
    }

    public int getDEF() {
        return DEF;
    }

    private int ATK;
    private int DEF;
    private String job;
    private ArrayList<Equipment> equipList = new ArrayList<>();

    public ArrayList<Equipment> getEquipList() {
        return equipList;
    }
    public static class Equipment{
        private int HP;
        private int ATK;
        private int DEF;
        private String Name;
        public Equipment(int HP, int ATK, int DEF, String name) {
            this.HP = HP;
            this.ATK = ATK;
            this.DEF = DEF;
            Name = name;
        }
        public int getHP() {
            return HP;
        }
        public int getATK() {
            return ATK;
        }
        public int getDEF() {
            return DEF;
        }
        public String getName() {
            return Name;
        }

        public String getEquipinfo()
        {
            return "\t이름 : "+ this.Name + " HP : " + this.HP + " ATK : "+this.ATK+" DEF : "+this.DEF;
        }
    }

    public Adventurer() {
        resetAdventurer();
    }
    public void getEquip(Equipment what){
        this.maxHP += what.HP;
        this.ATK += what.ATK;
        this.DEF += what.DEF;
        equipList.add(what);
    }

    public void resetAdventurer(){
        this.maxHP = 100;
        this.currHP = 100;
        this.ATK = 20;
        this.DEF = 5;
        this.job = "백수";
        this.equipList.clear();
    }

    public String getJob() {
        return job;
    }

    public boolean damaged(int damage){
        this.currHP -= damage;
        return currHP > 0;
    }

    public void healed(int heal)
    {
        this.currHP += heal;
        if(this.currHP > this.maxHP) this.currHP = maxHP;
    }

    public void getJob(String job)
    {
        if(job.equals("기사")){this.maxHP +=50; this.currHP +=50; this.ATK +=10; this.DEF +=5; this.job = "기사";}
        else if(job.equals("방패 기사")){this.maxHP +=70; this.currHP +=70; this.ATK +=4; this.DEF +=10; this.job = "방패 기사";}
        else if(job.equals("버서커")){this.maxHP -=20;
            if(currHP >20)this.currHP -=20; else currHP = 1;
            this.ATK +=50; this.DEF -=3; this.job = "버서커";}
        else {this.maxHP +=20; this.currHP +=20; this.ATK +=3; this.DEF +=2; this.job = "용병";}
    }


}
