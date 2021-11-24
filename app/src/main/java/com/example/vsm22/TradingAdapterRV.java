package com.example.vsm22;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsm22.models.Currency;
import com.example.vsm22.models.Stock;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TradingAdapterRV extends FirestoreRecyclerAdapter<Stock, StockViewHolder> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public TradingAdapterRV(@NonNull FirestoreRecyclerOptions<Stock> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull StockViewHolder holder, int position, @NonNull Stock model) {
        holder.stockName.setText(model.getStockName());
        db.collection("crypto").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> currencies = queryDocumentSnapshots.getDocuments();
                Currency currency1 = currencies.get(0).toObject(Currency.class);
                Currency currency2 = currencies.get(1).toObject(Currency.class);
                Currency currency3 = currencies.get(2).toObject(Currency.class);
                holder.priceCrypto1.setText(model.getStockPriceInRupees()/currency1.getcryptoPriceInRupees()+"");
                holder.priceCrypto2.setText(model.getStockPriceInRupees()/currency2.getcryptoPriceInRupees()+"");
                holder.priceCrypto3.setText(model.getStockPriceInRupees()/currency3.getcryptoPriceInRupees()+"");
            }
        });
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.trading_layout_item,parent,false);
        return new StockViewHolder(v);
    }
}

class StockViewHolder extends RecyclerView.ViewHolder {
    TextView stockName;
    Button buyButton;
    Button sellButton;
    TextView priceCrypto1;
    TextView priceCrypto2;
    TextView priceCrypto3;
    Spinner buySpinner;
    Spinner sellSpinner;
    public StockViewHolder(View itemView) {
        super(itemView);
        stockName=itemView.findViewById(R.id.Stock_name);
        buyButton=itemView.findViewById(R.id.BT_Buy);
        sellButton=itemView.findViewById(R.id.BT_sell);
        priceCrypto1=itemView.findViewById(R.id.TV_stock_price_crypto1_value);
        priceCrypto2=itemView.findViewById(R.id.TV_stock_price_crypto2_value);
        priceCrypto3=itemView.findViewById(R.id.TV_stock_price_crypto3_value);
        buySpinner=itemView.findViewById(R.id.Spin_buy);
        sellSpinner=itemView.findViewById(R.id.Spin_sell);
    }
}

