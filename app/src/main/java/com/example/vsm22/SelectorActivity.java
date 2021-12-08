package com.example.vsm22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vsm22.models.Important;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SelectorActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CardView sponsors;
    CardView mainGame;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        mainGame = findViewById(R.id.CV_mainGame);
        sponsors = findViewById(R.id.CV_sponsors);
        loader = findViewById(R.id.loader);

//        db.collection("registeredUsers").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if(documentSnapshot.exists()){
//                    mainGame.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            db.collection("information").document("values").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                @Override
//                                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                    if(documentSnapshot.exists()){
//                                        Important data = documentSnapshot.toObject(Important.class);
//                                        Intent intent = new Intent(SelectorActivity.this , MainActivity.class);
//                                        intent.putExtra("roundNo", data.roundNo);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                }
//                            });
//                        }
//                    });
//                }
//                else {
//                    Toast.makeText(SelectorActivity.this, "Not Registered",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        mainGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.setVisibility(View.VISIBLE);
                db.collection("registeredUsers").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            db.collection("information").document("values").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        Important data = documentSnapshot.toObject(Important.class);
                                        Intent intent = new Intent(SelectorActivity.this , MainActivity.class);
                                        intent.putExtra("roundNo", data.roundNo);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        }
                        else {
                            loader.setVisibility(View.GONE);
                            Toast.makeText(SelectorActivity.this, "Not Registered",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
//        mainGame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SelectorActivity.this , MainActivity.class);
//                startActivity(intent);
//
//            }
//        });

        sponsors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectorActivity.this, SponsorsActivity.class);
                startActivity(intent);
            }
        });
    }
}