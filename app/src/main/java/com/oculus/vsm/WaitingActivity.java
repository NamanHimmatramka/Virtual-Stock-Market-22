package com.oculus.vsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.oculus.vsm.models.Important;
import com.oculus.vsm.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WaitingActivity extends AppCompatActivity {

    Button nextRound;
    FirebaseFirestore db;
    RecyclerView leaderboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        nextRound = findViewById(R.id.nextRound);
        db = FirebaseFirestore.getInstance();
        leaderboard = findViewById(R.id.leaderboardRV);
        leaderboard.setLayoutManager(new LinearLayoutManager(this));
        db.collection("users").orderBy("netWorth", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> usersDs = queryDocumentSnapshots.getDocuments();
                    ArrayList<User> users = new ArrayList<>();
                    for(DocumentSnapshot ds : usersDs){
                        users.add(ds.toObject(User.class));
                    }
                    LeaderboardRVAdapter adapter = new LeaderboardRVAdapter(users);
                    leaderboard.setAdapter(adapter);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        nextRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("information").document("values").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Important data = documentSnapshot.toObject(Important.class);
                            if(data.nextRound == 1){
                                db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        if(documentSnapshot.exists()){
                                            User user = documentSnapshot.toObject(User.class);
                                            user.status = "";
                                            db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
                                            Intent intent = new Intent(WaitingActivity.this , MainActivity.class);
                                            intent.putExtra("roundNo", data.roundNo);
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
                            else if(data.nextRound==2){
                                startActivity(new Intent(WaitingActivity.this, EndActivity.class));
                            }
                            else
                                Toast.makeText(WaitingActivity.this, "Wait for the next round to start", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}