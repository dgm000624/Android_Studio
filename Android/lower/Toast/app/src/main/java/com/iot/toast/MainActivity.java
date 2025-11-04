package com.iot.toast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btn;
    ImageView img;

    private EditText editText;
    private Button button;

    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        img = findViewById(R.id.img);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                editText.setText("");
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });

        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this, "이미지 터치", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        btn = findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "button clicked", Toast.LENGTH_SHORT).show();
            }
        });


    }
}