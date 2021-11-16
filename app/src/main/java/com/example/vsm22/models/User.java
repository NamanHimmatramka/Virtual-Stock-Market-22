package com.example.vsm22.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String userName;
    public String uid;
     public List<Integer> noOfStocksOwned = new ArrayList<>();
    double[] currencyOwned = new double[3];
    public User(){
    }
    public User(String userName, String uid){
        this.uid=uid;
        this.userName=userName;
        for(int i=0; i<2; i++){
            noOfStocksOwned.add(0);
            currencyOwned[i] = 0.00;
        }
    }
}
