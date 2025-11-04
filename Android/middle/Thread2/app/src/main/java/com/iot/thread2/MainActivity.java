package com.iot.thread2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Semaphore;

public class MainActivity extends AppCompatActivity {

    private int count = 0;
    private int count2 = 0;
    private int cnt1 = 0;
    private int cnt2 = 0;

    private Semaphore semaphore = new Semaphore(1);
    private final boolean running = true;

    TextView textView;
    TextView textView2;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        Thread thread1 = new Thread(){
            @Override
            public void run() {
                super.run();
                while (running) {
                    try {
                        semaphore.acquire();
                        count++;//키 획득
                        sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();
                    }
                    cnt1++;

                    if(cnt1 == 10000) break;

                    Log.i("test", "plus" + count);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(count));
                        }
                    });
                }
            }
        };

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                super.run();
                while(running) {
                    try {
                        semaphore.acquire();
                        count2--;
                        sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();
                    }


                    cnt2++;
                    if(cnt2 == 10000) break;

                    Log.i("test", "minus" + count2);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView2.setText(String.valueOf(count2));
                        }

                    });
                }
            }
        };

        thread1.start();
        thread2.start();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}