package com.iot.edittext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button, button2;
    private EditText editText, editText2;
    Button btn;
    @SuppressLint({"MissingInflatedId", "CutPasteId"})
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

        textView = findViewById(R.id.view1);
        editText = findViewById(R.id.edit1);
        editText2 = findViewById(R.id.view2);
        button2 = findViewById(R.id.btn2);
        button = findViewById(R.id.btn1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                editText2.setText(str);
                editText.setText(" ");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText2.getText().toString();
                editText.setText(str);
                editText2.setText(" ");
            }
        });


        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent myIntent = new Intent(MainActivity.this, MainActivity2.class);
//                startActivity(myIntent);

                String str1 = editText.getText().toString();
                String str2 = editText2.getText().toString();

                editText.setText(str2);
                editText2.setText(str1);
            }
        });
    }
}