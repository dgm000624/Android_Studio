package com.iot.debug;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        int a = 2;
        int b = 3;
        int c = 5;
        int result = 0;
        result = a << 2;
        result += b;
        result = (result+c) >> 1;

        result = add(result, 3);
        textView.setText(String.valueOf(result));
    }

    int add(int x, int y)
    {
        return x+y;
    }
}