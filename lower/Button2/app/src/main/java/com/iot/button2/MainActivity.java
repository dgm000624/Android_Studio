package com.iot.button2;

import android.annotation.SuppressLint;
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

    TextView text1;
    Button btnName, btnPhone, btnTemp;
    EditText edit;
    Boolean toggle;

    String name = "도건민";
    String phoneNumber = "010-1212-1212";

    @SuppressLint("MissingInflatedId")
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

        text1 = findViewById(R.id.text1);
        btnName = findViewById(R.id.nameBtn);
        btnPhone = findViewById(R.id.phoneBtn);
        btnTemp = findViewById(R.id.tempBtn);
        edit = findViewById(R.id.editText);


        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText(name);
                toggle = true;
            }
        });

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                text1.setText(phoneNumber);
                toggle = false;
            }
        });

        btnTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = edit.getText().toString();

                if(toggle){
                    name = str;
                    text1.setText(name);
                }
                else{
                    phoneNumber = str;
                    text1.setText(phoneNumber);
                }

                edit.setText("");

            }
        });

    }
}