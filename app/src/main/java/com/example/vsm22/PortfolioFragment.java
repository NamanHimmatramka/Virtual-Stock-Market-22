package com.example.vsm22;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vsm22.models.Currency;
import com.example.vsm22.models.Stock;
import com.example.vsm22.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.DecimalFormat;

public class PortfolioFragment extends Fragment {
RecyclerView recyclerView,recyclerView_crypto;
private FirebaseFirestore firebaseFirestore,firebaseFirestore2;
private FirestoreRecyclerAdapter adapter,adapter_crypto;
    public PortfolioFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_portfolio, container, false);
        RecyclerViewStock(view);
        RecyclerViewCurrency(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

    }

    private class StockViewHolder extends RecyclerView.ViewHolder {
        private TextView stockName,stockPriceInRupees,stockNetWorth,stockOwned;
        public StockViewHolder(View itemView) {
            super(itemView);
            stockName=itemView.findViewById(R.id.TV_stockName);
            stockPriceInRupees=itemView.findViewById(R.id.TV_stock_worth_number);
            stockNetWorth=itemView.findViewById(R.id.TV_net_Stock_worth);
            stockOwned=itemView.findViewById(R.id.TV_stock_number);
        }
    }

    private class CryptoViewHolder extends RecyclerView.ViewHolder {
        private TextView cryptoName,cryptoPriceInRupees,cryptoNetWorth, cryptoOwned;
        private ConstraintLayout constraintLayout;
        private ImageView cyptoImage;
        public CryptoViewHolder(View itemView) {
            super(itemView);
            cryptoName=itemView.findViewById(R.id.TV_crypto_name);
            cryptoPriceInRupees=itemView.findViewById(R.id.TV_crypto_worth_number);
            cryptoNetWorth=itemView.findViewById(R.id.TV_crypto_worth_number2);
            cryptoOwned = itemView.findViewById(R.id.TV_crypto_number);
            constraintLayout=itemView.findViewById(R.id.CV_cryptoItem);
            cyptoImage=itemView.findViewById(R.id.IV_crypto);
        }}

    void RecyclerViewStock(View view){
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView=(RecyclerView)view.findViewById(R.id.RV_stocks_owned);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Query query=firebaseFirestore.collection("stocks");
        FirestoreRecyclerOptions<Stock> options=new FirestoreRecyclerOptions.Builder<Stock>()
                .setQuery(query,Stock.class)
                .build();

        adapter= new FirestoreRecyclerAdapter<Stock, StockViewHolder>(options) {

            @Override
            public StockViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_layout,parent,false);
                return new StockViewHolder(v);
            }

            @Override
            protected void onBindViewHolder( PortfolioFragment.StockViewHolder holder, int position, Stock model) {
                holder.stockName.setText(model.getStockName());
                holder.stockPriceInRupees.setText(model.getStockPriceInRupees()+"");

                firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            User user=documentSnapshot.toObject(User.class);
                            holder.stockOwned.setText(user.noOfStocksOwned.get(position)+"");
                            holder.stockNetWorth.setText(user.noOfStocksOwned.get(position)*model.getStockPriceInRupees()+"");
                        }
                    }
                });


            }
        };

        recyclerView.setAdapter(adapter);
    }

    void RecyclerViewCurrency(View view){
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView=(RecyclerView)view.findViewById(R.id.RV_wallet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        Query query=firebaseFirestore.collection("crypto");
        FirestoreRecyclerOptions<Currency> options=new FirestoreRecyclerOptions.Builder<Currency>()
                .setQuery(query,Currency.class)
                .build();

        adapter_crypto= new FirestoreRecyclerAdapter<Currency,CryptoViewHolder>(options) {

            @Override
            protected void onBindViewHolder( PortfolioFragment.CryptoViewHolder holder, int position,Currency model) {
                holder.cryptoName.setText(model.getcryptoName());
                double s= model.getcryptoPriceInRupees();

                holder.cryptoPriceInRupees.setText(String.format("%.2f",s));
                firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            User user=documentSnapshot.toObject(User.class);
                            holder.cryptoOwned.setText(user.currencyOwned.get(position)+"");
                           // holder.cryptoNetWorth.setText(user.currencyOwned.get(position)*model.getcryptoPriceInRupees()+"");
                           double temp=user.currencyOwned.get(position)*model.getcryptoPriceInRupees();
                            holder.cryptoNetWorth.setText(String.format("%.2f",temp));
                        }
                    }
                });
              switch (position){
                  case 0: holder.cyptoImage.setImageResource(R.drawable.bitcoin);
                  break;
                  case 1: holder.cyptoImage.setImageResource(R.drawable.dogecoin);
                      break;
                  case 2: holder.cyptoImage.setImageResource(R.drawable.eth);
                      break;
              }
                AnimationDrawable animationDrawable=(AnimationDrawable) holder.constraintLayout.getBackground();
                animationDrawable.setEnterFadeDuration(1000);
                animationDrawable.setExitFadeDuration(2000);
                animationDrawable.start();
            }

            @Override
            public CryptoViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.crypto_layout,parent,false);
                return new CryptoViewHolder(v);
            }

        };

        // recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter_crypto);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        adapter_crypto.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
        adapter_crypto.stopListening();
    }
}