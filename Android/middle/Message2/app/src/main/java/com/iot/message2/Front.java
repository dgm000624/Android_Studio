package com.iot.message2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Front extends AppCompatActivity {

    EditText editText;
    Button btn;

    private ActivityResultLauncher<Intent> launcher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_front);

        editText = findViewById(R.id.editText1);
        editText.setText(getIntent().getStringExtra("str2"));

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if(o.getResultCode()== RESULT_OK){
                    Intent data = o.getData();
                    if(data != null){
                        String resultData = data.getStringExtra("subMsg");
                        editText.setText(resultData);
                    }
                }
            }
        });

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Front.this, Back.class);
                intent.putExtra("str", editText.getText().toString());
                launcher.launch(intent);
            }
        });
    }
}