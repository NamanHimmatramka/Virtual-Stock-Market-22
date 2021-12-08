package com.example.vsm22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vsm22.models.Important;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WaitingActivity extends AppCompatActivity {

    Button nextRound;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        nextRound = findViewById(R.id.nextRound);
        db = FirebaseFirestore.getInstance();

        nextRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("information").document("values").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Important data = documentSnapshot.toObject(Important.class);
                            if(data.nextRound == 1){
                                Intent intent = new Intent(WaitingActivity.this , MainActivity.class);
                                intent.putExtra("roundNo", data.roundNo);
                                startActivity(intent);
                                finish();
                            }
                            else
                                Toast.makeText(WaitingActivity.this, "Wait for the next round to start", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}