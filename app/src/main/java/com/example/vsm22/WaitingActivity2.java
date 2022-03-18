package com.example.vsm22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vsm22.models.Important;
import com.example.vsm22.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WaitingActivity2 extends AppCompatActivity {
private Button leaderBoard;
private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_waiting2);
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if(user.loanACap == 2){
                    double old = user.currencyOwned.get(0);
                    double newValue = old - 0.5*user.loanBaseAmount;
                    user.currencyOwned.set(0, newValue);
                    user.loanACap=0;
                    db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        leaderBoard=findViewById(R.id.Btn_showLeaderboard);
        leaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("information").document("values").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Important important=documentSnapshot.toObject(Important.class);
                            if(important.leaderboard==1){
                                startActivity(new Intent(getApplicationContext(),WaitingActivity.class));
                            }
                            else{
                                Toast.makeText(WaitingActivity2.this, "Wait till we set up the leaderboard", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

    }
}