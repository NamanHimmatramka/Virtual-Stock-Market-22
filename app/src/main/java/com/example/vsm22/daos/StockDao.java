package com.example.vsm22.daos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.vsm22.models.Stock;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class StockDao {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference stockCollection = db.collection("stocks");
    public void addStock(Stock stock){
        stockCollection.document(stock.stockName).set(stock);
    }

    public Task<DocumentSnapshot> getStockByName(String stockName){
        return stockCollection.document(stockName).get();
    }

}
