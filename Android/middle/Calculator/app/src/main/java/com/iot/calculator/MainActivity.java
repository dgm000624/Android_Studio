package com.iot.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button plusBtn;
    Button minusBtn;
    Button multiBtn;
    Button divBtn;
    EditText mainText;
    EditText subText1;
    EditText subText2;
    EditText subText3;
    TextView totalText;

    private double num;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        multiBtn = findViewById(R.id.multiBtn);
        divBtn = findViewById(R.id.divideBtn);

        mainText = findViewById(R.id.main_edit);
        subText1 = findViewById(R.id.sub_edit1);
        subText2 = findViewById(R.id.sub_edit2);
        subText3 = findViewById(R.id.sub_edit3);

        totalText = findViewById(R.id.total_text);


        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    num = 0;
                    num += Integer.parseInt(mainText.getText().toString());
                    num += Integer.parseInt(subText1.getText().toString());
                    num += Integer.parseInt(subText2.getText().toString());
                    num += Integer.parseInt(subText3.getText().toString());

                    mainText.setText("");
                    subText1.setText("");
                    subText2.setText("");
                    subText3.setText("");

                    totalText.setText(String.valueOf(num));
                }catch(NumberFormatException e)
                {
                    Toast.makeText(MainActivity.this,"빈칸 있음",Toast.LENGTH_LONG).show();
                }
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    num = 0;
                    num += Integer.parseInt(mainText.getText().toString());
                    num -= Integer.parseInt(subText1.getText().toString());
                    num -= Integer.parseInt(subText2.getText().toString());
                    num -= Integer.parseInt(subText3.getText().toString());

                    mainText.setText("");
                    subText1.setText("");
                    subText2.setText("");
                    subText3.setText("");

                    totalText.setText(String.valueOf(num));
                }catch(NumberFormatException e)
                {
                    Toast.makeText(MainActivity.this,"빈칸 있음",Toast.LENGTH_LONG).show();
                }
            }
        });

        multiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    num = 1;
                    num *= Integer.parseInt(mainText.getText().toString());
                    num *= Integer.parseInt(subText1.getText().toString());
                    num *= Integer.parseInt(subText2.getText().toString());
                    num *= Integer.parseInt(subText3.getText().toString());

                    mainText.setText("");
                    subText1.setText("");
                    subText2.setText("");
                    subText3.setText("");

                    totalText.setText(String.valueOf(num));
                }catch(NumberFormatException e)
                {
                    Toast.makeText(MainActivity.this,"빈칸 있음",Toast.LENGTH_LONG).show();
                }
            }
        });

        divBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    num = 0;

                    if(subText1.getText().toString().equals("0") || subText2.getText().toString().equals("0") || subText3.getText().toString().equals("0"))
                    {Toast.makeText(MainActivity.this, "0으로 나누기 불가", Toast.LENGTH_LONG).show(); return;}
                    num += Integer.parseInt(mainText.getText().toString());
                    num /= Integer.parseInt(subText1.getText().toString());
                    num /= Integer.parseInt(subText2.getText().toString());
                    num /= Integer.parseInt(subText3.getText().toString());

                    mainText.setText("");
                    subText1.setText("");
                    subText2.setText("");
                    subText3.setText("");

                    totalText.setText(String.valueOf(num));
                }catch(NumberFormatException e)
                {
                    Toast.makeText(MainActivity.this,"빈칸 있음",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}