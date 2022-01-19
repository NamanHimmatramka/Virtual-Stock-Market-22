package com.example.vsm22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    TextView timer;
    int roundNo;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomAppBar = findViewById(R.id.app_bar);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        timer = findViewById(R.id.timer);
        roundNo = getIntent().getExtras().getInt("roundNo");
        db = FirebaseFirestore.getInstance();

        getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new PortfolioFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.portfolio: getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new PortfolioFragment()).commit();
                    return true;

                    case R.id.news: getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new NewsFragment(roundNo)).commit();
                    return true;

                    case R.id.tradingCenter: getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new TradingFragment()).commit();
                    return true;

                    case R.id.powercards: getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new PowercardsFragment()).commit();
                    return true;

                }
                return false;
            }
        });

        long duration = TimeUnit.MINUTES.toMillis(1);
        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String sDuration = String.format(Locale.ENGLISH, "%02d : %02d"
                        , TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                        , TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                timer.setText(sDuration);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, WaitingActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
}