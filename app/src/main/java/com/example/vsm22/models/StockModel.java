package com.example.vsm22.models;

public class StockModel {
    String stockName;
    double stockPriceInRupees;
    //double stockOwned;

    public StockModel() {
    }

    public StockModel(String stockName, double stockPriceInRupees) {
        this.stockName = stockName;
        this.stockPriceInRupees = stockPriceInRupees;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public double getStockPriceInRupees() {
        return stockPriceInRupees;
    }

    public void setStockPriceInRupees(double stockPriceInRupees) {
        this.stockPriceInRupees = stockPriceInRupees;
    }
}
