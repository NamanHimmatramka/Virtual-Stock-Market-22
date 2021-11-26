package com.example.vsm22.models;

public class News {
    private String Headline;
    private int RoundNo;

    public News() {

    }

    public News(String headline, int roundNo) {
        Headline = headline;
        RoundNo = roundNo;
    }

    public String getHeadline() {
        return Headline;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public int getRoundNo() {
        return RoundNo;
    }

    public void setRoundNo(int roundNo) {
        RoundNo = roundNo;
    }
}
