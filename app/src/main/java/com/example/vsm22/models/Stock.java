package com.example.vsm22.models;

public class Stock {

    public String stockName;
    double stockPriceInRupees;
    String stockImageUrl;
    public Stock(){

    }
    public Stock(String stockName, double stockPriceInRupees){
        this.stockName = stockName;
        this.stockPriceInRupees = stockPriceInRupees;
    }
}
