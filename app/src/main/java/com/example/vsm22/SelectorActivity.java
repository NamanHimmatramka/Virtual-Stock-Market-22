package com.example.vsm22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectorActivity extends AppCompatActivity {

    CardView sponsors;
    CardView mainGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        mainGame = findViewById(R.id.CV_mainGame);
        sponsors = findViewById(R.id.CV_sponsors);

        mainGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectorActivity.this , MainActivity.class);
                startActivity(intent);

            }
        });

        sponsors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectorActivity.this, SponsorsActivity.class);
                startActivity(intent);
            }
        });
    }
}