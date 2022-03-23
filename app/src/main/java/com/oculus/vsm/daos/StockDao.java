package com.oculus.vsm.daos;

import com.oculus.vsm.models.Stock;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StockDao {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference stockCollection = db.collection("stocks");
    public void addStock(Stock stock){
        stockCollection.document(stock.getStockName()).set(stock);
    }

    public Task<DocumentSnapshot> getStockByName(String stockName){
        return stockCollection.document(stockName).get();
    }

}
