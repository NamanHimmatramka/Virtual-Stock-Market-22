package com.example.vsm22;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vsm22.models.Currency;
import com.example.vsm22.models.Stock;
import com.example.vsm22.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TradingFragment extends Fragment {
RecyclerView recyclerView;
FirebaseFirestore db;
private TradingAdapterRV adapter;

    public TradingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_trading, container, false);
        db=FirebaseFirestore.getInstance();
        TextView crypto1 = view.findViewById(R.id.textView);
        TextView crypto2 = view.findViewById(R.id.textView2);
        TextView crypto3 = view.findViewById(R.id.textView3);
        db.collection("crypto").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> ds= queryDocumentSnapshots.getDocuments();
                crypto1.setText(ds.get(0).toObject(Currency.class).getcryptoPriceInRupees()+"");
                crypto2.setText(ds.get(1).toObject(Currency.class).getcryptoPriceInRupees()+"");
                crypto3.setText(ds.get(2).toObject(Currency.class).getcryptoPriceInRupees()+"");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        recyclerView=(RecyclerView)view.findViewById(R.id.RV_trading);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Query query=db.collection("stocks");
        FirestoreRecyclerOptions<Stock> options=new FirestoreRecyclerOptions.Builder<Stock>()
                .setQuery(query,Stock.class)
                .build();
        adapter = new TradingAdapterRV(options);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}