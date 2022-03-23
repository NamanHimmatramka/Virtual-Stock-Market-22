package com.oculus.vsm.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String userName;
    public String uid;
    public String status;
    public int insiderTrading;
    public int loanACap;
    public double loanBaseAmount;
    public double netWorth;
    public List<Integer> noOfStocksOwned = new ArrayList<>();
    public List<Double> currencyOwned = new ArrayList<>();
    public User(){
    }
    public User(String userName, String uid){
        this.uid=uid;
        this.userName=userName;
        this.insiderTrading=1;
        this.loanACap=1;
        this.loanBaseAmount=0;
        for(int i=0; i<6; i++){
            noOfStocksOwned.add(0);
        }
        for(int i=0; i<1; i++){
            currencyOwned.add(100000.00);
        }
    }
}
