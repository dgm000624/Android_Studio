package com.iot.thread;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private int counter = 0;
    private boolean isRunning = true;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        Thread thread = new Thread(new Thread(){
            @Override
            public void run() {
                while (isRunning){
                    try{
                        Thread.sleep(100);      //스레드 1초 정지
                    } catch (InterruptedException e) {
                        e.printStackTrace();;
                    }
                    counter++;
                    Log.i("test", "thread running:"+counter);

                    runOnUiThread(new Runnable() {  //UI업데이트(메인 스레드 실행)
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(counter));
                        }
                    });
                }
            }
        });
        thread.start(); //Thread 시작
    }
    // 액티비티 종료 싱 메모리 누수 방지를 위한 스레드 종료
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }
}