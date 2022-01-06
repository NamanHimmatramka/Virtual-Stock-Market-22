package com.example.vsm22;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vsm22.models.Currency;
import com.example.vsm22.models.Stock;
import com.example.vsm22.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TradingAdapterRV extends FirestoreRecyclerAdapter<Stock, StockViewHolder> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context context;
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
                holder.buySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    double buyPrice;
                    Currency buyCurrency;
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                        if(parent.getItemAtPosition(position1).toString().equals("CryptoOne")){
                            buyPrice = model.getStockPriceInRupees()/currency1.getcryptoPriceInRupees();
                            buyCurrency = currency1;
                        }
                        else if(parent.getItemAtPosition(position1).toString().equals("CryptoTwo")){
                            buyPrice = model.getStockPriceInRupees()/currency1.getcryptoPriceInRupees();
                            buyCurrency = currency2;
                        }
                        else if(parent.getItemAtPosition(position1).toString().equals("CryptoThree")){
                            buyPrice = model.getStockPriceInRupees()/currency3.getcryptoPriceInRupees();
                            buyCurrency = currency3;
                        }
                       db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                           @Override
                           public void onSuccess(DocumentSnapshot documentSnapshot) {
                               User user = documentSnapshot.toObject(User.class);
                               if(!holder.buyQuantity.getText().toString().equals("")) {
                                   if (buyCurrency == currency1) {
                                       double temp = user.currencyOwned.get(0) - (buyPrice * (Integer.parseInt(holder.buyQuantity.getText().toString())));
                                       user.currencyOwned.set(0, temp);
                                       int temp2 = user.noOfStocksOwned.get(position);
                                       user.noOfStocksOwned.set(position, temp2 + Integer.parseInt(holder.buyQuantity.getText().toString()));
                                   }
                                   if (buyCurrency == currency2) {
                                       double temp = user.currencyOwned.get(1) - (buyPrice * (Integer.parseInt(holder.buyQuantity.getText().toString())));
                                       user.currencyOwned.set(1, temp);
                                       int temp2 = user.noOfStocksOwned.get(position);
                                       user.noOfStocksOwned.set(position, temp2 + Integer.parseInt(holder.buyQuantity.getText().toString()));
                                   }
                                   if (buyCurrency == currency3) {
                                       double temp = user.currencyOwned.get(2) - (buyPrice * (Integer.parseInt(holder.buyQuantity.getText().toString())));
                                       user.currencyOwned.set(2, temp);
                                       int temp2 = user.noOfStocksOwned.get(position);
                                       user.noOfStocksOwned.set(position, temp2 + Integer.parseInt(holder.buyQuantity.getText().toString()));
                                   }
                               }
                               holder.buyButton.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
                                   }
                               });
                           }
                       });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                holder.sellSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    double sellPrice;
                    Currency sellCurrency;
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                        if(parent.getItemAtPosition(position1).toString().equals("CryptoOne")){
                            sellPrice = model.getStockPriceInRupees()/currency1.getcryptoPriceInRupees();
                            sellCurrency = currency1;
                        }
                        else if(parent.getItemAtPosition(position1).toString().equals("CryptoTwo")){
                            sellPrice = model.getStockPriceInRupees()/currency1.getcryptoPriceInRupees();
                            sellCurrency = currency2;
                        }
                        else if(parent.getItemAtPosition(position1).toString().equals("CryptoThree")){
                            sellPrice = model.getStockPriceInRupees()/currency3.getcryptoPriceInRupees();
                            sellCurrency = currency3;
                        }
                        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                User user = documentSnapshot.toObject(User.class);
                                if(!holder.sellQuantity.getText().toString().equals("")) {
                                    if (sellCurrency == currency1) {
                                        double temp = user.currencyOwned.get(0) + (sellPrice * (Integer.parseInt(holder.sellQuantity.getText().toString())));
                                        user.currencyOwned.set(0, temp);
                                        int temp2 = user.noOfStocksOwned.get(position);
                                        user.noOfStocksOwned.set(position, temp2 - Integer.parseInt(holder.sellQuantity.getText().toString()));
                                    }
                                    if (sellCurrency == currency2) {
                                        double temp = user.currencyOwned.get(1) + (sellPrice * (Integer.parseInt(holder.sellQuantity.getText().toString())));
                                        user.currencyOwned.set(1, temp);
                                        int temp2 = user.noOfStocksOwned.get(position);
                                        user.noOfStocksOwned.set(position, temp2 - Integer.parseInt(holder.sellQuantity.getText().toString()));
                                    }
                                    if (sellCurrency == currency3) {
                                        double temp = user.currencyOwned.get(2) + (sellPrice * (Integer.parseInt(holder.sellQuantity.getText().toString())));
                                        user.currencyOwned.set(2, temp);
                                        int temp2 = user.noOfStocksOwned.get(position);
                                        user.noOfStocksOwned.set(position, temp2 - Integer.parseInt(holder.sellQuantity.getText().toString()));
                                    }
                                }
                                holder.sellButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.trading_layout_item,parent,false);
        context = parent.getContext();
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
    EditText buyQuantity;
    EditText sellQuantity;
    public StockViewHolder(View itemView) {
        super(itemView);
        stockName=itemView.findViewById(R.id.TV_stock_name);
        buyButton=itemView.findViewById(R.id.BT_Buy);
        sellButton=itemView.findViewById(R.id.BT_sell);
        priceCrypto1=itemView.findViewById(R.id.TV_stock_price_crypto1_value);
        priceCrypto2=itemView.findViewById(R.id.TV_stock_price_crypto2_value);
        priceCrypto3=itemView.findViewById(R.id.TV_stock_price_crypto3_value);
        buySpinner=itemView.findViewById(R.id.Spin_buy);
        sellSpinner=itemView.findViewById(R.id.Spin_sell);
        buyQuantity=itemView.findViewById(R.id.ET_Buy);
        sellQuantity=itemView.findViewById(R.id.ET_Sell);
    }
}

