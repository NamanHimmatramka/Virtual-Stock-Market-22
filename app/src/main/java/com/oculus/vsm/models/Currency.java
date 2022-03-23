package com.oculus.vsm.models;

public class Currency {

    String cryptoName;
    double cryptoPriceInRupees;

    public Currency() {
    }

    public Currency(String cryptoName, double cryptoPriceInRupees) {
        this.cryptoName = cryptoName;
        this.cryptoPriceInRupees = cryptoPriceInRupees;
    }

    public String getcryptoName() {
        return cryptoName;
    }

    public void setcryptoName(String cryptoName) {
        this.cryptoName = cryptoName;
    }

    public double getcryptoPriceInRupees() {
        return cryptoPriceInRupees;
    }

    public void setcryptoPriceInRupees(double cryptoPriceInRupees) {
        this.cryptoPriceInRupees = cryptoPriceInRupees;
    }
}
