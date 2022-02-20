package com.example.vsm22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vsm22.models.Important;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

public class SelectorActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CardView sponsors;
    CardView mainGame,trialGame;
    ProgressBar loader;
    CardView logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

       mainGame = findViewById(R.id.CV_mainGame);
//        trialGame = findViewById(R.id.CV_trialRound);
      logOut=findViewById(R.id.CV_logOut);
        sponsors = findViewById(R.id.CV_sponsors);
        loader = findViewById(R.id.loader);
        loader.setVisibility(View.GONE);


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

//        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//
//                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
//                clearApplicationData();
//
//            }
//        });

        mainGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loader.setVisibility(View.VISIBLE);
                loader.bringToFront();
                db.collection("registeredUsers").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            db.collection("information").document("values").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        Important data = documentSnapshot.toObject(Important.class);
                                        if(data.gameStart == 1) {
                                            Intent intent = new Intent(SelectorActivity.this, MainActivity.class);
                                            intent.putExtra("roundNo", data.roundNo);
                                            startActivity(intent);
                                            loader.setVisibility(View.GONE);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(SelectorActivity.this, "Wait for the Game to start", Toast.LENGTH_LONG).show();
                                            loader.setVisibility(View.GONE);
                                        }
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
                loader.setVisibility(View.VISIBLE);
                loader.bringToFront();
                Intent intent = new Intent(SelectorActivity.this, SponsorsActivity.class);
                startActivity(intent);
                loader.setVisibility(View.GONE);
            }
        });

//        trialGame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loader.setVisibility(View.VISIBLE);
//                loader.bringToFront();
//
//                // loader.setVisibility(View.GONE);
//            }
//        });


    }
//    public static void deleteCache(Context context) {
//        try {
//            File dir = context.getCacheDir();
//            deleteDir(dir);
//        } catch (Exception e) { e.printStackTrace();}
//    }
//
//    public static boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//            return dir.delete();
//        } else if(dir!= null && dir.isFile()) {
//            return dir.delete();
//        } else {
//            return false;
//        }
//    }

//    public void clearApplicationData() {
//        File cache = getCacheDir();
//        File appDir = new File(cache.getParent());
//        if (appDir.exists()) {
//            String[] children = appDir.list();
//            for (String s : children) {
//                if (!s.equals("lib")) {
//                    deleteDir(new File(appDir, s));
//                    Log.i("EEEEEERRRRRROOOOOOORRRR", "****** File /data/data/APP_PACKAGE/" + s + " DELETED *******");
//                }
//            }
//        }
//    }
//
//    public static boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            String[] children = dir.list();
//            int i = 0;
//            while (i < children.length) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//                i++;
//            }
//        }
//
//        assert dir != null;
//        return dir.delete();
//    }

}