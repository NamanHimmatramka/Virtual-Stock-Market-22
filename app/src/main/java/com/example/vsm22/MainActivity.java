package com.example.vsm22;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.vsm22.models.Important;
import com.example.vsm22.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    BottomNavigationView bottomNavigationView;
    TextView timer;
    int roundNo;
    FirebaseFirestore db;
    int[] insiderTradingThisRound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        bottomAppBar = findViewById(R.id.app_bar);
        bottomNavigationView = findViewById(R.id.bottom_nav_view);
        timer = findViewById(R.id.timer);
        roundNo = getIntent().getExtras().getInt("roundNo");
        db = FirebaseFirestore.getInstance();
        insiderTradingThisRound = new int[1];

        getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new PortfolioFragment(roundNo)).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.portfolio: getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new PortfolioFragment(roundNo)).commit();
                    return true;

                    case R.id.news: getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new NewsFragment(roundNo)).commit();
                    return true;

                    case R.id.tradingCenter: getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new TradingFragment()).commit();
                    return true;

                    case R.id.powercards: getSupportFragmentManager().beginTransaction().replace(R.id.FL_main, new PowercardsFragment(roundNo, insiderTradingThisRound)).commit();
                    return true;

                }
                return false;
            }
        });

        long duration = TimeUnit.MINUTES.toMillis(5);
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
               db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            User user = documentSnapshot.toObject(User.class);
                            user.status = "waiting";
                            db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
                            Intent intent = new Intent(MainActivity.this, WaitingActivity2.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }.start();

        db.collection("information").document("values").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
             if(value.exists()){
                 Important important=value.toObject(Important.class);
                 if(important.isRoundActive==0){
                     startActivity(new Intent(getApplicationContext(),WaitingActivity2.class));
                 }
             }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }


}