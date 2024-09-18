package com.example.myapplication_master.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication_master.R;

public class MainActivity extends AppCompatActivity {

    private Button stsButton ;
    private Button recordButton;
    private Button setButton ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stsButton = findViewById(R.id.statistics);
        setButton = findViewById(R.id.setting);
        recordButton = findViewById(R.id.allRecord);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        recordButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AllRecordActivity.class);
            startActivity(intent);
        });

        setButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SetActivity.class);
            startActivity(intent);
        });

        stsButton.setOnClickListener(view -> {
            Intent intent = new Intent (MainActivity.this, StsActivity.class);
            startActivity(intent);
        });



    }
}