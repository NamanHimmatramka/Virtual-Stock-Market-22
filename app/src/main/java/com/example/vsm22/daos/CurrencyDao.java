package com.example.vsm22.daos;

import com.example.vsm22.models.Currency;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CurrencyDao {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference currencyCollection = db.collection("currency");
    public void addCurrency(Currency currency){
        currencyCollection.document(currency.currencyName).set(currency);
    }
}
