package com.example.vsm22.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String userName;
    public String uid;
    public String status;
    public int insiderTrading;
    public double netWorth;
    public List<Integer> noOfStocksOwned = new ArrayList<>();
    public List<Double> currencyOwned = new ArrayList<>();
    public User(){
    }
    public User(String userName, String uid){
        this.uid=uid;
        this.userName=userName;
        this.insiderTrading=1;
        for(int i=0; i<6; i++){
            noOfStocksOwned.add((i+1)*10);
        }
        for(int i=0; i<1; i++){
            currencyOwned.add((i+1)*10.00);
        }
    }
}
