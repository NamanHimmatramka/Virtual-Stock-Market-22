package com.example.vsm22;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PowercardsFragment extends Fragment {
ImageView loanIV,insiderIV;
TextView insiderTV;
Boolean check_insider=true, check_loan=true;
CardView insiderCV,loanCV;
int roundNo;
    public PowercardsFragment(int roundNo) {
        // Required empty public constructor
        this.roundNo=roundNo;
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

        loanCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loanFunction(view);
            }
        });

        return view;
    }

    private void loanFunction(View view) {
        if(check_loan==true){
            check_loan=false;
            loanIV.setAlpha((float) 0.3);
        }
        else{
            Toast.makeText(view.getContext(),"Insider Trading already used",Toast.LENGTH_SHORT).show();
        }

    }

    private void insiderFunction(View view) {
        if(check_insider==true){
           check_insider=false;
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
        else{
            Toast.makeText(view.getContext(),"Loan already used",Toast.LENGTH_SHORT).show();
        }
    }
}