package com.example.vsm22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        bottomAppBar = findViewById(R.id.app_bar);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new PortfolioFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.portfolio: getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new PortfolioFragment()).commit();
                    return true;

                    case R.id.news: getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new NewsFragment()).commit();
                    return true;

                    case R.id.tradingCenter: getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new TradingFragment()).commit();
                    return true;

                    case R.id.powercards: getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new PowercardsFragment()).commit();
                    return true;

                }
                return false;
            }
        });
    }
}