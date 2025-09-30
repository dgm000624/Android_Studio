package com.iot.rpg;

import android.view.accessibility.AccessibilityRequestPreparer;

public interface getReward {

    public abstract void applyJob(Adventurer adventurer ,int score);
    public abstract Adventurer afterFight(Adventurer adventurer);

}

