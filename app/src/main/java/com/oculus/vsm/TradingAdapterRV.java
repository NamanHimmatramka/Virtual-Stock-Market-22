package com.oculus.vsm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.oculus.vsm.models.Currency;
import com.oculus.vsm.models.Stock;
import com.oculus.vsm.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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
        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                User user=value.toObject(User.class);
                holder.owned.setText(user.noOfStocksOwned.get(position)+"");
            }
        });
        db.collection("stocks").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> ds = queryDocumentSnapshots.getDocuments();
                ArrayList<Stock> stocks = new ArrayList<>();
                for(DocumentSnapshot dsa : ds){
                    stocks.add(dsa.toObject(Stock.class));
                }
                holder.buyQuantity.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(!s.toString().isEmpty()) {
                            double tradeValue = Integer.parseInt(s.toString()) * stocks.get(position).getStockPriceInRupees();
                            holder.tradeValueNumber.setText(tradeValue + "");
                        }
                        else if(s.toString().isEmpty())
                            holder.tradeValueNumber.setText("0");
                        else
                            holder.tradeValueNumber.setText("");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        db.collection("crypto").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> currencies = queryDocumentSnapshots.getDocuments();
                Currency currency1 = currencies.get(0).toObject(Currency.class);
//                Currency currency2 = currencies.get(1).toObject(Currency.class);
//                Currency currency3 = currencies.get(2).toObject(Currency.class);
                holder.priceCrypto1.setText(model.getStockPriceInRupees()/currency1.getcryptoPriceInRupees()+"");
//                holder.priceCrypto2.setText(model.getStockPriceInRupees()/currency2.getcryptoPriceInRupees()+"");
//                holder.priceCrypto3.setText(model.getStockPriceInRupees()/currency3.getcryptoPriceInRupees()+"");
                holder.buyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double buyPrice;
                        Currency buyCurrency;
                        buyPrice = model.getStockPriceInRupees()/currency1.getcryptoPriceInRupees();
                        buyCurrency = currency1;
                        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                User user = documentSnapshot.toObject(User.class);
                                if(!holder.buyQuantity.getText().toString().equals("")) {
                                    int temp2 = user.noOfStocksOwned.get(position);
                                    boolean successful = false;
                                    if (buyCurrency == currency1) {
                                        double temp = user.currencyOwned.get(0) - (buyPrice * (Integer.parseInt(holder.buyQuantity.getText().toString())));
                                        if(temp>=0) {
                                            user.currencyOwned.set(0, temp);
                                            successful = true;
                                        }
                                    }
                                    if(successful == true){
                                        user.noOfStocksOwned.set(position, temp2 + Integer.parseInt(holder.buyQuantity.getText().toString()));
                                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                                .setTitle("Confirmation")
                                                .setMessage("Are you sure you want to buy these stocks using "+buyPrice * (Integer.parseInt(holder.buyQuantity.getText().toString())))
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
                                                        Toast.makeText(context, "Transaction Successful", Toast.LENGTH_LONG).show();
                                                        holder.buyQuantity.setText("");
                                                    }
                                                })
                                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Toast.makeText(context, "Transaction Cancelled", Toast.LENGTH_LONG).show();
                                                    }
                                                })
                                                .show();
                                    }
                                    else {
                                        Toast.makeText(context, "You don't have enough " + buyCurrency.getcryptoName() + "s", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                    }
                });

//                holder.buySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    double buyPrice;
//                    Currency buyCurrency;
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
//
//                            buyPrice = model.getStockPriceInRupees()/currency1.getcryptoPriceInRupees();
//                            buyCurrency = currency1;
//
////                        else if(parent.getItemAtPosition(position1).toString().equals("Surreal Coin")){
////                            buyPrice = model.getStockPriceInRupees()/currency2.getcryptoPriceInRupees();
////                            buyCurrency = currency2;
////                        }
////                        else if(parent.getItemAtPosition(position1).toString().equals("Pepe Coin")){
////                            buyPrice = model.getStockPriceInRupees()/currency3.getcryptoPriceInRupees();
////                            buyCurrency = currency3;
////                        }
//                       db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                           @Override
//                           public void onSuccess(DocumentSnapshot documentSnapshot) {
//                               User user = documentSnapshot.toObject(User.class);
//                               if(!holder.buyQuantity.getText().toString().equals("")) {
//                                   int temp2 = user.noOfStocksOwned.get(position);
//                                   boolean successful = false;
//                                   if (buyCurrency == currency1) {
//                                       double temp = user.currencyOwned.get(0) - (buyPrice * (Integer.parseInt(holder.buyQuantity.getText().toString())));
//                                       if(temp>0) {
//                                           user.currencyOwned.set(0, temp);
//                                           successful = true;
//                                       }
//                                   }
////                                   if (buyCurrency == currency2) {
////                                       double temp = user.currencyOwned.get(1) - (buyPrice * (Integer.parseInt(holder.buyQuantity.getText().toString())));
////                                       if(temp > 0) {
////                                           user.currencyOwned.set(1, temp);
////                                           successful = true;
////                                       }
////                                   }
////                                   if (buyCurrency == currency3) {
////                                       double temp = user.currencyOwned.get(2) - (buyPrice * (Integer.parseInt(holder.buyQuantity.getText().toString())));
////                                       if(temp>0){
////                                           user.currencyOwned.set(2, temp);
////                                           successful = true;
////                                       }
////                                   }
//                                   if(successful == true){
//                                   user.noOfStocksOwned.set(position, temp2 + Integer.parseInt(holder.buyQuantity.getText().toString()));
//
//                                   holder.buyButton.setOnClickListener(new View.OnClickListener() {
//                                       @Override
//                                       public void onClick(View v) {
//                                           holder.buySpinner.setSelection(0);
//                                           AlertDialog alertDialog = new AlertDialog.Builder(context)
//                                                   .setTitle("Confirmation")
//                                                   .setMessage("Are you sure you want to buy these stocks using "+buyPrice * (Integer.parseInt(holder.buyQuantity.getText().toString())))
//                                                   .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                                       @Override
//                                                       public void onClick(DialogInterface dialog, int which) {
//                                                           db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
//                                                           Toast.makeText(context, "Transaction Successful", Toast.LENGTH_LONG).show();
//                                                           holder.buyQuantity.setText("");
//                                                       }
//                                                   })
//                                                   .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                                                       @Override
//                                                       public void onClick(DialogInterface dialog, int which) {
//                                                           Toast.makeText(context, "Transaction Cancelled", Toast.LENGTH_LONG).show();
//                                                       }
//                                                   })
//                                                   .show();
//                                       }
//                                   });}
//                                   else {
//                                       holder.buySpinner.setSelection(0);
//                                       Toast.makeText(context, "You don't have enough " + buyCurrency.getcryptoName() + "s", Toast.LENGTH_LONG).show();
//                                   }
//                               }
//                           }
//                       });
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
                holder.sellButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double sellPrice;
                        Currency sellCurrency;
                        sellPrice = model.getStockPriceInRupees()/currency1.getcryptoPriceInRupees();
                        sellCurrency = currency1;

                        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                User user = documentSnapshot.toObject(User.class);
                                if(!holder.buyQuantity.getText().toString().equals("")) {
                                    int temp2 = user.noOfStocksOwned.get(position);
                                    boolean successful = temp2>=Integer.parseInt(holder.buyQuantity.getText().toString());
                                    if (sellCurrency == currency1) {
                                        double temp = user.currencyOwned.get(0) + (sellPrice * (Integer.parseInt(holder.buyQuantity.getText().toString())));
                                        if(successful)
                                            user.currencyOwned.set(0, temp);
                                    }

                                    if(successful){
                                        user.noOfStocksOwned.set(position, temp2 - Integer.parseInt(holder.buyQuantity.getText().toString()));
                                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                                .setTitle("Confirmation")
                                                .setMessage("Are you sure you want to sell these stocks for "+ sellPrice * (Integer.parseInt(holder.buyQuantity.getText().toString())))
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
                                                        Toast.makeText(context, "Transaction Successful", Toast.LENGTH_LONG).show();
                                                        holder.buyQuantity.setText("");
                                                    }
                                                })
                                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Toast.makeText(context, "Transaction Cancelled", Toast.LENGTH_LONG).show();
                                                    }
                                                })
                                                .show();
                                    }
                                    else {
                                        Toast.makeText(context, "You dont have enough " + model.getStockName() , Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                    }
                });
//                holder.sellSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    double sellPrice;
//                    Currency sellCurrency;
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
//
//                            sellPrice = model.getStockPriceInRupees()/currency1.getcryptoPriceInRupees();
//                            sellCurrency = currency1;
//
//                        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                User user = documentSnapshot.toObject(User.class);
//                                if(!holder.sellQuantity.getText().toString().equals("")) {
//                                    int temp2 = user.noOfStocksOwned.get(position);
//                                    boolean successful = temp2>=Integer.parseInt(holder.sellQuantity.getText().toString());
//                                    if (sellCurrency == currency1) {
//                                        double temp = user.currencyOwned.get(0) + (sellPrice * (Integer.parseInt(holder.sellQuantity.getText().toString())));
//                                        if(successful)
//                                            user.currencyOwned.set(0, temp);
//                                    }
//
//                                    if(successful){
//                                    user.noOfStocksOwned.set(position, temp2 - Integer.parseInt(holder.sellQuantity.getText().toString()));
//                                        holder.sellSpinner.setSelection(0);
//                                        AlertDialog alertDialog = new AlertDialog.Builder(context)
//                                                .setTitle("Confirmation")
//                                                .setMessage("Are you sure you want to sell these stocks for "+ sellPrice * (Integer.parseInt(holder.sellQuantity.getText().toString())))
//                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user);
//                                                        Toast.makeText(context, "Transaction Successful", Toast.LENGTH_LONG).show();
//                                                        holder.sellQuantity.setText("");
//                                                    }
//                                                })
//                                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        Toast.makeText(context, "Transaction Cancelled", Toast.LENGTH_LONG).show();
//                                                    }
//                                                })
//                                                .show();
//                                    }
//                                    else {
//                                        holder.sellSpinner.setSelection(0);
//                                        Toast.makeText(context, "You dont have enough " + model.getStockName() , Toast.LENGTH_LONG).show();
//                                    }
//                                }
//                            }
//                        });
//                    }

//                    @Override
//                    public void onNothingSelected(AdapterView<?> parent) {
//
//                    }
//                });
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
    TextView stockName,owned;
    Button buyButton;
    Button sellButton;
    TextView priceCrypto1;
    TextView priceCrypto2;
    TextView priceCrypto3;
//    Spinner buySpinner;
//    Spinner sellSpinner;
    EditText buyQuantity;
    TextView tradeValueNumber;
    TextView tradeValueText;
    public StockViewHolder(View itemView) {
        super(itemView);
        owned=itemView.findViewById(R.id.TV_trade_owned);
        stockName=itemView.findViewById(R.id.TV_stock_name);
        buyButton=itemView.findViewById(R.id.BT_Buy);
        sellButton=itemView.findViewById(R.id.BT_sell);
        priceCrypto1=itemView.findViewById(R.id.TV_stock_price_crypto1_value);
//        priceCrypto2=itemView.findViewById(R.id.TV_stock_price_crypto2_value);
//        priceCrypto3=itemView.findViewById(R.id.TV_stock_price_crypto3_value);
//        buySpinner=itemView.findViewById(R.id.Spin_buy);
//        sellSpinner=itemView.findViewById(R.id.Spin_sell);
        buyQuantity=itemView.findViewById(R.id.ET_Buy);
        tradeValueNumber = itemView.findViewById(R.id.TV_calculated_valu);
        tradeValueText = itemView.findViewById(R.id.tv_trade_value);
    }
}

