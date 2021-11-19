package com.example.vsm22;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vsm22.models.Stock;
import com.example.vsm22.models.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TradingFragment extends Fragment {
RecyclerView recyclerView;
FirebaseFirestore firebaseFirestore;
private FirestoreRecyclerAdapter adapter;

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
        RecyclerViewStock(view);
        adapter.startListening();
        return view;
    }

    private class StockViewHolder extends RecyclerView.ViewHolder {
        private TextView stockName;
        public StockViewHolder(View itemView) {
            super(itemView);
            stockName=itemView.findViewById(R.id.Stock_name);
//            stockPriceInRupees=itemView.findViewById(R.id.TV_stock_worth_number);
//            stockNetWorth=itemView.findViewById(R.id.TV_stock_worth_number2);
//            stockOwned=itemView.findViewById(R.id.TV_stock_number);
        }
    }
    void RecyclerViewStock(View view){
        firebaseFirestore=FirebaseFirestore.getInstance();
        recyclerView=(RecyclerView)view.findViewById(R.id.RV_trading);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Query query=firebaseFirestore.collection("stocks");
        FirestoreRecyclerOptions<Stock> options=new FirestoreRecyclerOptions.Builder<Stock>()
                .setQuery(query,Stock.class)
                .build();

        adapter= new FirestoreRecyclerAdapter<Stock, TradingFragment.StockViewHolder>(options) {

            @Override
            public TradingFragment.StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.trading_layout_item,parent,false);
                return new TradingFragment.StockViewHolder(v);
            }

            @Override
            protected void onBindViewHolder( TradingFragment.StockViewHolder holder, int position, Stock model) {
                holder.stockName.setText(model.getStockName());
//                holder.stockPriceInRupees.setText(model.getStockPriceInRupees()+"");
//                firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if(documentSnapshot.exists()){
//                            User user=documentSnapshot.toObject(User.class);
//                            holder.stockOwned.setText(user.noOfStocksOwned.get(position)+"");
//                            holder.stockNetWorth.setText("Rs"+user.noOfStocksOwned.get(position)*model.getStockPriceInRupees()+"");
//                        }
//                    }
//                });
                // holder.stockNetWorth.setText("Rs"+model.getStockPriceInRupees()*model.getStockPriceInRupees()+"");
            }
        };

        // recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}