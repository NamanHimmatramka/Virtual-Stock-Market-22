package com.example.vsm22.models;

public class User {
    public String userName;
    public String uid;
    int[] noOfStocksOwned = new int[3];
    double[] currencyOwned = new double[3];
    public User(String userName, String uid){
        this.uid=uid;
        this.userName=userName;
        for(int i=0; i<2; i++){
            noOfStocksOwned[i] = 0;
            currencyOwned[i] = 0.00;
        }
    }
}
