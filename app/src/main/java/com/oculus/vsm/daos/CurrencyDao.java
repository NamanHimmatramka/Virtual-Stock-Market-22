package com.oculus.vsm.daos;

import com.oculus.vsm.models.Currency;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CurrencyDao {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference currencyCollection = db.collection("currency");
    public void addCurrency(Currency currency){
        currencyCollection.document(currency.getcryptoName()).set(currency);
    }
}
