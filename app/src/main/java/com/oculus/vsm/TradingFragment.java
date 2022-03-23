package com.oculus.vsm;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oculus.vsm.models.Stock;
import com.oculus.vsm.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

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
//        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                                                                                                            @Override
//                                                                                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                                                                                                                User user = documentSnapshot.toObject(User.class);
//                                                                                                                                crypto1.setText(user.currencyOwned.get(0)+"");
//                                                                                                                                crypto2.setText(user.currencyOwned.get(1)+"");
//                                                                                                                                crypto3.setText(user.currencyOwned.get(2)+"");
//                                                                                                                            }
//                                                                                                                        }
//        ).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                User user = value.toObject(User.class);
                crypto1.setText(String.format("%.2f",user.currencyOwned.get(0)));
//                crypto2.setText(String.format("%.2f",user.currencyOwned.get(1)));
//                crypto3.setText(String.format("%.2f",user.currencyOwned.get(2)));
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