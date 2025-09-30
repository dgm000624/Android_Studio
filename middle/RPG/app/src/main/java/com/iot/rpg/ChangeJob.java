package com.iot.rpg;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class ChangeJob {

    getReward end;
    private int cnt;
    private ProgressBar progressBar;
    private Button button;
    private Adventurer adventurer;
    private Thread thread1;
    private volatile boolean running;

    public ChangeJob(getReward act, ProgressBar progressBar, Button button, Adventurer adventurer) {
        this.progressBar = progressBar;
        this.button = button;
        this.adventurer = adventurer;
        this.end = act;

        toEnable(button, progressBar);

        running = true;

        thread1 = new Thread() {
            @Override
            public void run() {
                while (running) {
                    try {
                        cnt++;
                        if (cnt > 100) cnt = 0;
                        Thread.sleep(10);
                        progressBar.post(() -> progressBar.setProgress(cnt));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread1.start();

        button.setOnClickListener(v -> {
            running = false;
            int score = progressBar.getProgress();
            end.applyJob(adventurer, score);
            toDisable(button, progressBar);
        });
    }

    private void toEnable(Button btn, ProgressBar bar) {
        btn.setVisibility(View.VISIBLE);
        bar.setVisibility(View.VISIBLE);
    }

    private void toDisable(Button btn, ProgressBar bar) {
        btn.setVisibility(View.INVISIBLE);
        bar.setVisibility(View.INVISIBLE);
    }
}
