package com.example.vsm22;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vsm22.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PowercardsFragment extends Fragment {
ImageView loanIV,insiderIV;
TextView insiderTV;
CardView insiderCV,loanCV;
FirebaseFirestore db;
int[] insiderTradingThisRound;
int[] loanACapThisRound;
int roundNo;
    public PowercardsFragment(int roundNo, int[] insiderTradingThisRound, int[] loanACapThisRound) {
        // Required empty public constructor
        this.roundNo=roundNo;
        this.insiderTradingThisRound = insiderTradingThisRound;
        this.loanACapThisRound = loanACapThisRound;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_powercards, container, false);
        db = FirebaseFirestore.getInstance();
        insiderIV=view.findViewById(R.id.IV_insider);
        insiderTV=view.findViewById(R.id.TV_insider);
        loanIV=view.findViewById(R.id.IV_loan);
        insiderCV=view.findViewById(R.id.CV_insider);
        loanCV=view.findViewById(R.id.CV_LOAN);
        insiderCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insiderFunction(view);
            }
        });
        if(insiderTradingThisRound[0] == 1){
            insiderIV.setAlpha((float) 0.3);
            insiderTV.setVisibility(View.VISIBLE);
            switch (roundNo) {
                case 1:
                    insiderTV.setText("Insider news: " + roundNo);
                    break;
                case 2:
                    insiderTV.setText("Insider news: " + roundNo);
                    break;
                case 3:
                    insiderTV.setText("Insider news: " + roundNo);
                    break;
                case 4:
                    insiderTV.setText("Insider news: " + roundNo);
                    break;
                case 5:
                    insiderTV.setText("Insider news: " + roundNo);
                    break;
            }
        }
        loanCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loanFunction(view);
            }
        });
        if(loanACapThisRound[0] == 1) {
            loanIV.setAlpha((float) 0.3);
        }
        return view;
    }

    private void loanFunction(View view) {

        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if(user.loanACap!=1){
                    Toast.makeText(view.getContext(),"Loan-A-Cap already used",Toast.LENGTH_SHORT).show();
                }
                else{
                    AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                            .setTitle("Confirmation")
                            .setMessage("Are you sure you want to use Loan-A-Cap")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    loanACapThisRound[0] = 1;
                                    loanIV.setAlpha((float) 0.3);
                                    double old = user.currencyOwned.get(0);
                                    double newValue = old + 0.4*old;
                                    user.loanBaseAmount=old;
                                    user.currencyOwned.set(0,newValue);
                                    user.loanACap = 2;
                                    Toast.makeText(view.getContext(), "You got a loan of "+(newValue-old), Toast.LENGTH_LONG).show();
                                    db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
                                }
                            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(view.getContext(), "Powercard not used", Toast.LENGTH_LONG).show();
                                }
                            }).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
//        if(check_loan==true){
//            check_loan=false;
//            loanIV.setAlpha((float) 0.3);
//        }
//        else{
//            Toast.makeText(view.getContext(),"Insider Trading already used",Toast.LENGTH_SHORT).show();
//        }

    }

    private void insiderFunction(View view) {
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    User user = documentSnapshot.toObject(User.class);
                    if(user.insiderTrading == 0)
                        Toast.makeText(view.getContext(),"Insider Trading already used",Toast.LENGTH_SHORT).show();
                    else{
                        insiderTradingThisRound[0] = 1;
                        AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                                .setTitle("Confirmation")
                                .setMessage("Are you sure you want to use Insider Trading")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        insiderIV.setAlpha((float) 0.3);
                                        insiderTV.setVisibility(View.VISIBLE);
                                        switch (roundNo) {
                                            case 1:
                                                insiderTV.setText("Insider news: " + roundNo);
                                                break;
                                            case 2:
                                                insiderTV.setText("Insider news: " + roundNo);
                                                break;
                                            case 3:
                                                insiderTV.setText("Insider news: " + roundNo);
                                                break;
                                            case 4:
                                                insiderTV.setText("Insider news: " + roundNo);
                                                break;
                                            case 5:
                                                insiderTV.setText("Insider news: " + roundNo);
                                                break;
                                        }
                                        user.insiderTrading = 0;
                                        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
                                    }
                                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(view.getContext(), "Powercard not used", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .show();

                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }
}